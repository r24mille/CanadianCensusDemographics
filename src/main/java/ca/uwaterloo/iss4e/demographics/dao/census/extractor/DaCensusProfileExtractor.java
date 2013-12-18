package ca.uwaterloo.iss4e.demographics.dao.census.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import ca.uwaterloo.iss4e.demographics.dao.census.mapper.AgeMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.ChildrenAtHomeMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.DwellingMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.HouseholdIncomeMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.HouseholdSizeMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.HouseholdTypeMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.LabourForceActivityMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.MaritalStatusMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.SizeOfFamilyMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.StructuralTypeMapper;
import ca.uwaterloo.iss4e.demographics.dao.census.mapper.WorkCommuteTransportationMapper;
import ca.uwaterloo.iss4e.demographics.dao.geography.mapper.DisseminationAreaMapper;
import ca.uwaterloo.iss4e.demographics.model.census.DaCensusProfile;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;

public class DaCensusProfileExtractor implements
		ResultSetExtractor<Collection<DaCensusProfile>> {
	private static final DisseminationAreaMapper dam = new DisseminationAreaMapper();
	private static final AgeMapper am = new AgeMapper();
	private static final ChildrenAtHomeMapper cahm = new ChildrenAtHomeMapper();
	private static final DwellingMapper dm = new DwellingMapper();
	private static final HouseholdIncomeMapper him = new HouseholdIncomeMapper();
	private static final HouseholdSizeMapper hsm = new HouseholdSizeMapper();
	private static final HouseholdTypeMapper htm = new HouseholdTypeMapper();
	private static final LabourForceActivityMapper lfam = new LabourForceActivityMapper();
	private static final MaritalStatusMapper msm = new MaritalStatusMapper();
	private static final SizeOfFamilyMapper sofm = new SizeOfFamilyMapper();
	private static final StructuralTypeMapper stm = new StructuralTypeMapper();
	private static final WorkCommuteTransportationMapper wctm = new WorkCommuteTransportationMapper();

	@Override
	public Collection<DaCensusProfile> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		List<DaCensusProfile> daCensusProfiles = new ArrayList<DaCensusProfile>();
		
		while (rs.next()) {
			int rowNum = rs.getRow();
			DisseminationArea da = dam.mapRow(rs, rowNum);
			
			DaCensusProfile daCensusProfile = new DaCensusProfile(da.getDaId());
			daCensusProfile.setAge(am.mapRow(rs, rowNum));
			daCensusProfile.setChildrenAtHome(cahm.mapRow(rs, rowNum));
			daCensusProfile.setDwelling(dm.mapRow(rs, rowNum));
			daCensusProfile.setHouseholdIncome(him.mapRow(rs, rowNum));
			daCensusProfile.setHouseholdSize(hsm.mapRow(rs, rowNum));
			daCensusProfile.setHouseholdType(htm.mapRow(rs, rowNum));
			daCensusProfile.setLabourForceActivity(lfam.mapRow(rs, rowNum));
			daCensusProfile.setMaritalStatus(msm.mapRow(rs, rowNum));
			daCensusProfile.setSizeOfFamily(sofm.mapRow(rs, rowNum));
			daCensusProfile.setStructuralType(stm.mapRow(rs, rowNum));
			daCensusProfile.setWorkCommuteTransportation(wctm.mapRow(rs, rowNum));
			
			daCensusProfiles.add(daCensusProfile);
		}

		return daCensusProfiles;
	}
}
