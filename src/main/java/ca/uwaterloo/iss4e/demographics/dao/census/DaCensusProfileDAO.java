package ca.uwaterloo.iss4e.demographics.dao.census;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.dao.census.extractor.DaCensusProfileExtractor;
import ca.uwaterloo.iss4e.demographics.model.census.DaCensusProfile;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class DaCensusProfileDAO {
	private DataSource dataSource;
	
	public DaCensusProfileDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public Collection<DaCensusProfile> getDaCensusProfiles(int provinceId) {
		String schema = CensusSchemaUtil.getSchemaFromPk(0); // Hackish

		String sql = "select * from " + schema + ".dissemination_area dissemination_area "
				+ "left join " + schema + ".age on age.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".children_at_home on children_at_home.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".dwelling on dwelling.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".household_size on household_size.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".household_type on household_type.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".household_income on household_income.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".labour_force_activity on labour_force_activity.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".marital_status on marital_status.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".size_of_families on size_of_families.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".structural_type on structural_type.da_id = dissemination_area.da_id "
				+ "left join " + schema + ".work_commute_transportation on work_commute_transportation.da_id = dissemination_area.da_id "
				+ "where dissemination_area.province_id = ? order by dissemination_area.da_id asc";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		Collection<DaCensusProfile> daCensusProfiles = template.query(sql,
				new Object[] { provinceId }, new DaCensusProfileExtractor());

		return daCensusProfiles;
	}
}
