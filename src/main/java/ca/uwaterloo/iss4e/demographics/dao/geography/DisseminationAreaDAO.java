package ca.uwaterloo.iss4e.demographics.dao.geography;

import java.util.Collection;
import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import ca.uwaterloo.iss4e.demographics.dao.geography.extractor.FullDisseminationAreaExtractor;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class DisseminationAreaDAO {
	private DataSource dataSource;
	
	public DisseminationAreaDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public Collection<DisseminationArea> getDisseminationAreas(int provinceId) {
		String schema = CensusSchemaUtil.getSchemaFromPk(0); // Hackish

		String sql = "select dissemination_area.*, polygon_patch.polygon_patch_id, "
				+ "coordinate.coordinate_id, coordinate.latitude, coordinate.longitude, coordinate.path_type "
				+ "from "
				+ schema
				+ ".dissemination_area "
				+ "left join "
				+ schema
				+ ".polygon_patch on polygon_patch.da_id = dissemination_area.da_id "
				+ "left join "
				+ schema
				+ ".coordinate on coordinate.polygon_patch_id = polygon_patch.polygon_patch_id "
				+ "where dissemination_area.province_id = ? "
				+ "order by dissemination_area.da_id, polygon_patch.polygon_patch_id, coordinate.coordinate_id asc";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		Collection<DisseminationArea> disseminationAreas = template.query(sql,
				new Object[] { provinceId }, new FullDisseminationAreaExtractor());

		return disseminationAreas;
	}

	public void insertDisseminationArea(DisseminationArea disseminationArea) {
		String schema = CensusSchemaUtil.getSchemaFromPk(disseminationArea
				.getDaId());

		String sql = "insert into "
				+ schema
				+ ".dissemination_area "
				+ "(da_id, province_name, province_id, census_division_id, "
				+ "census_division_name, census_division_type, data_quality_flags) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);

		// Handles null CensusDivisionType from 2006 census
		String cdtString = null;
		if (disseminationArea.getCensusDivisionType() != null) {
			cdtString = disseminationArea.getCensusDivisionType().toString();
		}

		template.update(
				sql,
				new Object[] { disseminationArea.getDaId(),
						disseminationArea.getProvinceName(),
						disseminationArea.getProvinceId(),
						disseminationArea.getCensusDivisionId(),
						disseminationArea.getCensusDivisionName(), cdtString,
						disseminationArea.getDataQualityFlags() });
	}

	public void updatePopulation(int population, int daId) {
		String schema = CensusSchemaUtil.getSchemaFromPk(daId);

		String sql = "update " + schema + ".dissemination_area "
				+ "set population = ? where da_id = ?";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(sql, new Object[] { population, daId });
	}

	public HashSet<Integer> getDaIds() {
		HashSet<Integer> daIds = new HashSet<Integer>();
		String schema = CensusSchemaUtil.getSchemaFromPk(0); // Hackish

		String sql = "select da_id from " + schema + ".dissemination_area";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		SqlRowSet rowSet = template.queryForRowSet(sql);
		while (rowSet.next()) {
			daIds.add(rowSet.getInt("da_id"));
		}

		return daIds;
	}
}
