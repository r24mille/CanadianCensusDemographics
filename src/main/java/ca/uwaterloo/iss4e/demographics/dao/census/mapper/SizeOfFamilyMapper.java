package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.SizeOfFamily;

public class SizeOfFamilyMapper implements RowMapper<SizeOfFamily> {
	public SizeOfFamily mapRow(ResultSet rs, int rowNum) throws SQLException {
		SizeOfFamily sizeOfFamily = new SizeOfFamily();
		sizeOfFamily.setSamplePercentage(rs
				.getInt("size_of_families.sample_percentage"));
		sizeOfFamily.setTotalCount(rs.getDouble("size_of_families.total"));
		sizeOfFamily.setPersons2Count(rs
				.getDouble("size_of_families.persons_2"));
		sizeOfFamily.setPersons3Count(rs
				.getDouble("size_of_families.persons_3"));
		sizeOfFamily.setPersons4Count(rs
				.getDouble("size_of_families.persons_4"));
		sizeOfFamily.setPersons5orMoreCount(rs
				.getDouble("size_of_families.persons_5_or_more"));
		return sizeOfFamily;
	}
}
