package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.StructuralType;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class StructuralTypeDAO {
	private DataSource dataSource;
	
	public StructuralTypeDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertStructuralType(StructuralType structuralType, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".structural_type ("
				+ pkColumn
				+ ", sample_percentage, total, single_detached, semi_detached, row_house, apartment_duplex, "
				+ "apartment_less_than_five_stories, apartment_five_stories_or_more, "
				+ "other_single_attached, movable_dwelling) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, structuralType.getSamplePercentage(),
						structuralType.getTotalCount(),
						structuralType.getSingleDetachedCount(),
						structuralType.getSemiDetachedCount(),
						structuralType.getRowHouseCount(),
						structuralType.getApartmentDuplexCount(),
						structuralType.getApartmentLessThan5StoriesCount(),
						structuralType.getApartment5StoriesOrMoreCount(),
						structuralType.getOtherSingleAttachedCount(),
						structuralType.getMovableDwellingCount() });
	}
}
