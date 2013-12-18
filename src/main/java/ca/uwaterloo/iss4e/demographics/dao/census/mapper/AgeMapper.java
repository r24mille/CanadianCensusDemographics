package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.Age;

public class AgeMapper implements RowMapper<Age> {
	public Age mapRow(ResultSet rs, int rowNum) throws SQLException {
		Age age = new Age();
		age.setSamplePercentage(rs.getInt("age.sample_percentage"));
		age.setTotalCount(rs.getDouble("age.total"));
		age.setYears0to4Count(rs.getDouble("age.years_0_to_4"));
		age.setYears5to9Count(rs.getDouble("age.years_5_to_9"));
		age.setYears10to14Count(rs.getDouble("age.years_10_to_14"));
		age.setYears15to19Count(rs.getDouble("age.years_15_to_19"));
		age.setYears20to24Count(rs.getDouble("age.years_20_to_24"));
		age.setYears25to29Count(rs.getDouble("age.years_25_to_29"));
		age.setYears30to34Count(rs.getDouble("age.years_30_to_34"));
		age.setYears35to39Count(rs.getDouble("age.years_35_to_39"));
		age.setYears40to44Count(rs.getDouble("age.years_40_to_44"));
		age.setYears45to49Count(rs.getDouble("age.years_45_to_49"));
		age.setYears50to54Count(rs.getDouble("age.years_50_to_54"));
		age.setYears55to59Count(rs.getDouble("age.years_55_to_59"));
		age.setYears60to64Count(rs.getDouble("age.years_60_to_64"));
		age.setYears65to69Count(rs.getDouble("age.years_65_to_69"));
		age.setYears70to74Count(rs.getDouble("age.years_70_to_74"));
		age.setYears75to79Count(rs.getDouble("age.years_75_to_79"));
		age.setYears80to84Count(rs.getDouble("age.years_80_to_84"));
		age.setYears85andOverCount(rs.getDouble("age.years_85_and_over"));
		age.setMedianAge(rs.getDouble("age.median_age"));
		return age;
	}
}
