package ca.uwaterloo.iss4e.demographics.dao.geography.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;

public class PolygonPatchMapper implements RowMapper<CensusPolygon> {
	public CensusPolygon mapRow(ResultSet rs, int rowNum) throws SQLException {
		CensusPolygon pp = new CensusPolygon();
		pp.setPolygonPatchId(rs.getInt("polygon_patch.polygon_patch_id"));
		return pp;
	}
}
