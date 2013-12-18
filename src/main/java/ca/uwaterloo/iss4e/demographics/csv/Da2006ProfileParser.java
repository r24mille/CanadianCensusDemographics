package ca.uwaterloo.iss4e.demographics.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;
import ca.uwaterloo.iss4e.demographics.model.census.Age;
import ca.uwaterloo.iss4e.demographics.model.census.ChildrenAtHome;
import ca.uwaterloo.iss4e.demographics.model.census.DaCensusProfile;
import ca.uwaterloo.iss4e.demographics.model.census.Dwelling;
import ca.uwaterloo.iss4e.demographics.model.census.HouseholdIncome;
import ca.uwaterloo.iss4e.demographics.model.census.HouseholdSize;
import ca.uwaterloo.iss4e.demographics.model.census.HouseholdType;
import ca.uwaterloo.iss4e.demographics.model.census.LabourForceActivity;
import ca.uwaterloo.iss4e.demographics.model.census.MaritalStatus;
import ca.uwaterloo.iss4e.demographics.model.census.SizeOfFamily;
import ca.uwaterloo.iss4e.demographics.model.census.StructuralType;
import ca.uwaterloo.iss4e.demographics.model.census.WorkCommuteTransportation;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;
import ca.uwaterloo.iss4e.demographics.model.geography.Province;

