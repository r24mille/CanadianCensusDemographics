package ca.uwaterloo.iss4e.demographics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.uwaterloo.iss4e.demographics.dao.geography.ForwardSortationAreaDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.CensusPolygonDAO;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.util.BuildKML;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class DisseminationAreaContainsMeter {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final CensusPolygonDAO censusPolygonDAO = (CensusPolygonDAO) context
			.getBean("censusPolygonDAO");

	public static void main(String[] args) {
		int daId = 35370607;
		List<CensusPolygon> censusPolygons = censusPolygonDAO
				.getCensusPolygonsForFK(daId);

		CensusCoordinate meter44144 = new CensusCoordinate(-82.88681162140344,
				42.33003713689462);

		for (CensusPolygon cp : censusPolygons) {
			System.out.println("Polygon Patch ID: " + cp.getPolygonPatchId());

			System.out.println(cp.getJTSPolygon());
			System.out.println(meter44144.getJTSPoint());

			System.out.println("DA #35370607 covers Meter #44144 "
					+ cp.getJTSPolygon().covers(meter44144.getJTSPoint()));
		}

		// Query database and append all the placemarks
		ForwardSortationAreaDAO fsaDAO = (ForwardSortationAreaDAO) context
				.getBean("forwardSortationAreaDAO");
		createOutlineFile(censusPolygons, ("whaaa.kml"));
	}

	private static void createOutlineFile(List<CensusPolygon> censusPolygons,
			String filename) {

		try {
			Kml kml = new Kml();
			Document kmlDocument = kml.createAndSetDocument();

			// Build out KML document
			appendStyles(kmlDocument);
			appendPlaceMarks(kmlDocument, censusPolygons);

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
			List<CensusPolygon> censusPolygons) {

		for (CensusPolygon censusPolygon : censusPolygons) {
			// Create PLACEMARK element for DOCUMENT
			Placemark placemark = new Placemark();
			placemark.setName("Polygon ID: "
					+ censusPolygon.getPolygonPatchId());
			placemark.setStyleUrl("#the_style");
			placemark.setDescription("Polygon ID: "
					+ censusPolygon.getPolygonPatchId());

			// Create MULTIGEOMETRY element for PLACEMARK
			MultiGeometry multiGeometry = new MultiGeometry();

			Point point = new Point();
			point.setId("point_" + censusPolygon.getPolygonPatchId());
			point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);

			// Create POLYGON for MULTIGEOMETRY
			multiGeometry.addToGeometry(BuildKML.createPolygon(censusPolygon));

			placemark.setGeometry(multiGeometry);
			kmlDocument.addToFeature(placemark);
		}
	}
}
