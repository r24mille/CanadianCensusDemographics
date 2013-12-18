package ca.uwaterloo.iss4e.demographics.csv;

import java.io.IOException;
import java.util.HashSet;

import au.com.bytecode.opencsv.CSVReader;
import ca.uwaterloo.iss4e.demographics.model.census.Age;
import ca.uwaterloo.iss4e.demographics.model.census.ChildrenAtHome;
import ca.uwaterloo.iss4e.demographics.model.census.FsaCensusProfile;
import ca.uwaterloo.iss4e.demographics.model.census.HouseholdSize;
import ca.uwaterloo.iss4e.demographics.model.census.MaritalStatus;
import ca.uwaterloo.iss4e.demographics.model.census.SizeOfFamily;
import ca.uwaterloo.iss4e.demographics.model.census.StructuralType;

public class Fsa2011ProfileParser {
	public HashSet<FsaCensusProfile> parseFsaCensusProfiles(CSVReader reader) {
		HashSet<FsaCensusProfile> fsaCensusProfiles = new HashSet<FsaCensusProfile>();
		String[] nextLine;
		String activeFsaCode = "";
		try {
			FsaCensusProfile fsaCensusProfile = null;
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				try {
					String fsaCode = nextLine[0];
					if (nextLine[0].equals("Geo_Code")) {
						// Do nothing
					} else if (!activeFsaCode.equals(fsaCode)) {
						if (!activeFsaCode.equals("")) {
							if (fsaCensusProfile.isPopulated()) {
								fsaCensusProfiles.add(fsaCensusProfile);
							}
						}
						activeFsaCode = fsaCode;
						System.out.println(activeFsaCode);
						fsaCensusProfile = new FsaCensusProfile(activeFsaCode);
						
						if (nextLine[2]
								.equals("Population in 2011")) {
							fsaCensusProfile.setPopulation(this.parsePopulation(nextLine[4]));
							System.out.println("Population: " + fsaCensusProfile.getPopulation());
						}
					} else if (nextLine[2]
							.equals("Total population by age groups")) {
						fsaCensusProfile.setAge(this.parseAge(reader,
								nextLine[4]));
						System.out.println(fsaCensusProfile.getAge());

					} else if (nextLine[2]
							.equals("Total population 15 years and over by marital status")) {
						fsaCensusProfile.setMaritalStatus(this.parseMaritalStatus(reader, nextLine[4]));
						System.out.println(fsaCensusProfile.getMaritalStatus());
					} else if (nextLine[2].equals("Total number of census families in private households") && fsaCensusProfile.getSizeOfFamily() == null) {
						fsaCensusProfile.setSizeOfFamily(this.parseSizeOfFamily(reader, nextLine[4]));
						System.out.println(fsaCensusProfile.getSizeOfFamily());
					} else if (nextLine[2].equals("Total children in census families in private households")) {
						fsaCensusProfile.setChildrenAtHome(this.parseChildrenAtHome(reader, nextLine[4]));
						System.out.println(fsaCensusProfile.getChildrenAtHome());
					} else if (nextLine[2].equals("Total number of occupied private dwellings by structural type of dwelling")) {
						fsaCensusProfile.setStructuralType(this.parseStructuralType(reader, nextLine[4]));
						System.out.println(fsaCensusProfile.getStructuralType());
					} else if (nextLine[2].equals("Total number of private households by household size")) {
						fsaCensusProfile.setHouseholdSize(this.parseHouseholdSize(reader, nextLine[4]));
						System.out.println(fsaCensusProfile.getHouseholdSize());
					}
				} catch (NumberFormatException e) {
					System.out
							.println("Could not parse cell into number, skipping.");
				}
			}
			
			// Add final profile
			if (fsaCensusProfile.isPopulated()) {
				fsaCensusProfiles.add(fsaCensusProfile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fsaCensusProfiles;
	}
	
	private int parsePopulation(String total) {
		int population = Integer.parseInt(total);
		return population;
	}

	private Age parseAge(CSVReader reader, String total)
			throws NumberFormatException, IOException {
		Age age = new Age();
		age.setTotalCount(Double.parseDouble(total));
		age.setYears0to4Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears5to9Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears10to14Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears15to19Count(Double.parseDouble(reader.readNext()[4]));
		reader.readNext(); // 15
		reader.readNext(); // 16
		reader.readNext(); // 17
		reader.readNext(); // 18
		reader.readNext(); // 19
		age.setYears20to24Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears25to29Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears30to34Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears35to39Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears40to44Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears45to49Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears50to54Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears55to59Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears60to64Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears65to69Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears70to74Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears75to79Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears80to84Count(Double.parseDouble(reader.readNext()[4]));
		age.setYears85andOverCount(Double.parseDouble(reader.readNext()[4]));
		age.setMedianAge(Double.parseDouble(reader.readNext()[4]));
		return age;
	}

	private MaritalStatus parseMaritalStatus(CSVReader reader, String total) throws NumberFormatException, IOException {
		MaritalStatus maritalStatus = new MaritalStatus();
		maritalStatus.setTotalCount(Double.parseDouble(total));
		maritalStatus.setMarriedOrCommonlawCount(Double.parseDouble(reader.readNext()[4]));
		reader.readNext(); // married
		reader.readNext(); // common law
		reader.readNext(); // not married (summary)
		maritalStatus.setNeverMarriedCount(Double.parseDouble(reader.readNext()[4]));
		maritalStatus.setSeparatedCount(Double.parseDouble(reader.readNext()[4]));
		maritalStatus.setDivorcedCount(Double.parseDouble(reader.readNext()[4]));
		maritalStatus.setWidowedCount(Double.parseDouble(reader.readNext()[4]));
		return maritalStatus;
	}
	
	private SizeOfFamily parseSizeOfFamily(CSVReader reader, String total) throws NumberFormatException, IOException {
		SizeOfFamily sizeOfFamily = new SizeOfFamily();
		sizeOfFamily.setTotalCount(Double.parseDouble(total));
		sizeOfFamily.setPersons2Count(Double.parseDouble(reader.readNext()[4]));
		sizeOfFamily.setPersons3Count(Double.parseDouble(reader.readNext()[4]));
		sizeOfFamily.setPersons4Count(Double.parseDouble(reader.readNext()[4]));
		sizeOfFamily.setPersons5orMoreCount(Double.parseDouble(reader.readNext()[4]));
		return sizeOfFamily;
	}

	private ChildrenAtHome parseChildrenAtHome(CSVReader reader, String total) throws NumberFormatException, IOException {
		ChildrenAtHome childrenAtHome = new ChildrenAtHome();
		childrenAtHome.setTotalCount(Double.parseDouble(total));
		childrenAtHome.setYears5andUnderCount(Double.parseDouble(reader.readNext()[4]));
		childrenAtHome.setYears6to14Count(Double.parseDouble(reader.readNext()[4]));
		childrenAtHome.setYears15to17Count(Double.parseDouble(reader.readNext()[4]));
		childrenAtHome.setYears18to24Count(Double.parseDouble(reader.readNext()[4]));
		childrenAtHome.setYears25andOverCount(Double.parseDouble(reader.readNext()[4]));
		childrenAtHome.setAveragePerFamily(Double.parseDouble(reader.readNext()[4]));
		return childrenAtHome;
	}
	
	private StructuralType parseStructuralType(CSVReader reader, String total) throws NumberFormatException, IOException {
		StructuralType structuralType = new StructuralType();
		structuralType.setTotalCount(Double.parseDouble(total));
		structuralType.setSingleDetachedCount(Double.parseDouble(reader.readNext()[4]));
		structuralType.setApartment5StoriesOrMoreCount(Double.parseDouble(reader.readNext()[4]));
		structuralType.setMovableDwellingCount(Double.parseDouble(reader.readNext()[4]));
		reader.readNext(); // other dwelling (summary)
		structuralType.setSemiDetachedCount(Double.parseDouble(reader.readNext()[4]));
		structuralType.setRowHouseCount(Double.parseDouble(reader.readNext()[4]));
		structuralType.setApartmentDuplexCount(Double.parseDouble(reader.readNext()[4]));
		structuralType.setApartmentLessThan5StoriesCount(Double.parseDouble(reader.readNext()[4]));
		structuralType.setOtherSingleAttachedCount(Double.parseDouble(reader.readNext()[4]));
		return structuralType;
	}
	
	private HouseholdSize parseHouseholdSize(CSVReader reader, String total) throws NumberFormatException, IOException {
		HouseholdSize householdSize = new HouseholdSize();
		householdSize.setTotalCount(Double.parseDouble(total));
		householdSize.setPersons1Count(Double.parseDouble(reader.readNext()[4]));
		householdSize.setPersons2Count(Double.parseDouble(reader.readNext()[4]));
		householdSize.setPersons3Count(Double.parseDouble(reader.readNext()[4]));
		householdSize.setPersons4Count(Double.parseDouble(reader.readNext()[4]));
		householdSize.setPersons5Count(Double.parseDouble(reader.readNext()[4]));
		householdSize.setPersons6orMoreCount(Double.parseDouble(reader.readNext()[4]));
		reader.readNext(); // Number of persons in private households
		householdSize.setAverageNumberPersons(Double.parseDouble(reader.readNext()[4]));
		return householdSize;
	}
}
