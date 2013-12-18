package ca.uwaterloo.iss4e.demographics.model.geography;

public enum CensusDivisionType {
	CENSUS_DIVISION, COUNTY, DISTRICT, DISTRICT_MUNICIPALITY, REGIONAL_DISTRICT, REGION, REGIONAL_MUNICIPALITY, TERRITORY, UNITED_COUNTIES, UNKNOWN;
	
	public static CensusDivisionType fromString(String cdType) {
		if (cdType.equalsIgnoreCase("CDR")) {
			return CENSUS_DIVISION;
		} else if (cdType.equalsIgnoreCase("CT")
				|| cdType.equalsIgnoreCase("CTY")) {
			return COUNTY;
		} else if (cdType.equalsIgnoreCase("DIS")) {
			return DISTRICT;
		} else if (cdType.equalsIgnoreCase("DM")) {
			return DISTRICT_MUNICIPALITY;
		} else if (cdType.equalsIgnoreCase("RD")) {
			return REGIONAL_DISTRICT;
		} else if (cdType.equalsIgnoreCase("REG")) {
			return REGION;
		} else if (cdType.equalsIgnoreCase("RM")
				|| cdType.equalsIgnoreCase("MRC")) {
			return REGIONAL_MUNICIPALITY;
		} else if (cdType.equalsIgnoreCase("TE")
				|| cdType.equalsIgnoreCase("TÉ")) {
			return TERRITORY;
		} else if (cdType.equalsIgnoreCase("UC")) {
			return UNITED_COUNTIES;
		} else {
			return UNKNOWN;
		}
	}
}
