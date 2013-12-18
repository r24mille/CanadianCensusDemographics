package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.SizeOfFamily;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class SizeOfFamilyDAO {
	private DataSource dataSource;
	
	public SizeOfFamilyDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertSizeOfFamily(SizeOfFamily sizeOfFamily, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into " + schema + ".size_of_families (" + pkColumn
				+ ", sample_percentage, total, persons_2, persons_3, "
				+ "persons_4, persons_5_or_more) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, sizeOfFamily.getSamplePercentage(),
						sizeOfFamily.getTotalCount(),
						sizeOfFamily.getPersons2Count(),
						sizeOfFamily.getPersons3Count(),
						sizeOfFamily.getPersons4Count(),
						sizeOfFamily.getPersons5orMoreCount() });
	}
}
