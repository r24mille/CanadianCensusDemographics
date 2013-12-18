package ca.uwaterloo.iss4e.demographics.util;

public class CensusSchemaUtil {
	public static final String CENSUS_2006_DA_SCHEMA = "census2006da";
	public static final String CENSUS_2011_DA_SCHEMA = "census2011da";
	public static final String CENSUS_2011_FSA_SCHEMA = "census2011fsa";
	
	public static String getPkColumnName(Object pk) {
		if (pk instanceof Integer) {
			return "da_id";
		} else if (pk instanceof String) {
			return "fsa_code";
		} else {
			return null;
		}
	}

	public static String getSchemaFromPk(Object pk) {
		if (pk instanceof Integer) {
			return CENSUS_2006_DA_SCHEMA;
		} else if (pk instanceof String) {
			return CENSUS_2011_FSA_SCHEMA;
		} else {
			return null;
		}
	}
}
