package ca.uwaterloo.iss4e.demographics.dao.geography;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.CoordinateMapper;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.util.CensusSchemaUtil;

public class CoordinateDAO {
	private DataSource dataSource;

	public CoordinateDAO(DataSource dataSouce) {
		this.dataSource = dataSouce;
	}

	public void insertCoordinatesForDA(
			List<CensusCoordinate> censusCoordinates, int polygonPatchId) {
		String schema = CensusSchemaUtil.getSchemaFromPk(0); // Hackish

		int i = 0;
		while (i <= censusCoordinates.size()) {
			// Set an endpoint to maximize the subList length at 2000 elements
			int end = i + 2000;
			if (end > censusCoordinates.size()) {
				end = censusCoordinates.size();
			}

			// Insert coordinates in ~2000 element batches
			List<CensusCoordinate> coordSubList = censusCoordinates.subList(i,
					end);
			this.insertCoordinates(coordSubList, polygonPatchId, schema);

			i = i + 2000;
		}

	}

	public void insertCoordinatesForFSA(
			List<CensusCoordinate> censusCoordinates, int polygonPatchId) {
		String schema = CensusSchemaUtil.getSchemaFromPk("fsa_code"); // Hackish

		int i = 0;
		while (i <= censusCoordinates.size()) {
			// Set an endpoint to maximize the subList length at 2000 elements
			int end = i + 2000;
			if (end > censusCoordinates.size()) {
				end = censusCoordinates.size();
			}

			// Insert coordinates in ~2000 element batches
			List<CensusCoordinate> coordSubList = censusCoordinates.subList(i,
					end);
			this.insertCoordinates(coordSubList, polygonPatchId, schema);

			i = i + 2000;
		}
	}

	private void insertCoordinates(List<CensusCoordinate> censusCoordinates,
			int polygonPatchId, String schema) {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("insert into " + schema + ".coordinate ");
		sqlBuff.append("(polygon_patch_id, latitude, longitude, path_type) ");
		sqlBuff.append("values ");

		for (int i = 0; i < censusCoordinates.size(); i++) {
			sqlBuff.append("(?, ?, ?, ?)");

			if (i + 1 < censusCoordinates.size()) {
				sqlBuff.append(", ");
			}
		}

		ArrayList<Object> argsList = new ArrayList<Object>();
		int j = 1;
		for (CensusCoordinate censusCoordinate : censusCoordinates) {
			argsList.add(j, polygonPatchId);
			j = j + 1;
			argsList.add(j, censusCoordinate.getLatitude());
			j = j + 1;
			argsList.add(j, censusCoordinate.getLongitude());
			j = j + 1;
			argsList.add(j, censusCoordinate.getPathType().toString());
			j = j + 1;
		}
		Object[] args = new Object[argsList.size()];
		argsList.toArray(args);

		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update(sqlBuff.toString(), args);
	}

	public List<CensusCoordinate> getCoordinatesForDA(int polygonPatchId) {
		String schema = CensusSchemaUtil.getSchemaFromPk(0); // Hackish
		return this.getCoordinates(polygonPatchId, schema);
	}

	public List<CensusCoordinate> getCoordinatesForFSA(int polygonPatchId) {
		String schema = CensusSchemaUtil.getSchemaFromPk("fsa_code"); // Hackish
		return this.getCoordinates(polygonPatchId, schema);
	}

	private List<CensusCoordinate> getCoordinates(int polygonPatchId,
			String schema) {
		String sql = "select * from " + schema
				+ ".coordinate where polygon_patch_id = ?";

		JdbcTemplate template = new JdbcTemplate(dataSource);
		List<CensusCoordinate> censusCoordinates = template.query(sql,
				new Object[] { polygonPatchId }, new CoordinateMapper());

		return censusCoordinates;

	}
}
