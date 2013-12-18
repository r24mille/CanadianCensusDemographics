package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.HouseholdType;

public class HouseholdTypeMapper implements RowMapper<HouseholdType> {
	public HouseholdType mapRow(ResultSet rs, int rowNum) throws SQLException {
		HouseholdType householdType = new HouseholdType();
		householdType.setSamplePercentage(rs.getInt("household_type.sample_percentage"));
		householdType.setTotalCount(rs.getDouble("household_type.total"));
		householdType.setOneFamilyCount(rs.getDouble("household_type.one_family"));
		householdType.setMultipleFamilyCount(rs.getDouble("household_type.multiple_family"));
		householdType.setNonFamilyCount(rs.getDouble("household_type.non_family"));
		return householdType;
	}
}
