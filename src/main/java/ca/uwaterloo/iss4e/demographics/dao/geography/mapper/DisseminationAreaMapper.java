package ca.uwaterloo.iss4e.demographics.dao.geography.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.geography.CensusDivisionType;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;

public class DisseminationAreaMapper implements RowMapper<DisseminationArea> {
	public DisseminationArea mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CensusDivisionType censusDivisionType;

		if (rs.getString("dissemination_area.census_division_type") == null) {
			censusDivisionType = CensusDivisionType.UNKNOWN;
		} else {
			censusDivisionType = CensusDivisionType.valueOf(rs
					.getString("dissemination_area.census_division_type"));
		}

		DisseminationArea da = new DisseminationArea(
				rs.getInt("dissemination_area.da_id"),
				rs.getInt("dissemination_area.census_division_id"),
				rs.getInt("dissemination_area.province_id"),
				rs.getString("dissemination_area.census_division_name"),
				censusDivisionType,
				rs.getString("dissemination_area.province_name"),
				rs.getString("dissemination_area.data_quality_flags"));
		return da;
	}
}
