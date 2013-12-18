package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.WorkCommuteTransportation;

public class WorkCommuteTransportationMapper implements
		RowMapper<WorkCommuteTransportation> {
	public WorkCommuteTransportation mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		WorkCommuteTransportation workCommuteTransportation = new WorkCommuteTransportation();
		workCommuteTransportation.setSamplePercentage(rs
				.getInt("work_commute_transportation.sample_percentage"));
		workCommuteTransportation.setTotalCount(rs
				.getDouble("work_commute_transportation.total"));
		workCommuteTransportation.setCarTruckVanDriver(rs
				.getDouble("work_commute_transportation.car_trk_van_driver"));
		workCommuteTransportation
				.setCarTruckVanPassenger(rs
						.getDouble("work_commute_transportation.car_trk_van_passenger"));
		workCommuteTransportation.setPublicTransit(rs
				.getDouble("work_commute_transportation.public_transit"));
		workCommuteTransportation.setWalked(rs
				.getDouble("work_commute_transportation.walked"));
		workCommuteTransportation.setBicycle(rs
				.getDouble("work_commute_transportation.bicycle"));
		workCommuteTransportation.setMotorcycle(rs
				.getDouble("work_commute_transportation.motorcycle"));
		workCommuteTransportation.setTaxicab(rs
				.getDouble("work_commute_transportation.taxicab"));
		workCommuteTransportation.setOtherMethod(rs
				.getDouble("work_commute_transportation.other"));
		return workCommuteTransportation;
	}
}
