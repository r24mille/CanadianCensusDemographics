package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.LabourForceActivity;

public class LabourForceActivityMapper implements
		RowMapper<LabourForceActivity> {
	public LabourForceActivity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		LabourForceActivity labourForceActivity = new LabourForceActivity();
		labourForceActivity.setSamplePercentage(rs
				.getInt("labour_force_activity.sample_percentage"));
		labourForceActivity.setTotalCount(rs
				.getDouble("labour_force_activity.total"));
		labourForceActivity.setEmployedCount(rs
				.getDouble("labour_force_activity.employed"));
		labourForceActivity.setUnemployedCount(rs
				.getDouble("labour_force_activity.unemployed"));
		labourForceActivity.setNotInLabourForceCount(rs
				.getDouble("labour_force_activity.not_in_labour_force"));
		return labourForceActivity;
	}
}
