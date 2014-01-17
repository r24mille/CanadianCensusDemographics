package ca.uwaterloo.iss4e.demographics.dao.geography.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.CensusCoordinateMapper;
import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.ForwardSortationAreaMapper;
import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.CensusPolygonMapper;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.ForwardSortationArea;

public class FullForwardSortationAreaExtractor implements
		ResultSetExtractor<Collection<ForwardSortationArea>> {

	@Override
	public Collection<ForwardSortationArea> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		String fsaCode = "";
		int cPolyId = 0;
		HashMap<String, ForwardSortationArea> fsaMap = new HashMap<String, ForwardSortationArea>();

		while (rs.next()) {
			ForwardSortationAreaMapper fsam = new ForwardSortationAreaMapper();
			ForwardSortationArea fsa = fsam.mapRow(rs, rs.getRow());
			if (!fsaCode.equals(fsa.getFsaCode())) {
				System.out.println("fsa code: " + fsa.getFsaCode());
				fsaCode = fsa.getFsaCode();
				fsaMap.put(fsa.getFsaCode(), fsa);
			}

			CensusPolygonMapper ppm = new CensusPolygonMapper();
			CensusPolygon cPoly = ppm.mapRow(rs, rs.getRow());
			if (cPolyId != cPoly.getPolygonPatchId()) {
				cPolyId = cPoly.getPolygonPatchId();
				fsaMap.get(fsa.getFsaCode()).addPolygon(cPoly);
			}

			CensusCoordinateMapper cm = new CensusCoordinateMapper();
			CensusCoordinate cCoord = cm.mapRow(rs, rs.getRow());
			ForwardSortationArea tempFSA = fsaMap.get(fsa.getFsaCode());
			int cpIndex = tempFSA.getCensusPolygons().size() - 1;
			tempFSA.getCensusPolygons().get(cpIndex).addCensusCoordinate(cCoord);
		}

		return fsaMap.values();
	}

}
