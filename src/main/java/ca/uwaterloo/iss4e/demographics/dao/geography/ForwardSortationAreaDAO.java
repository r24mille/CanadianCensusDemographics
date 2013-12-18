package ca.uwaterloo.iss4e.demographics.dao.geography;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.dao.geography.extractor.FullForwardSortationAreaExtractor;
import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.ForwardSortationAreaMapper;
import ca.uwaterloo.iss4e.demographics.model.geography.ForwardSortationArea;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class ForwardSortationAreaDAO {
	private DataSource dataSource;

	public ForwardSortationAreaDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}

	/**
	 * Gets skeleton information about an FSA. However, populating all
	 * associated CensusPolygon and CensusCoordinate objects is done separately.
	 * 
	 * @param fsaCode
	 * @return ForwardSortationArea
	 */
	public ForwardSortationArea getForwardSortationArea(String fsaCode) {
		String sql = "select fsa_code, province_id, province_name from "
				+ CensusSchemaUtil.CENSUS_2011_FSA_SCHEMA
				+ ".forward_sortation_area where fsa_code = ?";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		ForwardSortationArea fsa = template.queryForObject(sql,
				new Object[] { fsaCode }, new ForwardSortationAreaMapper());
		return fsa;
	}

	/**
	 * Gets skeleton information about an FSA. However, populating all
	 * associated CensusPolygon and CensusCoordinate objects is done separately.
	 * 
	 * @param fsaCode
	 * @return ForwardSortationArea
	 */
	public Collection<ForwardSortationArea> getForwardSortationAreas(
			int provinceId) {

		String sql = "select forward_sortation_area.*, polygon_patch.polygon_patch_id, "
				+ "coordinate.coordinate_id, coordinate.latitude, coordinate.longitude, coordinate.path_type "
				+ "from "
				+ CensusSchemaUtil.CENSUS_2011_FSA_SCHEMA
				+ ".forward_sortation_area "
				+ "left join "
				+ CensusSchemaUtil.CENSUS_2011_FSA_SCHEMA
				+ ".polygon_patch on polygon_patch.fsa_code = forward_sortation_area.fsa_code "
				+ "left join "
				+ CensusSchemaUtil.CENSUS_2011_FSA_SCHEMA
				+ ".coordinate on coordinate.polygon_patch_id = polygon_patch.polygon_patch_id "
				+ "where forward_sortation_area.province_id = ? "
				+ "order by forward_sortation_area.fsa_code, polygon_patch.polygon_patch_id, coordinate.coordinate_id asc";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		Collection<ForwardSortationArea> forwardSortationAreas = template
				.query(sql, new Object[] { provinceId },
						new FullForwardSortationAreaExtractor());

		return forwardSortationAreas;
	}

	/**
	 * Inserts a new FSA into the database.
	 * 
	 * @param forwardSortationArea
	 */
	public void insertForwardSortationArea(
			ForwardSortationArea forwardSortationArea) {
		String sql = "insert into " + CensusSchemaUtil.CENSUS_2011_FSA_SCHEMA
				+ ".forward_sortation_area "
				+ "(fsa_code, province_name, province_id) "
				+ "values (?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(sql,
				new Object[] { forwardSortationArea.getFsaCode(),
						forwardSortationArea.getProvinceName(),
						forwardSortationArea.getProvinceId() });
	}

	/**
	 * <p>
	 * The demographics data has population for an FSA. Though it's not
	 * geography, it seemed to fit best in the forward_sortation_area and it
	 * gives context to other demographics totals.
	 * </p>
	 * <p>
	 * This method is for updating the forward_sortation_area after GML import
	 * due to the fact that population is contained in the demographics CSV
	 * parsing.
	 * </p>
	 * 
	 * @param population
	 * @param fsaCode
	 */
	public void updatePopulation(int population, String fsaCode) {

		String sql = "update " + CensusSchemaUtil.CENSUS_2011_FSA_SCHEMA
				+ ".forward_sortation_area "
				+ "set population = ? where fsa_code = ?";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(sql, new Object[] { population, fsaCode });
	}
}
