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

import ca.uwaterloo.iss4e.demographics.dao.geography.CensusCoordinateDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.DisseminationAreaDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.CensusPolygonDAO;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;
import ca.uwaterloo.iss4e.demographics.sax.Da2006GmlHandler;

public class DisseminationArea2006GeographyImport {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final CensusPolygonDAO censusPolygonDAO = (CensusPolygonDAO) context
			.getBean("censusPolygonDAO");
	private static final DisseminationAreaDAO disseminationAreaDAO = (DisseminationAreaDAO) context
			.getBean("disseminationAreaDAO");
	private static final CensusCoordinateDAO censusCoordinateDAO = (CensusCoordinateDAO) context
			.getBean("censusCoordinateDAO");

	public static void main(String[] args) {
		String file = "gda_000b06g_e.gml";
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
			Da2006GmlHandler da2006GmlHandler = new Da2006GmlHandler();
			saxParser.parse(fis, da2006GmlHandler);
			HashSet<DisseminationArea> das = da2006GmlHandler.getDas();

			// Insert each DA into the database
			for (DisseminationArea da : das) {
				System.out.println("Current DA: " + da.getDaId());

				// Each DA may be comprised of multiple CensusPolygons
				for (CensusPolygon censusPolygon : da.getCensusPolygons()) {
					 int polygonPatchId = censusPolygonDAO.insertCensusPolygon(da.getDaId());
					 censusPolygon.setPolygonPatchId(polygonPatchId);
					System.out.println("  - Polygon Patch Inserted: "
							+ censusPolygon.getPolygonPatchId());

					// Each CensusPolygon is comprised of many Coordinates
					censusCoordinateDAO.insertCoordinatesForDA(
							censusPolygon.getCensusCoordinates(),
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
