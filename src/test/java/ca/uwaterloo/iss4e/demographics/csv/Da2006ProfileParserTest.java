package ca.uwaterloo.iss4e.demographics.csv;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import ca.uwaterloo.iss4e.demographics.csv.Da2006ProfileParser;
import ca.uwaterloo.iss4e.demographics.model.census.Age;
import ca.uwaterloo.iss4e.demographics.model.census.CensusProfile;
import ca.uwaterloo.iss4e.demographics.model.census.ChildrenAtHome;
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
import au.com.bytecode.opencsv.CSVReader;

public class Da2006ProfileParserTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testParseDaCensusData() {
		try {
			CSVReader reader = new CSVReader(new FileReader("2006pei.csv"));
			Da2006ProfileParser da2006ProfileParser = new Da2006ProfileParser();
			HashMap<Integer, ArrayList<Object>> censusProfiles = da2006ProfileParser
					.parseDaCensusData(reader);

			// Check data of DA ID 11030078
			ArrayList<Object> daData = censusProfiles.get(11030078);

			for (Object dataItem : daData) {
				if (dataItem instanceof CensusProfile) {
					CensusProfile censusProfile = (CensusProfile) dataItem;
					
					// Check that ages are summed properly
					Age age = censusProfile.getAge();
					assertEquals("Test 0-4 sum", 10, age.getYears0to4Count(), 0);
					assertEquals("Test 5-9 sum", 25, age.getYears5to9Count(), 0);
					assertEquals("Test 10-14 sum", 35, age.getYears10to14Count(), 0);
					assertEquals("Test 15-19 sum", 55, age.getYears15to19Count(), 0);
					assertEquals("Test 20-24 sum", 35, age.getYears20to24Count(), 0);
					assertEquals("Test 25-29 sum", 20, age.getYears25to29Count(), 0);
					assertEquals("Test 30-34 sum", 20, age.getYears30to34Count(), 0);
					assertEquals("Test 35-39 sum", 15, age.getYears35to39Count(), 0);
					assertEquals("Test 40-44 sum", 25, age.getYears40to44Count(), 0);
					assertEquals("Test 45-49 sum", 55, age.getYears45to49Count(), 0);
					assertEquals("Test 50-54 sum", 50, age.getYears50to54Count(), 0);
					assertEquals("Test 55-59 sum", 40, age.getYears55to59Count(), 0);
					assertEquals("Test 60-64 sum", 30, age.getYears60to64Count(), 0);
					assertEquals("Test 65-69 sum", 30, age.getYears65to69Count(), 0);
					assertEquals("Test 70-74 sum", 20, age.getYears70to74Count(), 0);
					assertEquals("Test 75-79 sum", 5, age.getYears75to79Count(), 0);
					assertEquals("Test 80-84 sum", 10, age.getYears80to84Count(), 0);
					assertEquals("Test 85+ sum", 5, age.getYears85andOverCount(), 0);
					
					// Check MaritalStatus
					MaritalStatus maritalStatus = censusProfile.getMaritalStatus();
					assertEquals("Total marital status", 405, maritalStatus.getTotalCount(), 0);
					assertEquals("Never married", 140, maritalStatus.getNeverMarriedCount(), 0);
					assertEquals("Married", 215, maritalStatus.getMarriedOrCommonlawCount(), 0);
					assertEquals("Separated", 0, maritalStatus.getSeparatedCount(), 0);
					assertEquals("Divorced", 20, maritalStatus.getDivorcedCount(), 0);
					assertEquals("Widowed", 30, maritalStatus.getWidowedCount(), 0);
					
					// Check SizeOfFamily
					SizeOfFamily sizeOfFamily = censusProfile.getSizeOfFamily();
					assertEquals("Total number families", 145, sizeOfFamily.getTotalCount(), 0);
					assertEquals("2 persons", 70, sizeOfFamily.getPersons2Count(), 0);
					assertEquals("3 persons", 35, sizeOfFamily.getPersons3Count(), 0);
					assertEquals("4 persons", 10, sizeOfFamily.getPersons4Count(), 0);
					assertEquals("5+ persons", 30, sizeOfFamily.getPersons5orMoreCount(), 0);
					
					// Check ChildrenAtHome
					ChildrenAtHome childrenAtHome = censusProfile.getChildrenAtHome();
					assertEquals("total number children at home", 165, childrenAtHome.getTotalCount(), 0);
					assertEquals("under 6 years", 20, childrenAtHome.getYears5andUnderCount(), 0);
					assertEquals("6-14 years", 55, childrenAtHome.getYears6to14Count(), 0);
					assertEquals("15-17 years", 30, childrenAtHome.getYears15to17Count(), 0);
					assertEquals("18-24 years", 45, childrenAtHome.getYears18to24Count(), 0);
					assertEquals("25+ years", 15, childrenAtHome.getYears25andOverCount(), 0);
					assertEquals("avg number children at home", 1.1, childrenAtHome.getAveragePerFamily(), 0);
				
					// Check Dwelling
					Dwelling dwelling = censusProfile.getDwelling();
					assertEquals("total occupied dwellings", 170, dwelling.getTotalCount(), 0);
					assertEquals("owned", 155, dwelling.getOwnedCount(), 0);
					assertEquals("rented", 20, dwelling.getRentedCount(), 0);
					assertEquals("band housing", 0, dwelling.getBandHousingCount(), 0);
					assertEquals("regular maintenance", 130, dwelling.getRegularMaintenanceOnlyCount(), 0);
					assertEquals("minor repairs", 50, dwelling.getMinorRepairsCount(), 0);
					assertEquals("major repairs", 0, dwelling.getMajorRepairsCount(), 0);
					assertEquals("constructed before 1946", 45, dwelling.getConstructionBefore1946Count(), 0);
					assertEquals("constructed 1946-1960", 15, dwelling.getConstruction1946to1960Count(), 0);
					assertEquals("constructed 1961-1970", 30, dwelling.getConstruction1961to1970Count(), 0);
					assertEquals("constructed 1971-1980", 45, dwelling.getConstruction1971to1980Count(), 0);
					assertEquals("constructed 1981-1985", 20, dwelling.getConstruction1981to1985Count(), 0);
					assertEquals("constructed 1986-1990", 10, dwelling.getConstruction1986to1990Count(), 0);
					assertEquals("constructed 1991-1995", 0, dwelling.getConstruction1991to1995Count(), 0);
					assertEquals("constructed 1996-2000", 10, dwelling.getConstruction1996to2000Count(), 0);
					assertEquals("constructed 2001-2006", 0, dwelling.getConstruction2001to2006Count(), 0);
				
					// Check StructuralType
					StructuralType structuralType = censusProfile.getStructuralType();
					assertEquals("total number by structural type", 175, structuralType.getTotalCount(), 0);
					assertEquals("single-detached", 170, structuralType.getSingleDetachedCount(), 0);
					assertEquals("semi-detached", 0, structuralType.getSemiDetachedCount(), 0);
					assertEquals("row house", 0, structuralType.getRowHouseCount(), 0);
					assertEquals("apartment, duplex", 0, structuralType.getApartmentDuplexCount(), 0);
					assertEquals("apartment, 5+ stories", 0, structuralType.getApartment5StoriesOrMoreCount(), 0);
					assertEquals("apartment, <5 stories", 0, structuralType.getApartmentLessThan5StoriesCount(), 0);
					assertEquals("other single-attached", 0, structuralType.getOtherSingleAttachedCount(), 0);
					assertEquals("movable dwelling", 5, structuralType.getMovableDwellingCount(), 0);
				
					// Check HouseholdSize
					HouseholdSize householdSize = censusProfile.getHouseholdSize();
					assertEquals("total number by household size", 175, householdSize.getTotalCount(), 0);
					assertEquals("1 person", 30, householdSize.getPersons1Count(), 0);
					assertEquals("2 persons", 60, householdSize.getPersons2Count(), 0);
					assertEquals("3 persons", 45, householdSize.getPersons3Count(), 0);
					assertEquals("4-5 persons", 35, householdSize.getPersons4to5Count(), 0);
					assertEquals("6+ perons", 5, householdSize.getPersons6orMoreCount(), 0);
					assertEquals("avg persons", 2.7, householdSize.getAverageNumberPersons(), 0);
					
					// Check HouseholdType
					HouseholdType householdType = censusProfile.getHouseholdType();
					assertEquals("total number by household type", 170, householdType.getTotalCount(), 0);
					assertEquals("one-family", 140, householdType.getOneFamilyCount(), 0);
					assertEquals("multiple-family", 0, householdType.getMultipleFamilyCount(), 0);
					assertEquals("non-family", 30, householdType.getNonFamilyCount(), 0);
					
					// Check LabourForceActivity
					LabourForceActivity labourForceActivity = censusProfile.getLabourForceActivity();
					assertEquals("total population by labour force acivity", 395, labourForceActivity.getTotalCount(), 0);
					assertEquals("employed", 185, labourForceActivity.getEmployedCount(), 0);
					assertEquals("unemployed", 35, labourForceActivity.getUnemployedCount(), 0);
					assertEquals("not in labour force", 170, labourForceActivity.getNotInLabourForceCount(), 0);

					// Check WorkCommuteTransportation
					WorkCommuteTransportation workCommuteTransportation = censusProfile.getWorkCommuteTransportation();
					assertEquals("total labour force by commute transportation", 170, workCommuteTransportation.getTotalCount(), 0);
					assertEquals("car/truck/van driver", 155, workCommuteTransportation.getCarTruckVanDriver(), 0);
					assertEquals("car/truck/van passenger", 10, workCommuteTransportation.getCarTruckVanPassenger(), 0);
					assertEquals("public transit", 0, workCommuteTransportation.getPublicTransit(), 0);
					assertEquals("walked", 0, workCommuteTransportation.getWalked(), 0);
					assertEquals("bicycle", 10, workCommuteTransportation.getBicycle(), 0);
					assertEquals("motorcycle", 0, workCommuteTransportation.getMotorcycle(), 0);
					assertEquals("taxi", 0, workCommuteTransportation.getTaxicab(), 0);
					assertEquals("other", 10, workCommuteTransportation.getOtherMethod(), 0);

					// Check HouseholdIncome
					HouseholdIncome householdIncome = censusProfile.getHouseholdIncome();
					assertEquals("total by household income", 175, householdIncome.getTotalCount(), 0);
					assertEquals("under $10,000", 15, householdIncome.getDollarsUnder10000Count(), 0);
					assertEquals("$10,000 - $19,999", 20, householdIncome.getDollars10000to19999Count(), 0);
					assertEquals("$20,000 - $29,999", 0, householdIncome.getDollars20000to29999Count(), 0);
					assertEquals("$30,000 - $39,999", 30, householdIncome.getDollars30000to39999Count(), 0);
					assertEquals("$40,000 - $49,999", 40, householdIncome.getDollars40000to49999Count(), 0);
					assertEquals("$50,000 - $59,999", 15, householdIncome.getDollars50000to59999Count(), 0);
					assertEquals("$60,000 - $69,999", 15, householdIncome.getDollars60000to69999Count(), 0);
					assertEquals("$70,000 - $79,999", 10, householdIncome.getDollars70000to79999Count(), 0);
					assertEquals("$80,000 - $89,999", 15, householdIncome.getDollars80000to89999Count(), 0);
					assertEquals("$90,000 - $99,999", 0, householdIncome.getDollars90000to99999Count(), 0);
					assertEquals("$100,000+", 10, householdIncome.getDollars100000AndOverCount(), 0);
					assertEquals("median income", 45624, householdIncome.getDollarsMedian(), 0);
					assertEquals("avg income", 49734, householdIncome.getDollarsAverage(), 0);
					assertEquals("standard error $", 4425, householdIncome.getDollarsStandardError(), 0);

				}
			}

		} catch (FileNotFoundException e) {
			fail("Could not find CSV file.");
		}

	}

}
