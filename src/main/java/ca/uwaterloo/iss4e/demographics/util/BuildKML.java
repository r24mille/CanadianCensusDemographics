package ca.uwaterloo.iss4e.demographics.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.PathType;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.ColorMode;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.LabelStyle;
import de.micromata.opengis.kml.v_2_2_0.LineStyle;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.PolyStyle;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import de.micromata.opengis.kml.v_2_2_0.Style;

public class BuildKML {
	public static Geometry createPolygon(CensusPolygon censusPolygon) {
		// Create POLYGON element
		Polygon polygon = new Polygon();
		polygon.setId("poly_" + censusPolygon.getPolygonPatchId());
		polygon.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);

		// Create OUTERBOUNDARYIS element for POLYGON
		Boundary outerBoundaryIs = new Boundary();

		// Create LINEARRING element for OUTERBOUNDARYIS
		LinearRing outerLinearRing = new LinearRing();
		LinearRing innerLinearRing = new LinearRing();

		// Create COORDINATES for LINEARRING
		List<Coordinate> exteriorCoordinates = new ArrayList<Coordinate>();
		List<Coordinate> interiorCoordinates = new ArrayList<Coordinate>();

		for (ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate gmlCoord : censusPolygon
				.getCoordinates()) {
			Coordinate kmlCoord = new Coordinate(gmlCoord.getLongitude(),
					gmlCoord.getLatitude());
			
			if (gmlCoord.getPathType() == PathType.EXTERIOR) {
				exteriorCoordinates.add(kmlCoord);
			} else if (gmlCoord.getPathType() == PathType.INTERIOR) {
				interiorCoordinates.add(kmlCoord);
			}
		}

		outerLinearRing.setCoordinates(exteriorCoordinates);
		innerLinearRing.setCoordinates(interiorCoordinates);
		outerBoundaryIs.setLinearRing(outerLinearRing);
		
		polygon.setOuterBoundaryIs(outerBoundaryIs);
		if (interiorCoordinates.size() > 0) {
			List<Boundary> innerBoundaryIs = new ArrayList<Boundary>();
			
			Boundary innerBoundary = new Boundary();
			innerBoundary.setLinearRing(innerLinearRing);
			innerBoundaryIs.add(innerBoundary);
			
			polygon.setInnerBoundaryIs(innerBoundaryIs);
		}
		return polygon;
	}
	
	/**
	 * Creates a Style KML element tied to the reading range. Sets icon and
	 * associated colors.
	 * 
	 * @param id
	 * @param iconLocation
	 * @param abgr
	 * @return Style object
	 */
	public static Style createStyle(String id, String abgr) {

		// Create STYLE element
		Style style = new Style();
		style.setId(id);

		// Create LABELSTYLE for STYLE
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.setColor(abgr);
		style.setLabelStyle(labelStyle);

		// Create LINESTYLE for STYLE
		LineStyle lineStyle = new LineStyle();
		lineStyle.setColor("ff000000");
		lineStyle.setWidth(1);
		style.setLineStyle(lineStyle);

		// Create POLYSTYLE for STYLE
		PolyStyle polyStyle = new PolyStyle();
		polyStyle.setColor(abgr);
		polyStyle.setColorMode(ColorMode.RANDOM);
		polyStyle.setFill(true);
		polyStyle.setOutline(true);
		style.setPolyStyle(polyStyle);

		return style;
	}

	public static String timeSpanFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		return sdf.format(date);
	}
}
