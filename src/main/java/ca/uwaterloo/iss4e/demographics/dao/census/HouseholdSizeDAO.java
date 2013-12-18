package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.HouseholdSize;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class HouseholdSizeDAO {
	private DataSource dataSource;
	
	public HouseholdSizeDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertHouseholdSize(HouseholdSize householdSize, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".household_size ("
				+ pkColumn
				+ ", sample_percentage, total, average_number_persons, persons_1, persons_2, "
				+ "persons_3, persons_4, persons_5, persons_4_to_5, persons_6_or_more) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, householdSize.getSamplePercentage(),
						householdSize.getTotalCount(),
						householdSize.getAverageNumberPersons(),
						householdSize.getPersons1Count(),
						householdSize.getPersons2Count(),
						householdSize.getPersons3Count(),
						householdSize.getPersons4Count(),
						householdSize.getPersons5Count(),
						householdSize.getPersons4to5Count(),
						householdSize.getPersons6orMoreCount() });
	}
}
