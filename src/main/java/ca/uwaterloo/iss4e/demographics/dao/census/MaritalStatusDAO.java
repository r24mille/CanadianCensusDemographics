package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.MaritalStatus;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class MaritalStatusDAO {
	private DataSource dataSource;
	
	public MaritalStatusDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertMaritalStatus(MaritalStatus maritalStatus, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".marital_status ("
				+ pkColumn
				+ ", sample_percentage, total, never_married, married_or_commonlaw, "
				+ "separated, divorced, widowed) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, maritalStatus.getSamplePercentage(),
						maritalStatus.getTotalCount(),
						maritalStatus.getNeverMarriedCount(),
						maritalStatus.getMarriedOrCommonlawCount(),
						maritalStatus.getSeparatedCount(),
						maritalStatus.getDivorcedCount(),
						maritalStatus.getWidowedCount() });

	}
}
