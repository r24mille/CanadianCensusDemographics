package ca.uwaterloo.iss4e.demographics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import ca.uwaterloo.iss4e.demographics.dao.geography.CoordinateDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.DisseminationAreaDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.PolygonPatchDAO;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;
import ca.uwaterloo.iss4e.demographics.sax.DaGmlHandler;

public class DisseminationAreaGeographyImport {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final PolygonPatchDAO polygonPatchDAO = (PolygonPatchDAO) context
			.getBean("polygonPatchDAO");
	private static final DisseminationAreaDAO disseminationAreaDAO = (DisseminationAreaDAO) context
			.getBean("disseminationAreaDAO");
	private static final CoordinateDAO coordinateDAO = (CoordinateDAO) context
			.getBean("coordinateDAO");

	public static void main(String[] args) {
		String file = "gda_000b11g_e.gml";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		saxParse(fis);
	}

	private static void saxParse(FileInputStream fis) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			DaGmlHandler daGmlHandler = new DaGmlHandler();
			saxParser.parse(fis, daGmlHandler);
			HashSet<DisseminationArea> das = daGmlHandler.getDas();

			// Insert each DA into the database
			for (DisseminationArea da : das) {
				System.out.println("Inserting DA: " + da.getDaId());
				disseminationAreaDAO.insertDisseminationArea(da);

				// Each DA may be comprised of multiple PolygonPatches
				for (CensusPolygon censusPolygon : da.getPolygonPatches()) {
					int polygonPatchId = polygonPatchDAO.insertPolygonPatch(da.getDaId());
					censusPolygon.setPolygonPatchId(polygonPatchId);
					System.out.println("  - Polygon Patch Inserted: "
							+ censusPolygon.getPolygonPatchId());

					// Each CensusPolygon is comprised of many Coordinates
					coordinateDAO.insertCoordinatesForDA(
							censusPolygon.getCoordinates(),
							censusPolygon.getPolygonPatchId());
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
