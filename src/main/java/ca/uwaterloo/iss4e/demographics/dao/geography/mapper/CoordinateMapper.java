package ca.uwaterloo.iss4e.demographics.dao.geography.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.PathType;

public class CoordinateMapper implements RowMapper<CensusCoordinate> {
	public CensusCoordinate mapRow(ResultSet rs, int rowNum) throws SQLException {
		double longitude = rs.getDouble("coordinate.longitude");
		double latitude = rs.getDouble("coordinate.latitude");

		CensusCoordinate censusCoordinate = new CensusCoordinate(longitude, latitude);
		censusCoordinate.setCoordinateId(rs.getInt("coordinate.coordinate_id"));
		censusCoordinate.setPathType(PathType.valueOf(rs.getString("coordinate.path_type")));

		return censusCoordinate;
	}
}
