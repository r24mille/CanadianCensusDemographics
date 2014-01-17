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
import ca.uwaterloo.iss4e.demographics.dao.geography.ForwardSortationAreaDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.CensusPolygonDAO;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.ForwardSortationArea;
import ca.uwaterloo.iss4e.demographics.sax.Fsa2011GmlHandler;

public class ForwardSortationArea2011GeographyImport {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final CensusPolygonDAO censusPolygonDAO = (CensusPolygonDAO) context
			.getBean("censusPolygonDAO");
	private static final ForwardSortationAreaDAO forwardSortationAreaDAO = (ForwardSortationAreaDAO) context
			.getBean("forwardSortationAreaDAO");
	private static final CensusCoordinateDAO censusCoordinateDAO = (CensusCoordinateDAO) context
			.getBean("censusCoordinateDAO");

	public static void main(String[] args) {
		String file = "gfsa000b11g_e.gml";
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
			Fsa2011GmlHandler fsa2011GmlHandler = new Fsa2011GmlHandler();
			saxParser.parse(fis, fsa2011GmlHandler);
			HashSet<ForwardSortationArea> fsas = fsa2011GmlHandler.getFsas();

			// Insert each FSA into the database
			for (ForwardSortationArea fsa : fsas) {
				System.out.println("Inserting FSA: " + fsa.getFsaCode());
				forwardSortationAreaDAO.insertForwardSortationArea(fsa);

				// Each FSA may be comprised of multiple CensusPolygons
				for (CensusPolygon censusPolygon : fsa.getCensusPolygons()) {
					int polygonPatchId = censusPolygonDAO.insertCensusPolygon(fsa
							.getFsaCode());
					censusPolygon.setPolygonPatchId(polygonPatchId);
					System.out.println("  - Polygon Patch Inserted: "
							+ censusPolygon.getPolygonPatchId());

					// Each CensusPolygon is comprised of many Coordinates
					censusCoordinateDAO.insertCoordinatesForFSA(
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
