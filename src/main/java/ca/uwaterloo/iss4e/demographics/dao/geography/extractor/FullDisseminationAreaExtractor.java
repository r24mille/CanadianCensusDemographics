package ca.uwaterloo.iss4e.demographics.dao.geography.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.CoordinateMapper;
import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.DisseminationAreaMapper;
import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.PolygonPatchMapper;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;

public class FullDisseminationAreaExtractor implements
		ResultSetExtractor<Collection<DisseminationArea>> {

	@Override
	public Collection<DisseminationArea> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		int daId = 0;
		int cPolyId = 0;
		HashMap<Integer, DisseminationArea> daMap = new HashMap<Integer, DisseminationArea>();

		while (rs.next()) {
			DisseminationAreaMapper dam = new DisseminationAreaMapper();
			DisseminationArea da = dam.mapRow(rs, rs.getRow());
			if (daId != da.getDaId()) {
				System.out.println("da id: " + da.getDaId());
				daId = da.getDaId();
				daMap.put(da.getDaId(), da);
			}

			PolygonPatchMapper ppm = new PolygonPatchMapper();
			CensusPolygon cPoly = ppm.mapRow(rs, rs.getRow());
			if (cPolyId != cPoly.getPolygonPatchId()) {
				cPolyId = cPoly.getPolygonPatchId();
				daMap.get(da.getDaId()).addPolygon(cPoly);
			}

			CoordinateMapper cm = new CoordinateMapper();
			CensusCoordinate cCoord = cm.mapRow(rs, rs.getRow());
			DisseminationArea tempDA = daMap.get(da.getDaId());
			int cpIndex = tempDA.getPolygonPatches().size() - 1;
			tempDA.getPolygonPatches().get(cpIndex).addCoordinate(cCoord);
		}

		return daMap.values();
	}
}
