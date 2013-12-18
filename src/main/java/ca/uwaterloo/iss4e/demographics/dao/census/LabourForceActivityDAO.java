package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.LabourForceActivity;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class LabourForceActivityDAO {
	private DataSource dataSource;
	
	public LabourForceActivityDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertLabourForceActivity(
			LabourForceActivity labourForceActivity, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".labour_force_activity ("
				+ pkColumn
				+ ", sample_percentage, total, employed, unemployed, not_in_labour_force) "
				+ "values (?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(sql,
				new Object[] { pk, labourForceActivity.getSamplePercentage(),
						labourForceActivity.getTotalCount(),
						labourForceActivity.getEmployedCount(),
						labourForceActivity.getUnemployedCount(),
						labourForceActivity.getNotInLabourForceCount() });

	}
}
