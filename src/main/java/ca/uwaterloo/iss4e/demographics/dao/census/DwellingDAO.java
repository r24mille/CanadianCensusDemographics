package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.Dwelling;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class DwellingDAO {
	private DataSource dataSource;
	
	public DwellingDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertDwelling(Dwelling dwelling, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".dwelling ("
				+ pkColumn
				+ ", sample_percentage, total, average_number_rooms, average_number_bedrooms, "
				+ "owned, rented, band_housing, regular_maintenance_only, minor_repairs, "
				+ "major_repairs, construction_before_1946, construction_1946_to_1960, "
				+ "construction_1961_to_1970, construction_1971_to_1980, construction_1981_to_1985, "
				+ "construction_1986_to_1990, construction_1991_to_1995, construction_1996_to_2000, "
				+ "construction_2001_to_2006) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk, dwelling.getSamplePercentage(),
						dwelling.getTotalCount(),
						dwelling.getAverageNumberOfRooms(),
						dwelling.getAverageNumberOfBedrooms(),
						dwelling.getOwnedCount(), dwelling.getRentedCount(),
						dwelling.getBandHousingCount(),
						dwelling.getRegularMaintenanceOnlyCount(),
						dwelling.getMinorRepairsCount(),
						dwelling.getMajorRepairsCount(),
						dwelling.getConstructionBefore1946Count(),
						dwelling.getConstruction1946to1960Count(),
						dwelling.getConstruction1961to1970Count(),
						dwelling.getConstruction1971to1980Count(),
						dwelling.getConstruction1981to1985Count(),
						dwelling.getConstruction1986to1990Count(),
						dwelling.getConstruction1991to1995Count(),
						dwelling.getConstruction1996to2000Count(),
						dwelling.getConstruction2001to2006Count() });
	}
}
