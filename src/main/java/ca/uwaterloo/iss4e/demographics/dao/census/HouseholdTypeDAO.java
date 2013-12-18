package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.HouseholdType;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class HouseholdTypeDAO {
	private DataSource dataSource;
	
	public HouseholdTypeDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertHouseholdType(HouseholdType householdType, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".household_type ("
				+ pkColumn
				+ ", sample_percentage, total, one_family, multiple_family, non_family) "
				+ "values (?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, householdType.getSamplePercentage(),
						householdType.getTotalCount(),
						householdType.getOneFamilyCount(),
						householdType.getMultipleFamilyCount(),
						householdType.getNonFamilyCount() });
	}
}
