package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.ChildrenAtHome;

public class ChildrenAtHomeMapper implements RowMapper<ChildrenAtHome> {
	public ChildrenAtHome mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChildrenAtHome childrenAtHome = new ChildrenAtHome();
		childrenAtHome.setSamplePercentage(rs
				.getInt("children_at_home.sample_percentage"));
		childrenAtHome.setTotalCount(rs.getDouble("children_at_home.total"));
		childrenAtHome.setAveragePerFamily(rs
				.getDouble("children_at_home.average_per_family"));
		childrenAtHome.setYears5andUnderCount(rs
				.getDouble("children_at_home.years_5_and_under"));
		childrenAtHome.setYears6to14Count(rs
				.getDouble("children_at_home.years_6_to_14"));
		childrenAtHome.setYears15to17Count(rs
				.getDouble("children_at_home.years_15_to_17"));
		childrenAtHome.setYears18to24Count(rs
				.getDouble("children_at_home.years_18_to_24"));
		childrenAtHome.setYears25andOverCount(rs
				.getDouble("children_at_home.years_25_and_over"));
		return childrenAtHome;
	}
}
