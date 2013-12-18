package ca.uwaterloo.iss4e.demographics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.bytecode.opencsv.CSVReader;
import ca.uwaterloo.iss4e.demographics.csv.Da2006ProfileParser;
import ca.uwaterloo.iss4e.demographics.dao.census.AgeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.ChildrenAtHomeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.DwellingDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.HouseholdIncomeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.HouseholdSizeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.HouseholdTypeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.LabourForceActivityDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.MaritalStatusDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.SizeOfFamilyDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.StructuralTypeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.WorkCommuteTransportationDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.DisseminationAreaDAO;
import ca.uwaterloo.iss4e.demographics.model.census.DaCensusProfile;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;

public class DisseminationArea2006CensusImport {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final DisseminationAreaDAO daDAO = (DisseminationAreaDAO) context
			.getBean("disseminationAreaDAO");
	private static final AgeDAO ageDAO = (AgeDAO) context.getBean("ageDAO");
	private static final ChildrenAtHomeDAO childrenAtHomeDAO = (ChildrenAtHomeDAO) context
			.getBean("childrenAtHomeDAO");
	private static final DwellingDAO dwellingDAO = (DwellingDAO) context
			.getBean("dwellingDAO");
	private static final HouseholdIncomeDAO householdIncomeDAO = (HouseholdIncomeDAO) context
			.getBean("householdIncomeDAO");
	private static final HouseholdSizeDAO householdSizeDAO = (HouseholdSizeDAO) context
			.getBean("householdSizeDAO");
	private static final HouseholdTypeDAO householdTypeDAO = (HouseholdTypeDAO) context
			.getBean("householdTypeDAO");
	private static final LabourForceActivityDAO labourForceActivityDAO = (LabourForceActivityDAO) context
			.getBean("labourForceActivityDAO");
	private static final MaritalStatusDAO maritalStatusDAO = (MaritalStatusDAO) context
			.getBean("maritalStatusDAO");
	private static final SizeOfFamilyDAO sizeOfFamilyDAO = (SizeOfFamilyDAO) context
			.getBean("sizeOfFamilyDAO");
	private static final StructuralTypeDAO structuralTypeDAO = (StructuralTypeDAO) context
			.getBean("structuralTypeDAO");
	private static final WorkCommuteTransportationDAO workCommuteTransportationDAO = (WorkCommuteTransportationDAO) context
			.getBean("workCommuteTransportationDAO");

	public static void main(String[] args) {
		csvParse();
	}

	private static void csvParse() {
		ArrayList<String> files = new ArrayList<String>();
		files.add("2006alta.csv");
		files.add("2006bc.csv");
		files.add("2006man.csv");
		files.add("2006nb.csv");
		files.add("2006nl.csv");
		files.add("2006ns.csv");
		files.add("2006nt.csv");
		files.add("2006nvt.csv");
		files.add("2006ont(pt1).ca.uwaterloo.iss4e.demographics.csv");
		files.add("2006ont(pt2).ca.uwaterloo.iss4e.demographics.csv");
		files.add("2006ont(pt3).ca.uwaterloo.iss4e.demographics.csv");
		files.add("2006ont(pt4).ca.uwaterloo.iss4e.demographics.csv");
		files.add("2006pei.csv");
		files.add("2006que(pt1).ca.uwaterloo.iss4e.demographics.csv");
		files.add("2006que(pt2).ca.uwaterloo.iss4e.demographics.csv");
		files.add("2006sask.csv");
		files.add("2006yt.csv");

		HashSet<Integer> existingDaIds = daDAO.getDaIds();

		CSVReader reader = null;
		try {
			for (String file : files) {
				reader = new CSVReader(new FileReader(file));
				Da2006ProfileParser da2006ProfileParser = new Da2006ProfileParser();
				HashMap<Integer, ArrayList<Object>> censusData = da2006ProfileParser
						.parseDaCensusData(reader);

				Collection<ArrayList<Object>> values = censusData.values();

				for (ArrayList<Object> dataItems : values) {
					for (Object dataItem : dataItems) {
						if (dataItem instanceof DisseminationArea) {
							DisseminationArea da = (DisseminationArea) dataItem;

							if (!existingDaIds.contains(da.getDaId())) {
								System.out.println("Adding DA " + da.getDaId());
								daDAO.insertDisseminationArea(da);
							}
						} else if (dataItem instanceof DaCensusProfile) {
							DaCensusProfile profile = (DaCensusProfile) dataItem;
							if (!existingDaIds.contains(profile.getDaId())) {
								System.out.println("Updating Population for "
										+ profile.getDaId());
								daDAO.updatePopulation(profile.getPopulation(),
										profile.getDaId());

								System.out.println("Inserting Age for "
										+ profile.getDaId());
								ageDAO.insertAge(profile.getAge(),
										profile.getDaId());

								System.out
										.println("Inserting ChildrenAtHome for "
												+ profile.getDaId());
								childrenAtHomeDAO.insertChildrenAtHome(
										profile.getChildrenAtHome(),
										profile.getDaId());

								System.out.println("Inserting Dwelling for "
										+ profile.getDaId());
								dwellingDAO.insertDwelling(
										profile.getDwelling(),
										profile.getDaId());

								System.out
										.println("Inserting HouseholdIncome for "
												+ profile.getDaId());
								householdIncomeDAO.insertHouseholdIncome(
										profile.getHouseholdIncome(),
										profile.getDaId());

								System.out
										.println("Inserting HouseholdSize for "
												+ profile.getDaId());
								householdSizeDAO.insertHouseholdSize(
										profile.getHouseholdSize(),
										profile.getDaId());

								System.out
										.println("Inserting HouseholdType for "
												+ profile.getDaId());
								householdTypeDAO.insertHouseholdType(
										profile.getHouseholdType(),
										profile.getDaId());

								System.out
										.println("Inserting LabourForceActivity for "
												+ profile.getDaId());
								labourForceActivityDAO
										.insertLabourForceActivity(profile
												.getLabourForceActivity(),
												profile.getDaId());

								System.out
										.println("Inserting MaritalStatus for "
												+ profile.getDaId());
								maritalStatusDAO.insertMaritalStatus(
										profile.getMaritalStatus(),
										profile.getDaId());

								System.out
										.println("Inserting SizeOfFamily for "
												+ profile.getDaId());
								sizeOfFamilyDAO.insertSizeOfFamily(
										profile.getSizeOfFamily(),
										profile.getDaId());

								System.out
										.println("Inserting StructuralType for "
												+ profile.getDaId());
								structuralTypeDAO.insertStructuralType(
										profile.getStructuralType(),
										profile.getDaId());

								System.out
										.println("Inserting WorkCommuteTransportation for "
												+ profile.getDaId());
								workCommuteTransportationDAO
										.insertWorkCommuteTransportation(
												profile.getWorkCommuteTransportation(),
												profile.getDaId());
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}