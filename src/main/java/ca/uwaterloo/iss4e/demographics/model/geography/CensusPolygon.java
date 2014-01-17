package ca.uwaterloo.iss4e.demographics.model.geography;

import java.util.ArrayList;
import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Modeled very similarly to the PolygonPatch concept from GML but 
 * named CensusPolygon so that users do not get confused when 
 * working with opengis.geometry classes.
 */
public class CensusPolygon {
	private int polygonPatchId;
	private List<CensusCoordinate> censusCoordinates;

	public CensusPolygon() {
		this.censusCoordinates = new ArrayList<CensusCoordinate>();
	}

	public CensusPolygon(int polygonPatchId) {
		this.polygonPatchId = polygonPatchId;
		this.censusCoordinates = new ArrayList<CensusCoordinate>();
	}

	public Polygon getJTSPolygon() {
		GeometryFactory gf = JTSFactoryFinder.getGeometryFactory();
		Polygon polygon = new Polygon(this.getExteriorLinearRing(),
				this.getInteriorLinearRings(), gf);
		return polygon;
	}

	private LinearRing[] getInteriorLinearRings() {
		List<LinearRing> interiorLinRingList = new ArrayList<LinearRing>();

		boolean start = true; // Marks start of new LinearRing
		Coordinate startCoord = null; // Used for loop comparison
		List<Coordinate> coordList = null;
		for (CensusCoordinate censusCoordinate : censusCoordinates) {
			if (censusCoordinate.getPathType() == PathType.INTERIOR) {
				if (start) {
					// If it's not first loop and start is flagged, ad previous
					// Coordiantes
					if (coordList != null) {
						interiorLinRingList.add(this
								.coordinatesToLinearRing(coordList));
					}

					coordList = new ArrayList<Coordinate>();
					startCoord = censusCoordinate.getJTSPoint().getCoordinate();
					coordList.add(startCoord);
					start = false;
				} else {
					Coordinate currCoord = censusCoordinate.getJTSPoint()
							.getCoordinate();
					coordList.add(currCoord);

					// If current Coordiante same as start Coordinate, flag
					// restart next iteration
					if (currCoord.x == startCoord.x
							&& currCoord.y == startCoord.y) {
						start = true;
					}
				}
			}
		}

		LinearRing[] linearRings = interiorLinRingList
				.toArray(new LinearRing[interiorLinRingList.size()]);
		return linearRings;
	}

	public LinearRing getExteriorLinearRing() {
		List<Coordinate> coordList = new ArrayList<Coordinate>();

		for (CensusCoordinate censusCoordinate : censusCoordinates) {
			if (censusCoordinate.getPathType() == PathType.EXTERIOR) {
				coordList.add(new Coordinate(censusCoordinate.getLongitude(),
						censusCoordinate.getLatitude()));
			}
		}

		return this.coordinatesToLinearRing(coordList);
	}

	private LinearRing coordinatesToLinearRing(List<Coordinate> coordList) {
		Coordinate[] coordinates = coordList.toArray(new Coordinate[coordList
				.size()]);
		GeometryFactory geomFactory = JTSFactoryFinder.getGeometryFactory();
		return geomFactory.createLinearRing(coordinates);
	}

	public void addCensusCoordinate(CensusCoordinate censusCoordinate) {
		this.censusCoordinates.add(censusCoordinate);
	}

	public int getPolygonPatchId() {
		return polygonPatchId;
	}

	public void setPolygonPatchId(int polygonPatchId) {
		this.polygonPatchId = polygonPatchId;
	}

	public List<CensusCoordinate> getCensusCoordinates() {
		return censusCoordinates;
	}

	public void setCensusCoordinates(List<CensusCoordinate> censusCoordinates) {
		this.censusCoordinates = censusCoordinates;
	}
}