package ca.uwaterloo.iss4e.demographics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.uwaterloo.iss4e.demographics.dao.geography.DisseminationAreaDAO;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;
import ca.uwaterloo.iss4e.demographics.model.geography.Province;
import ca.uwaterloo.iss4e.demographics.util.BuildKML;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class DisseminationAreaKML {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final DisseminationAreaDAO disseminationAreaDAO = (DisseminationAreaDAO) context
			.getBean("disseminationAreaDAO");

	public static void main(String[] args) {
		// Query database and append all the placemarks
		Collection<DisseminationArea> das = disseminationAreaDAO
				.getDisseminationAreas(Province.BC_PROV_ID);
		createOutlineFile(das, (Province.BC_PROV_ID + ".kml"));
	}

	private static void createOutlineFile(Collection<DisseminationArea> das,
			String filename) {

		try {
			Kml kml = new Kml();
			Document kmlDocument = kml.createAndSetDocument();

			// Build out KML document
			appendStyles(kmlDocument);
			appendPlaceMarks(kmlDocument, das);

			kml.marshal(new File(filename));
			System.out.println("Done");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Appends many STYLE elements for each reading range we've identified as
	 * important.
	 * 
	 * @param kmlDocument
	 * @throws IOException
	 */
	private static void appendStyles(Document kmlDocument) {
		kmlDocument.addToStyleSelector(BuildKML.createStyle("the_style",
				"88ffffff"));
	}

	private static void appendPlaceMarks(Document kmlDocument,
			Collection<DisseminationArea> das) {

		for (DisseminationArea da : das) {
			// Create PLACEMARK element for DOCUMENT
			Placemark placemark = new Placemark();
			placemark.setName("DA ID: " + da.getDaId());
			placemark.setStyleUrl("#the_style");
			placemark.setDescription("Province: " + da.getProvinceName());

			// Create MULTIGEOMETRY element for PLACEMARK
			MultiGeometry multiGeometry = new MultiGeometry();

			// Add POINT elements to the MULTIGEOMETRY
			for (CensusPolygon censusPolygon : da.getCensusPolygons()) {
				Point point = new Point();
				point.setId("point_" + censusPolygon.getPolygonPatchId());
				point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);

				// Create POLYGON for MULTIGEOMETRY
				multiGeometry.addToGeometry(BuildKML
						.createPolygon(censusPolygon));
			}

			placemark.setGeometry(multiGeometry);
			kmlDocument.addToFeature(placemark);
		}
	}

}
