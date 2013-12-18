package ca.uwaterloo.iss4e.demographics.model.geography;

public class Province {
	public static final int NEWFOUNDLAND_PROV_ID = 10;
	public static final int PEI_PROV_ID = 11;
	public static final int NOVA_SCOTIA_PROV_ID = 12;
	public static final int NEW_BRUNS_PROV_ID = 13;
	public static final int QUEBEC_PROV_ID = 24;
	public static final int ONTARIO_PROV_ID = 35;
	public static final int MANITOBA_PROV_ID = 46;
	public static final int SASKATCHEWAN_PROV_ID = 47;
	public static final int ALBERTA_PROV_ID = 48;
	public static final int BC_PROV_ID = 59;
	public static final int YUKON_PROV_ID = 60;
	public static final int NWT_PROV_ID = 61;
	public static final int NUNAVUT_PROV_ID = 62;

	public static String getProvinceName(int provinceId) {
		switch (provinceId) {
		case NEWFOUNDLAND_PROV_ID:
			return "Newfoundland and Labrador / Terre-Neuve-et-Labrador";
		case PEI_PROV_ID:
			return "Prince Edward Island / Île-du-Prince-Édouard";
		case NOVA_SCOTIA_PROV_ID:
			return "Nova Scotia / Nouvelle-Écosse";
		case NEW_BRUNS_PROV_ID:
			return "New Brunswick / Nouveau-Brunswick";
		case QUEBEC_PROV_ID:
			return "Quebec / Québec";
		case ONTARIO_PROV_ID:
			return "Ontario";
		case MANITOBA_PROV_ID:
			return "Manitoba";
		case SASKATCHEWAN_PROV_ID:
			return "Saskatchewan";
		case ALBERTA_PROV_ID:
			return "Alberta";
		case BC_PROV_ID:
			return "British Columbia / Colombie-Britannique";
		case YUKON_PROV_ID:
			return "Yukon";
		case NWT_PROV_ID:
			return "Northwest Territories / Territoires du Nord-Ouest";
		case NUNAVUT_PROV_ID:
			return "Nunavut";
		default:
			return "Unknown";
		}
	}
}
