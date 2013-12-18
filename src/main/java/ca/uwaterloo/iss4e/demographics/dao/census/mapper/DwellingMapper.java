package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.Dwelling;

public class DwellingMapper implements RowMapper<Dwelling> {
	public Dwelling mapRow(ResultSet rs, int rowNum) throws SQLException {
		Dwelling dwelling = new Dwelling();
		dwelling.setSamplePercentage(rs.getInt("dwelling.sample_percentage"));
		dwelling.setTotalCount(rs.getDouble("dwelling.total"));
		dwelling.setAverageNumberOfRooms(rs
				.getDouble("dwelling.average_number_rooms"));
		dwelling.setAverageNumberOfBedrooms(rs
				.getDouble("dwelling.average_number_bedrooms"));
		dwelling.setOwnedCount(rs.getDouble("dwelling.owned"));
		dwelling.setRentedCount(rs.getDouble("dwelling.rented"));
		dwelling.setBandHousingCount(rs.getDouble("dwelling.band_housing"));
		dwelling.setRegularMaintenanceOnlyCount(rs
				.getDouble("dwelling.regular_maintenance_only"));
		dwelling.setMinorRepairsCount(rs.getDouble("dwelling.minor_repairs"));
		dwelling.setMajorRepairsCount(rs.getDouble("dwelling.major_repairs"));
		dwelling.setConstructionBefore1946Count(rs
				.getDouble("dwelling.construction_before_1946"));
		dwelling.setConstruction1946to1960Count(rs
				.getDouble("dwelling.construction_1946_to_1960"));
		dwelling.setConstruction1961to1970Count(rs
				.getDouble("dwelling.construction_1961_to_1970"));
		dwelling.setConstruction1971to1980Count(rs
				.getDouble("dwelling.construction_1971_to_1980"));
		dwelling.setConstruction1981to1985Count(rs
				.getDouble("dwelling.construction_1981_to_1985"));
		dwelling.setConstruction1986to1990Count(rs
				.getDouble("dwelling.construction_1986_to_1990"));
		dwelling.setConstruction1991to1995Count(rs
				.getDouble("dwelling.construction_1991_to_1995"));
		dwelling.setConstruction1996to2000Count(rs
				.getDouble("dwelling.construction_1996_to_2000"));
		dwelling.setConstruction2001to2006Count(rs
				.getDouble("dwelling.construction_2001_to_2006"));
		return dwelling;
	}
}
