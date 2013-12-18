package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.HouseholdIncome;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class HouseholdIncomeDAO {
	private DataSource dataSource;
	
	public HouseholdIncomeDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertHouseholdIncome(HouseholdIncome householdIncome, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".household_income ("
				+ pkColumn
				+ ", sample_percentage, total, dollars_under_10000, dollars_10000_to_19999, "
				+ "dollars_20000_to_29999, dollars_30000_to_39999, dollars_40000_to_49999, "
				+ "dollars_50000_to_59999, dollars_60000_to_69999, dollars_70000_to_79999, "
				+ "dollars_80000_to_89999, dollars_90000_to_99999, dollars_100000_and_over, "
				+ "dollars_median, dollars_average, dollars_standard_error) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, householdIncome.getSamplePercentage(),
						householdIncome.getTotalCount(),
						householdIncome.getDollarsUnder10000Count(),
						householdIncome.getDollars10000to19999Count(),
						householdIncome.getDollars20000to29999Count(),
						householdIncome.getDollars30000to39999Count(),
						householdIncome.getDollars40000to49999Count(),
						householdIncome.getDollars50000to59999Count(),
						householdIncome.getDollars60000to69999Count(),
						householdIncome.getDollars70000to79999Count(),
						householdIncome.getDollars80000to89999Count(),
						householdIncome.getDollars90000to99999Count(),
						householdIncome.getDollars100000AndOverCount(),
						householdIncome.getDollarsMedian(),
						householdIncome.getDollarsAverage(),
						householdIncome.getDollarsStandardError() });
	}
}
