package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.HouseholdSize;

public class HouseholdSizeMapper implements RowMapper<HouseholdSize> {
	public HouseholdSize mapRow(ResultSet rs, int rowNum) throws SQLException {
		HouseholdSize householdSize = new HouseholdSize();
		householdSize.setSamplePercentage(rs
				.getInt("household_size.sample_percentage"));
		householdSize.setTotalCount(rs.getDouble("household_size.total"));
		householdSize.setAverageNumberPersons(rs
				.getDouble("household_size.average_number_persons"));
		householdSize
				.setPersons1Count(rs.getDouble("household_size.persons_1"));
		householdSize
				.setPersons2Count(rs.getDouble("household_size.persons_2"));
		householdSize
				.setPersons3Count(rs.getDouble("household_size.persons_3"));
		householdSize
				.setPersons4Count(rs.getDouble("household_size.persons_4"));
		householdSize
				.setPersons5Count(rs.getDouble("household_size.persons_5"));
		householdSize.setPersons4to5Count(rs
				.getDouble("household_size.persons_4_to_5"));
		householdSize.setPersons6orMoreCount(rs
				.getDouble("household_size.persons_6_or_more"));
		return householdSize;
	}
}
