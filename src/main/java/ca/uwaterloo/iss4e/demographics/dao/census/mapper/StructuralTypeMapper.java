package ca.uwaterloo.iss4e.demographics.dao.census.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ca.uwaterloo.iss4e.demographics.model.census.StructuralType;

public class StructuralTypeMapper implements RowMapper<StructuralType> {
	public StructuralType mapRow(ResultSet rs, int rowNum) throws SQLException {
		StructuralType structuralType = new StructuralType();
		structuralType.setSamplePercentage(rs
				.getInt("structural_type.sample_percentage"));
		structuralType.setTotalCount(rs.getDouble("structural_type.total"));
		structuralType.setSingleDetachedCount(rs
				.getDouble("structural_type.single_detached"));
		structuralType.setSemiDetachedCount(rs
				.getDouble("structural_type.semi_detached"));
		structuralType.setRowHouseCount(rs
				.getDouble("structural_type.row_house"));
		structuralType.setApartmentDuplexCount(rs
				.getDouble("structural_type.apartment_duplex"));
		structuralType.setApartmentLessThan5StoriesCount(rs
				.getDouble("structural_type.apartment_less_than_five_stories"));
		structuralType.setApartment5StoriesOrMoreCount(rs
				.getDouble("structural_type.apartment_five_stories_or_more"));
		structuralType.setOtherSingleAttachedCount(rs
				.getDouble("structural_type.other_single_attached"));
		structuralType.setMovableDwellingCount(rs
				.getDouble("structural_type.movable_dwelling"));
		return structuralType;
	}
}
