package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.MaritalStatus;

public class MaritalStatusMapper implements RowMapper<MaritalStatus> {
	public MaritalStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		MaritalStatus maritalStatus = new MaritalStatus();
		maritalStatus.setSamplePercentage(rs
				.getInt("marital_status.sample_percentage"));
		maritalStatus.setTotalCount(rs.getDouble("marital_status.total"));
		maritalStatus.setNeverMarriedCount(rs
				.getDouble("marital_status.never_married"));
		maritalStatus.setMarriedOrCommonlawCount(rs
				.getDouble("marital_status.married_or_commonlaw"));
		maritalStatus.setSeparatedCount(rs
				.getDouble("marital_status.separated"));
		maritalStatus.setDivorcedCount(rs.getDouble("marital_status.divorced"));
		maritalStatus.setWidowedCount(rs.getDouble("marital_status.widowed"));
		return maritalStatus;
	}
}
