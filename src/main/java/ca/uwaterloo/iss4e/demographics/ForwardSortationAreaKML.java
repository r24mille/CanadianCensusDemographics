package ca.uwaterloo.iss4e.demographics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.uwaterloo.iss4e.demographics.dao.geography.ForwardSortationAreaDAO;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.ForwardSortationArea;
import ca.uwaterloo.iss4e.demographics.model.geography.Province;
import ca.uwaterloo.iss4e.demographics.util.BuildKML;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class ForwardSortationAreaKML {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final ForwardSortationAreaDAO forwardSortationAreaDAO = (ForwardSortationAreaDAO) context
			.getBean("forwardSortationAreaDAO");
	
	public static void main(String[] args) {
		Collection<ForwardSortationArea> fsas = forwardSortationAreaDAO
				.getForwardSortationAreas(Province.BC_PROV_ID);
		createOutlineFile(fsas, (Province.BC_PROV_ID + ".kml"));
	}

	private static void createOutlineFile(Collection<ForwardSortationArea> fsas,
			String filename) {

		try {
			Kml kml = new Kml();
			Document kmlDocument = kml.createAndSetDocument();

			// Build out KML document
			appendStyles(kmlDocument);
			appendPlaceMarks(kmlDocument, fsas);

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
			Collection<ForwardSortationArea> fsas) {

		for (ForwardSortationArea fsa : fsas) {
			// Create PLACEMARK element for DOCUMENT
			Placemark placemark = new Placemark();
			placemark.setName("FSA Code: " + fsa.getFsaCode());
			placemark.setStyleUrl("#the_style");
			placemark.setDescription("Province: " + fsa.getProvinceName());
			
			// Create MULTIGEOMETRY element for PLACEMARK
			MultiGeometry multiGeometry = new MultiGeometry();
			
			// Add POINT elements to the MULTIGEOMETRY
			for (CensusPolygon censusPolygon : fsa.getCensusPolygons()) {
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
