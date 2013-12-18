package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.ChildrenAtHome;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class ChildrenAtHomeDAO {
	private DataSource dataSource;
	
	public ChildrenAtHomeDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertChildrenAtHome(ChildrenAtHome childrenAtHome, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".children_at_home ("
				+ pkColumn
				+ ", sample_percentage, total, average_per_family, years_5_and_under, "
				+ "years_6_to_14, years_15_to_17, years_18_to_24, years_25_and_over) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, childrenAtHome.getSamplePercentage(),
						childrenAtHome.getTotalCount(),
						childrenAtHome.getAveragePerFamily(),
						childrenAtHome.getYears5andUnderCount(),
						childrenAtHome.getYears6to14Count(),
						childrenAtHome.getYears15to17Count(),
						childrenAtHome.getYears18to24Count(),
						childrenAtHome.getYears25andOverCount() });
	}
}