public class Da2006ProfileParser {
	public HashMap<Integer, ArrayList<Object>> parseDaCensusData(
			CSVReader reader) {
		HashMap<Integer, DisseminationArea> indexedDAs = null;
		HashMap<Integer, DaCensusProfile> daCensusProfiles = null;

		String[] nextLine;
		try {
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine[0].contains("Profile of Diss")) {
					indexedDAs = this.parseGeographyRow(nextLine);
				} else if (nextLine[0].contains("Population, 2006 - 100% data")) {
					daCensusProfiles = this
							.initDaCensusProfilesWithPopulation(nextLine);
				} else if (nextLine[0]
						.contains("Total population by sex and age groups - 100% data")) {
					this.parseAges(reader, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total population 15 years and over by legal marital status - 100% data")) {
					this.parseMaritalStatus(reader, nextLine, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total number of census families in private households - 20% sample data")) {
					this.parseSizeOfFamily(reader, nextLine, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total number of children at home - 20% sample data")) {
					this.parseChildrenAtHome(reader, nextLine, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total number of occupied private dwellings - 20% sample data")) {
					this.parseDwelling(reader, nextLine, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total number of occupied private dwellings by structural type of dwelling - 100% data")) {
					this.parseStructuralType(reader, nextLine, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total number of private households by household size - 100% data")) {
					this.parseHouseholdSize(reader, nextLine, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total number of private households by household type - 20% sample data")) {
					this.parseHouseholdType(reader, nextLine, daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total population 15 years and over by labour force activity - 20% sample data")) {
					this.parseLabourForceActivity(reader, nextLine,
							daCensusProfiles);
				} else if (nextLine[0]
						.contains("Total employed labour force 15 years and over with usual place of work or no fixed workplace address by mode of transportation - 20% sample data")) {
					this.parseWorkCommuteTransportation(reader, nextLine,
							daCensusProfiles);
				} else if (nextLine[0]
						.contains("Household income in 2005 of private households - 20% sample data")) {
					this.parseHouseholdIncome(reader, nextLine,
							daCensusProfiles);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Post-processing, extract Dissemination Areas from entire results.
		 * Proper DAs in this dataset simply has a number for the census
		 * division name.
		 */
		HashMap<Integer, ArrayList<Object>> censusData = new HashMap<Integer, ArrayList<Object>>();
		for (int i = 1; i <= indexedDAs.size(); i++) {
			if (indexedDAs.get(i).getCensusDivisionName().matches("\\d{8}")) {
				int daId = Integer.parseInt(indexedDAs.get(i)
						.getCensusDivisionName());
				indexedDAs.get(i).setDaId(daId);
				daCensusProfiles.get(i).setDaId(daId);

				ArrayList<Object> dataItems = new ArrayList<Object>();
				dataItems.add(indexedDAs.get(i));
				dataItems.add(daCensusProfiles.get(i));

				censusData.put(daId, dataItems);
			}
		}

		return censusData;
	}

	private void parseHouseholdIncome(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init HouseholdIncome
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				HouseholdIncome householdIncome = new HouseholdIncome();
				householdIncome.setTotalCount(this.parseDouble(lineVal));
				householdIncome.setSamplePercentage(20);
				daCensusProfiles.get(i).setHouseholdIncome(householdIncome);
			} else if (lineVal.equals("-")) {
				HouseholdIncome householdIncome = new HouseholdIncome();
				householdIncome.setTotalCount(0);
				householdIncome.setSamplePercentage(20);
				daCensusProfiles.get(i).setHouseholdIncome(householdIncome);
			}
		}

		// Populate subsequent WorkCommuteTransportation lines
		try {
			for (int j = 0; j < 14; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						HouseholdIncome householdIncome = daCensusProfiles.get(
								i).getHouseholdIncome();

						switch (j) {
						case 0:
							householdIncome.setDollarsUnder10000Count(this
									.parseDouble(lineVal));
							break;
						case 1:
							householdIncome.setDollars10000to19999Count(this
									.parseDouble(lineVal));
							break;
						case 2:
							householdIncome.setDollars20000to29999Count(this
									.parseDouble(lineVal));
							break;
						case 3:
							householdIncome.setDollars30000to39999Count(this
									.parseDouble(lineVal));
							break;
						case 4:
							householdIncome.setDollars40000to49999Count(this
									.parseDouble(lineVal));
							break;
						case 5:
							householdIncome.setDollars50000to59999Count(this
									.parseDouble(lineVal));
							break;
						case 6:
							householdIncome.setDollars60000to69999Count(this
									.parseDouble(lineVal));
							break;
						case 7:
							householdIncome.setDollars70000to79999Count(this
									.parseDouble(lineVal));
							break;
						case 8:
							householdIncome.setDollars80000to89999Count(this
									.parseDouble(lineVal));
							break;
						case 9:
							householdIncome.setDollars90000to99999Count(this
									.parseDouble(lineVal));
							break;
						case 10:
							householdIncome.setDollars100000AndOverCount(this
									.parseDouble(lineVal));
							break;
						case 11:
							householdIncome.setDollarsMedian(this
									.parseDouble(lineVal));
							break;
						case 12:
							householdIncome.setDollarsAverage(this
									.parseDouble(lineVal));
							break;
						case 13:
							householdIncome.setDollarsStandardError(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseWorkCommuteTransportation(CSVReader reader,
			String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init WorkCommuteTransportation
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				WorkCommuteTransportation workCommuteTransport = new WorkCommuteTransportation();
				workCommuteTransport.setTotalCount(this.parseDouble(lineVal));
				workCommuteTransport.setSamplePercentage(20);
				daCensusProfiles.get(i).setWorkCommuteTransportation(
						workCommuteTransport);
			} else if (lineVal.equals("-")) {
				WorkCommuteTransportation workCommuteTransport = new WorkCommuteTransportation();
				workCommuteTransport.setTotalCount(0);
				workCommuteTransport.setSamplePercentage(20);
				daCensusProfiles.get(i).setWorkCommuteTransportation(
						workCommuteTransport);
			}
		}

		// Populate subsequent WorkCommuteTransportation lines
		try {
			for (int j = 0; j < 8; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						WorkCommuteTransportation workCommuteTransport = daCensusProfiles
								.get(i).getWorkCommuteTransportation();

						switch (j) {
						case 0:
							workCommuteTransport.setCarTruckVanDriver(this
									.parseDouble(lineVal));
							break;
						case 1:
							workCommuteTransport.setCarTruckVanPassenger(this
									.parseDouble(lineVal));
							break;
						case 2:
							workCommuteTransport.setPublicTransit(this
									.parseDouble(lineVal));
							break;
						case 3:
							workCommuteTransport.setWalked(this
									.parseDouble(lineVal));
							break;
						case 4:
							workCommuteTransport.setBicycle(this
									.parseDouble(lineVal));
							break;
						case 5:
							workCommuteTransport.setMotorcycle(this
									.parseDouble(lineVal));
							break;
						case 6:
							workCommuteTransport.setTaxicab(this
									.parseDouble(lineVal));
							break;
						case 7:
							workCommuteTransport.setOtherMethod(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseLabourForceActivity(CSVReader reader,
			String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init LabourForceActivity
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				LabourForceActivity labourForceActivity = new LabourForceActivity();
				labourForceActivity.setTotalCount(this.parseDouble(lineVal));
				labourForceActivity.setSamplePercentage(20);
				daCensusProfiles.get(i).setLabourForceActivity(
						labourForceActivity);
			} else if (lineVal.equals("-")) {
				LabourForceActivity labourForceActivity = new LabourForceActivity();
				labourForceActivity.setTotalCount(0);
				labourForceActivity.setSamplePercentage(20);
				daCensusProfiles.get(i).setLabourForceActivity(
						labourForceActivity);
			}
		}

		// Populate subsequent LabourForceActivity lines
		try {
			for (int j = 0; j < 4; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						LabourForceActivity labourForceActivity = daCensusProfiles
								.get(i).getLabourForceActivity();

						switch (j) {
						// case 0 "in labour force" inferred from
						// employed and unemployed
						case 1:
							labourForceActivity.setEmployedCount(this
									.parseDouble(lineVal));
							break;
						case 2:
							labourForceActivity.setUnemployedCount(this
									.parseDouble(lineVal));
							break;
						case 3:
							labourForceActivity.setNotInLabourForceCount(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseHouseholdType(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init HouseholdType
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				HouseholdType householdType = new HouseholdType();
				householdType.setTotalCount(this.parseDouble(lineVal));
				householdType.setSamplePercentage(20);
				daCensusProfiles.get(i).setHouseholdType(householdType);
			} else if (lineVal.equals("-")) {
				HouseholdType householdType = new HouseholdType();
				householdType.setTotalCount(0);
				householdType.setSamplePercentage(20);
				daCensusProfiles.get(i).setHouseholdType(householdType);
			}
		}

		// Populate subsequent HouseholdType lines
		try {
			for (int j = 0; j < 3; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						HouseholdType householdType = daCensusProfiles.get(i)
								.getHouseholdType();

						switch (j) {
						case 0:
							householdType.setOneFamilyCount(this
									.parseDouble(lineVal));
							break;
						case 1:
							householdType.setMultipleFamilyCount(this
									.parseDouble(lineVal));
							break;
						case 2:
							householdType.setNonFamilyCount(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseHouseholdSize(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init HouseholdSize
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				HouseholdSize householdSize = new HouseholdSize();
				householdSize.setTotalCount(this.parseDouble(lineVal));
				householdSize.setSamplePercentage(100);
				daCensusProfiles.get(i).setHouseholdSize(householdSize);
			} else if (lineVal.equals("-")) {
				HouseholdSize householdSize = new HouseholdSize();
				householdSize.setTotalCount(0);
				householdSize.setSamplePercentage(100);
				daCensusProfiles.get(i).setHouseholdSize(householdSize);
			}
		}

		// Populate subsequent HouseholdSize lines
		try {
			for (int j = 0; j < 7; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						HouseholdSize householdSize = daCensusProfiles.get(i)
								.getHouseholdSize();

						switch (j) {
						case 0:
							householdSize.setPersons1Count(this
									.parseDouble(lineVal));
							break;
						case 1:
							householdSize.setPersons2Count(this
									.parseDouble(lineVal));
							break;
						case 2:
							householdSize.setPersons3Count(this
									.parseDouble(lineVal));
							break;
						case 3:
							householdSize.setPersons4to5Count(this
									.parseDouble(lineVal));
							break;
						case 4:
							householdSize.setPersons6orMoreCount(this
									.parseDouble(lineVal));
							break;
						// case 5, no field for
						// "Numbers persons in private households"
						case 6:
							householdSize.setAverageNumberPersons(this
									.parseDouble(lineVal));
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseStructuralType(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init StructuralType
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				StructuralType structuralType = new StructuralType();
				structuralType.setTotalCount(this.parseDouble(lineVal));
				structuralType.setSamplePercentage(100);
				daCensusProfiles.get(i).setStructuralType(structuralType);
			} else if (lineVal.equals("-")) {
				StructuralType structuralType = new StructuralType();
				structuralType.setTotalCount(0);
				structuralType.setSamplePercentage(100);
				daCensusProfiles.get(i).setStructuralType(structuralType);
			}
		}

		// Populate subsequent StructuralType lines
		try {
			for (int j = 0; j < 8; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						StructuralType structuralType = daCensusProfiles.get(i)
								.getStructuralType();

						switch (j) {
						case 0:
							structuralType.setSingleDetachedCount(this
									.parseDouble(lineVal));
							break;
						case 1:
							structuralType.setSemiDetachedCount(this
									.parseDouble(lineVal));
							break;
						case 2:
							structuralType.setRowHouseCount(this
									.parseDouble(lineVal));
							break;
						case 3:
							structuralType.setApartmentDuplexCount(this
									.parseDouble(lineVal));
							break;
						case 4:
							structuralType.setApartment5StoriesOrMoreCount(this
									.parseDouble(lineVal));
							break;
						case 5:
							structuralType
									.setApartmentLessThan5StoriesCount(this
											.parseDouble(lineVal));
							break;
						case 6:
							structuralType.setOtherSingleAttachedCount(this
									.parseDouble(lineVal));
							break;
						case 7:
							structuralType.setMovableDwellingCount(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseDwelling(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init Dwelling
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				Dwelling dwelling = new Dwelling();
				dwelling.setTotalCount(this.parseDouble(lineVal));
				dwelling.setSamplePercentage(20);
				daCensusProfiles.get(i).setDwelling(dwelling);
			} else if (lineVal.equals("-")) {
				Dwelling dwelling = new Dwelling();
				dwelling.setTotalCount(0);
				dwelling.setSamplePercentage(20);
				daCensusProfiles.get(i).setDwelling(dwelling);
			}
		}

		// Populate subsequent Dwelling lines
		try {
			for (int j = 0; j < 20; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						Dwelling dwelling = daCensusProfiles.get(i)
								.getDwelling();

						switch (j) {
						case 0:
							dwelling.setAverageNumberOfRooms(this
									.parseDouble(lineVal));
							break;
						case 1:
							dwelling.setAverageNumberOfBedrooms(this
									.parseDouble(lineVal));
							break;
						// case 2 repeats "total dwellings", use default
						case 3:
							dwelling.setOwnedCount(this.parseDouble(lineVal));
							break;
						case 4:
							dwelling.setRentedCount(this.parseDouble(lineVal));
							break;
						case 5:
							dwelling.setBandHousingCount(this
									.parseDouble(lineVal));
							break;
						// case 6 repeats "total dwellings", use default
						case 7:
							dwelling.setRegularMaintenanceOnlyCount(this
									.parseDouble(lineVal));
							break;
						case 8:
							dwelling.setMinorRepairsCount(this
									.parseDouble(lineVal));
							break;
						case 9:
							dwelling.setMajorRepairsCount(this
									.parseDouble(lineVal));
							break;
						// case 10 repeats "total dwellings", use default
						case 11:
							dwelling.setConstructionBefore1946Count(this
									.parseDouble(lineVal));
							break;
						case 12:
							dwelling.setConstruction1946to1960Count(this
									.parseDouble(lineVal));
							break;
						case 13:
							dwelling.setConstruction1961to1970Count(this
									.parseDouble(lineVal));
							break;
						case 14:
							dwelling.setConstruction1971to1980Count(this
									.parseDouble(lineVal));
							break;
						case 15:
							dwelling.setConstruction1981to1985Count(this
									.parseDouble(lineVal));
							break;
						case 16:
							dwelling.setConstruction1986to1990Count(this
									.parseDouble(lineVal));
							break;
						case 17:
							dwelling.setConstruction1991to1995Count(this
									.parseDouble(lineVal));
							break;
						case 18:
							dwelling.setConstruction1996to2000Count(this
									.parseDouble(lineVal));
							break;
						case 19:
							dwelling.setConstruction2001to2006Count(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseChildrenAtHome(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init ChildrenAtHome
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				ChildrenAtHome childrenAtHome = new ChildrenAtHome();
				childrenAtHome.setTotalCount(this.parseDouble(lineVal));
				childrenAtHome.setSamplePercentage(20);
				daCensusProfiles.get(i).setChildrenAtHome(childrenAtHome);
			} else if (lineVal.equals("-")) {
				ChildrenAtHome childrenAtHome = new ChildrenAtHome();
				childrenAtHome.setTotalCount(0);
				childrenAtHome.setSamplePercentage(20);
				daCensusProfiles.get(i).setChildrenAtHome(childrenAtHome);
			}
		}

		// Populate subsequent ChildrenAtHome lines
		try {
			for (int j = 0; j < 6; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						ChildrenAtHome childrenAtHome = daCensusProfiles.get(i)
								.getChildrenAtHome();

						switch (j) {
						case 0:
							childrenAtHome.setYears5andUnderCount(this
									.parseDouble(lineVal));
							break;
						case 1:
							childrenAtHome.setYears6to14Count(this
									.parseDouble(lineVal));
							break;
						case 2:
							childrenAtHome.setYears15to17Count(this
									.parseDouble(lineVal));
							break;
						case 3:
							childrenAtHome.setYears18to24Count(this
									.parseDouble(lineVal));
							break;
						case 4:
							childrenAtHome.setYears25andOverCount(this
									.parseDouble(lineVal));
							break;
						case 5:
							childrenAtHome.setAveragePerFamily(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseSizeOfFamily(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		// Use totals from currentLine to init SizeOfFamily
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				SizeOfFamily sizeOfFamily = new SizeOfFamily();
				sizeOfFamily.setTotalCount(this.parseDouble(lineVal));
				sizeOfFamily.setSamplePercentage(20);
				daCensusProfiles.get(i).setSizeOfFamily(sizeOfFamily);
			} else if (lineVal.equals("-")) {
				SizeOfFamily sizeOfFamily = new SizeOfFamily();
				sizeOfFamily.setTotalCount(0);
				sizeOfFamily.setSamplePercentage(20);
				daCensusProfiles.get(i).setSizeOfFamily(sizeOfFamily);
			}
		}

		// Populate subsequent SizeOfFamily lines
		try {
			for (int j = 0; j < 4; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						SizeOfFamily sizeOfFamily = daCensusProfiles.get(i)
								.getSizeOfFamily();

						switch (j) {
						case 0:
							sizeOfFamily.setPersons2Count(this
									.parseDouble(lineVal));
							break;
						case 1:
							sizeOfFamily.setPersons3Count(this
									.parseDouble(lineVal));
							break;
						case 2:
							sizeOfFamily.setPersons4Count(this
									.parseDouble(lineVal));
							break;
						case 3:
							sizeOfFamily.setPersons5orMoreCount(this
									.parseDouble(lineVal));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Value of "Married" taken as "Married or Common Law" since the definition
	 * may have changed slightly between 2006 and 2011.
	 * 
	 * @param reader
	 * @param currentLine
	 * @param daCensusProfiles
	 */
	private void parseMaritalStatus(CSVReader reader, String[] currentLine,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {

		// Use totals from currentLine to init MaritalStatus
		for (int i = 0; i < currentLine.length; i++) {
			String lineVal = currentLine[i];

			if (this.isNumeric(lineVal)) {
				MaritalStatus maritalStatus = new MaritalStatus();
				maritalStatus.setTotalCount(this.parseDouble(lineVal));
				maritalStatus.setSamplePercentage(100);
				daCensusProfiles.get(i).setMaritalStatus(maritalStatus);
			} else if (lineVal.equals("-")) {
				MaritalStatus maritalStatus = new MaritalStatus();
				maritalStatus.setTotalCount(0);
				maritalStatus.setSamplePercentage(100);
				daCensusProfiles.get(i).setMaritalStatus(maritalStatus);
			}
		}

		// Populate subsequent MaritalStatus lines
		try {
			for (int j = 0; j < 5; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						MaritalStatus maritalStatus = daCensusProfiles.get(i)
								.getMaritalStatus();

						switch (j) {
						case 0:
							maritalStatus.setNeverMarriedCount(this
									.parseDouble(lineVal));
							break;
						case 1:
							maritalStatus.setMarriedOrCommonlawCount(this
									.parseDouble(lineVal));
							break;
						case 2:
							maritalStatus.setSeparatedCount(this
									.parseDouble(lineVal));
							break;
						case 3:
							maritalStatus.setDivorcedCount(this
									.parseDouble(lineVal));
							break;
						case 4:
							maritalStatus.setWidowedCount(this
									.parseDouble(lineVal));
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 2011 census combines the male and female age demographics. Combine the
	 * male and female demographics into a single 2006 Age demographic.
	 * 
	 * @param reader
	 * @param daCensusProfiles
	 */
	private void parseAges(CSVReader reader,
			HashMap<Integer, DaCensusProfile> daCensusProfiles) {
		try {
			for (int j = 0; j < 38; j++) {
				String[] nextLine = reader.readNext();

				for (int i = 0; i < nextLine.length; i++) {
					String lineVal = nextLine[i];

					if (this.isNumeric(lineVal)) {
						if (j == 0) { // Init Age with male total
							Age age = new Age();
							age.setTotalCount(this.parseDouble(lineVal));
							age.setSamplePercentage(100);
							daCensusProfiles.get(i).setAge(age);
						} else if (j == 19) { // female total
							Age age = daCensusProfiles.get(i).getAge();
							double total = age.getTotalCount();
							age.setTotalCount(total + this.parseDouble(lineVal));
						} else {
							Age age = daCensusProfiles.get(i).getAge();
							double lineValDouble = this.parseDouble(lineVal);
							if (j == 1) { // male 0-4
								age.setYears0to4Count(lineValDouble);
							} else if (j == 2) { // male 5-9
								age.setYears5to9Count(lineValDouble);
							} else if (j == 3) { // male 10-14
								age.setYears10to14Count(lineValDouble);
							} else if (j == 4) { // male 15-19
								age.setYears15to19Count(lineValDouble);
							} else if (j == 5) { // male 20-24
								age.setYears20to24Count(lineValDouble);
							} else if (j == 6) { // male 25-29
								age.setYears25to29Count(lineValDouble);
							} else if (j == 7) { // male 30-34
								age.setYears30to34Count(lineValDouble);
							} else if (j == 8) { // male 35-39
								age.setYears35to39Count(lineValDouble);
							} else if (j == 9) { // male 40-44
								age.setYears40to44Count(lineValDouble);
							} else if (j == 10) { // male 45-49
								age.setYears45to49Count(lineValDouble);
							} else if (j == 11) { // male 50-54
								age.setYears50to54Count(lineValDouble);
							} else if (j == 12) { // male 55-59
								age.setYears55to59Count(lineValDouble);
							} else if (j == 13) { // male 60-64
								age.setYears60to64Count(lineValDouble);
							} else if (j == 14) { // male 65-69
								age.setYears65to69Count(lineValDouble);
							} else if (j == 15) { // male 70-74
								age.setYears70to74Count(lineValDouble);
							} else if (j == 16) { // male 75-79
								age.setYears75to79Count(lineValDouble);
							} else if (j == 17) { // male 80-84
								age.setYears80to84Count(lineValDouble);
							} else if (j == 18) { // male 85+
								age.setYears85andOverCount(lineValDouble);
							} else if (j == 20) { // female 0-4
								double crnt = age.getYears0to4Count();
								age.setYears0to4Count(crnt + lineValDouble);
							} else if (j == 21) { // female 5-9
								double crnt = age.getYears5to9Count();
								age.setYears5to9Count(crnt + lineValDouble);
							} else if (j == 22) { // female 10-14
								double crnt = age.getYears10to14Count();
								age.setYears10to14Count(crnt + lineValDouble);
							} else if (j == 23) { // female 15-19
								double crnt = age.getYears15to19Count();
								age.setYears15to19Count(crnt + lineValDouble);
							} else if (j == 24) { // female 20-24
								double crnt = age.getYears20to24Count();
								age.setYears20to24Count(crnt + lineValDouble);
							} else if (j == 25) { // female 25-29
								double crnt = age.getYears25to29Count();
								age.setYears25to29Count(crnt + lineValDouble);
							} else if (j == 26) { // female 30-34
								double crnt = age.getYears30to34Count();
								age.setYears30to34Count(crnt + lineValDouble);
							} else if (j == 27) { // female 35-39
								double crnt = age.getYears35to39Count();
								age.setYears35to39Count(crnt + lineValDouble);
							} else if (j == 28) { // female 40-44
								double crnt = age.getYears40to44Count();
								age.setYears40to44Count(crnt + lineValDouble);
							} else if (j == 29) { // female 45-49
								double crnt = age.getYears45to49Count();
								age.setYears45to49Count(crnt + lineValDouble);
							} else if (j == 30) { // female 50-54
								double crnt = age.getYears50to54Count();
								age.setYears50to54Count(crnt + lineValDouble);
							} else if (j == 31) { // female 55-59
								double crnt = age.getYears55to59Count();
								age.setYears55to59Count(crnt + lineValDouble);
							} else if (j == 32) { // female 60-64
								double crnt = age.getYears60to64Count();
								age.setYears60to64Count(crnt + lineValDouble);
							} else if (j == 33) { // female 65-69
								double crnt = age.getYears65to69Count();
								age.setYears65to69Count(crnt + lineValDouble);
							} else if (j == 34) { // female 70-74
								double crnt = age.getYears70to74Count();
								age.setYears70to74Count(crnt + lineValDouble);
							} else if (j == 35) { // female 75-79
								double crnt = age.getYears75to79Count();
								age.setYears75to79Count(crnt + lineValDouble);
							} else if (j == 36) { // female 80-84
								double crnt = age.getYears80to84Count();
								age.setYears80to84Count(crnt + lineValDouble);
							} else if (j == 37) { // female 85+
								double crnt = age.getYears85andOverCount();
								age.setYears85andOverCount(crnt + lineValDouble);
							}
						}
					} else if (lineVal.equals("-") && j == 0) {
						Age age = new Age();
						age.setTotalCount(0);
						age.setSamplePercentage(100);
						daCensusProfiles.get(i).setAge(age);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private HashMap<Integer, DaCensusProfile> initDaCensusProfilesWithPopulation(
			String[] nextLine) {
		HashMap<Integer, DaCensusProfile> daCensusProfiles = new HashMap<Integer, DaCensusProfile>();

		for (int i = 0; i < nextLine.length; i++) {
			String lineVal = nextLine[i];

			if (this.isNumeric(lineVal)) {
				// Set daId to index until post-processing
				DaCensusProfile daCensusProfile = new DaCensusProfile(i);
				double population = this.parseDouble(lineVal);
				daCensusProfile.setPopulation((int) Math.round(population));
				daCensusProfiles.put(i, daCensusProfile);
			} else if (lineVal.equals("-")) {
				// Set daId to index until post-processing
				DaCensusProfile daCensusProfile = new DaCensusProfile(i);
				daCensusProfile.setPopulation(0);
				daCensusProfiles.put(i, daCensusProfile);
			}
		}

		return daCensusProfiles;
	}

	/**
	 * Parses the DA ID from the first row of CSV data. It is the number
	 * contained within parenthesis.
	 * 
	 * @param nextLine
	 */
	private HashMap<Integer, DisseminationArea> parseGeographyRow(
			String[] nextLine) {
		HashMap<Integer, DisseminationArea> indexedDAs = new HashMap<Integer, DisseminationArea>();

		for (int i = 0; i < nextLine.length; i++) {
			String lineVal = nextLine[i];

			// That the line matches the assumed DA ID format
			if (lineVal.matches(".*\\(\\d*\\).*")) {
				String censusDivisionName = this
						.parseCensusDivisionName(lineVal);
				String dataQualityFlags = this.parseDataQualityFlags(lineVal);
				int provinceId = this.parseProvinceId(lineVal);

				DisseminationArea da = new DisseminationArea();
				da.setCensusDivisionName(censusDivisionName);
				da.setDataQualityFlags(dataQualityFlags);
				da.setProvinceId(provinceId);
				da.setProvinceName(Province.getProvinceName(provinceId));
				da.setDaId(i); // Set daId to index until post-processing

				indexedDAs.put(i, da);
			}
		}

		return indexedDAs;
	}

	private int parseProvinceId(String lineVal) {
		int leftParenIndex = lineVal.lastIndexOf("(");
		return Integer.parseInt(lineVal.substring((leftParenIndex + 1),
				(leftParenIndex + 3)));
	}

	private long parseDaUID(String lineVal) {
		int leftParenIndex = lineVal.lastIndexOf("(");
		int rightParenIndex = lineVal.lastIndexOf(")");
		return Long.parseLong(lineVal.substring((leftParenIndex + 1),
				rightParenIndex));
	}

	/**
	 * <p>
	 * Final portion after DA UID is a set of 5 data quality flags described by
	 * the Beyond 20/20 document.
	 * </p>
	 * <ol>
	 * <li>(0XXXX) Incomplete enumeration flag</li>
	 * <li>(X0XXX) 100% data quality flag</li>
	 * <li>(XX0XX) Population and dwelling counts error flag</li>
	 * <li>(XXX0X) 20% sample data quality flag</li>
	 * <li>(XXXX0) 2001 adjusted population flag</li>
	 * </ol>
	 * <p>
	 * See Beyond 20/20 document for a detailed description of each flag.
	 * </p>
	 * <p>
	 * Capturing data from CSV and dumping into the database for now.
	 * </p>
	 * 
	 * @param lineVal
	 * @return
	 */
	private String parseDataQualityFlags(String lineVal) {
		int length = lineVal.length();
		return lineVal.substring(length - 5);
	}

	/**
	 * First portion before DA UID is assumed to be the census division's name.
	 * 
	 * @param lineVal
	 * @return censusDivisionName
	 */
	private String parseCensusDivisionName(String lineVal) {
		int leftParenIndex = lineVal.lastIndexOf("(");
		return lineVal.substring(0, leftParenIndex).trim();
	}

	/**
	 * match a number with optional '-' and decimal.
	 * 
	 * @param string
	 * @return boolean
	 */
	private boolean isNumeric(String string) {
		return this.stripNumber(string).matches("-?\\d+(\\.\\d+)?"); //
	}

	private String stripNumber(String string) {
		return string.replace(",", "");
	}

	private double parseDouble(String string) {
		return Double.parseDouble(this.stripNumber(string));
	}
}
