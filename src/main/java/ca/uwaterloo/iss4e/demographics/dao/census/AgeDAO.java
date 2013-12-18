package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.Age;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class AgeDAO {
	private DataSource dataSource;
	
	public AgeDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertAge(Age age, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".age ("
				+ pkColumn
				+ ", sample_percentage, total, years_0_to_4, years_5_to_9, "
				+ "years_10_to_14, years_15_to_19, years_20_to_24, years_25_to_29, "
				+ "years_30_to_34, years_35_to_39, years_40_to_44, years_45_to_49, "
				+ "years_50_to_54, years_55_to_59, years_60_to_64, years_65_to_69, "
				+ "years_70_to_74, years_75_to_79, years_80_to_84, years_85_and_over, median_age) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, age.getSamplePercentage(),
						age.getTotalCount(), age.getYears0to4Count(),
						age.getYears5to9Count(), age.getYears10to14Count(),
						age.getYears15to19Count(), age.getYears20to24Count(),
						age.getYears25to29Count(), age.getYears30to34Count(),
						age.getYears35to39Count(), age.getYears40to44Count(),
						age.getYears45to49Count(), age.getYears50to54Count(),
						age.getYears55to59Count(), age.getYears60to64Count(),
						age.getYears65to69Count(), age.getYears70to74Count(),
						age.getYears75to79Count(), age.getYears80to84Count(),
						age.getYears85andOverCount(), age.getMedianAge() });

	}
}
