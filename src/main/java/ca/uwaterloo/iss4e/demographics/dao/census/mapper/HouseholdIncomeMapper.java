package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.HouseholdIncome;

public class HouseholdIncomeMapper implements RowMapper<HouseholdIncome> {
	public HouseholdIncome mapRow(ResultSet rs, int rowNum) throws SQLException {
		HouseholdIncome householdIncome = new HouseholdIncome();
		householdIncome.setSamplePercentage(rs
				.getInt("household_income.sample_percentage"));
		householdIncome.setTotalCount(rs.getDouble("household_income.total"));
		householdIncome.setDollarsUnder10000Count(rs
				.getDouble("household_income.dollars_under_10000"));
		householdIncome.setDollars10000to19999Count(rs
				.getDouble("household_income.dollars_10000_to_19999"));
		householdIncome.setDollars20000to29999Count(rs
				.getDouble("household_income.dollars_20000_to_29999"));
		householdIncome.setDollars30000to39999Count(rs
				.getDouble("household_income.dollars_30000_to_39999"));
		householdIncome.setDollars40000to49999Count(rs
				.getDouble("household_income.dollars_40000_to_49999"));
		householdIncome.setDollars50000to59999Count(rs
				.getDouble("household_income.dollars_50000_to_59999"));
		householdIncome.setDollars60000to69999Count(rs
				.getDouble("household_income.dollars_60000_to_69999"));
		householdIncome.setDollars70000to79999Count(rs
				.getDouble("household_income.dollars_70000_to_79999"));
		householdIncome.setDollars80000to89999Count(rs
				.getDouble("household_income.dollars_80000_to_89999"));
		householdIncome.setDollars90000to99999Count(rs
				.getDouble("household_income.dollars_90000_to_99999"));
		householdIncome.setDollars100000AndOverCount(rs
				.getDouble("household_income.dollars_100000_and_over"));
		householdIncome.setDollarsMedian(rs
				.getDouble("household_income.dollars_median"));
		householdIncome.setDollarsAverage(rs
				.getDouble("household_income.dollars_average"));
		householdIncome.setDollarsStandardError(rs
				.getDouble("household_income.dollars_standard_error"));
		return householdIncome;
	}
}
