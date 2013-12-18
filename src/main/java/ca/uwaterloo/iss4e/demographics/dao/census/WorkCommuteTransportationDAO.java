package ca.uwaterloo.iss4e.demographics.dao.census;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.model.census.WorkCommuteTransportation;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class WorkCommuteTransportationDAO {
	private DataSource dataSource;
	
	public WorkCommuteTransportationDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}
	
	public void insertWorkCommuteTransportation(
			WorkCommuteTransportation workCommuteTransportation, Object pk) {
		String pkColumn = CensusSchemaUtil.getPkColumnName(pk);
		String schema = CensusSchemaUtil.getSchemaFromPk(pk);

		String sql = "insert into "
				+ schema
				+ ".work_commute_transportation ("
				+ pkColumn
				+ ", sample_percentage, total, car_trk_van_driver, car_trk_van_passenger, public_transit, "
				+ "walked, bicycle, motorcycle, taxicab, other) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(
				sql,
				new Object[] { pk,
						workCommuteTransportation.getSamplePercentage(),
						workCommuteTransportation.getTotalCount(),
						workCommuteTransportation.getCarTruckVanDriver(),
						workCommuteTransportation.getCarTruckVanPassenger(),
						workCommuteTransportation.getPublicTransit(),
						workCommuteTransportation.getWalked(),
						workCommuteTransportation.getBicycle(),
						workCommuteTransportation.getMotorcycle(),
						workCommuteTransportation.getTaxicab(),
						workCommuteTransportation.getOtherMethod() });

	}
}
