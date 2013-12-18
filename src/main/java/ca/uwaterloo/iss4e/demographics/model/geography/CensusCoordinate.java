package ca.uwaterloo.iss4e.demographics.model.geography;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class CensusCoordinate extends com.vividsolutions.jts.geom.Coordinate {
	private static final long serialVersionUID = -320162206013397160L;
	private int coordinateId;
	private PathType pathType;

	public CensusCoordinate(double longitude, double latitude) {
		super(longitude, latitude);
	}

	public CensusCoordinate(double longitude, double latitude, double altitude) {
		super(longitude, latitude, altitude);
	}

	public Point getJTSPoint() {
		GeometryFactory gf = JTSFactoryFinder.getGeometryFactory();
		return gf.createPoint(this);
	}

	public int getCoordinateId() {
		return coordinateId;
	}

	public void setCoordinateId(int coordinateId) {
		this.coordinateId = coordinateId;
	}

	public PathType getPathType() {
		return pathType;
	}

	public void setPathType(PathType pathType) {
		this.pathType = pathType;
	}

	public void setLongitude(double longitude) {
		super.x = longitude;
	}

	public double getLongitude() {
		return super.x;
	}

	public void setLatitude(double latitude) {
		super.y = latitude;
	}

	public double getLatitude() {
		return super.y;
	}

	public void setAltitude(double altitude) {
		super.z = altitude;
	}

	public double getAltitude() {
		return super.z;
	}
}