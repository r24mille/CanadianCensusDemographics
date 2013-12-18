package ca.uwaterloo.iss4e.demographics.dao.geography.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;

import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.CoordinateMapper;
import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.PolygonPatchMapper;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;

public class PolygonPatchExtractor extends
		OneToManyResultSetExtractor<CensusPolygon, CensusCoordinate, Integer> {
	public PolygonPatchExtractor() {
		super(new PolygonPatchMapper(), new CoordinateMapper());
	}

	@Override
	protected Integer mapPrimaryKey(ResultSet rs) throws SQLException {
		return rs.getInt("p.polygon_patch_id");
	}

	@Override
	protected Integer mapForeignKey(ResultSet rs) throws SQLException {
		if (rs.getObject("c.polygon_patch_id") == null) {
			return null;
		} else {
			return rs.getInt("c.polygon_patch_id");
		}
	}

	@Override
	protected void addChild(CensusPolygon root, CensusCoordinate child) {
		root.addCoordinate(child);
	}
}
