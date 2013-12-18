package ca.uwaterloo.iss4e.demographics.dao.geography.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.geography.ForwardSortationArea;

public class ForwardSortationAreaMapper implements
		RowMapper<ForwardSortationArea> {
	public ForwardSortationArea mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		String fsaCode = rs.getString("forward_sortation_area.fsa_code");
		int provinceId = rs.getInt("forward_sortation_area.province_id");
		String provinceName = rs
				.getString("forward_sortation_area.province_name");

		return new ForwardSortationArea(fsaCode, provinceName, provinceId);
	}

}
