package ca.uwaterloo.iss4e.demographics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.bytecode.opencsv.CSVReader;
import ca.uwaterloo.iss4e.demographics.csv.Da2011ProfileParser;
import ca.uwaterloo.iss4e.demographics.dao.census.AgeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.ChildrenAtHomeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.HouseholdSizeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.MaritalStatusDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.SizeOfFamilyDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.StructuralTypeDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.DisseminationAreaDAO;
import ca.uwaterloo.iss4e.demographics.model.census.DaCensusProfile;

public class DisseminationArea2011CensusImport {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final DisseminationAreaDAO daDAO = (DisseminationAreaDAO) context
			.getBean("disseminationAreaDAO");
	private static final AgeDAO ageDAO = (AgeDAO) context.getBean("ageDAO");
	private static final ChildrenAtHomeDAO childrenAtHomeDAO = (ChildrenAtHomeDAO) context
			.getBean("childrenAtHomeDAO");
	private static final HouseholdSizeDAO householdSizeDAO = (HouseholdSizeDAO) context
			.getBean("householdSizeDAO");
	private static final MaritalStatusDAO maritalStatusDAO = (MaritalStatusDAO) context
			.getBean("maritalStatusDAO");
	private static final SizeOfFamilyDAO sizeOfFamilyDAO = (SizeOfFamilyDAO) context
			.getBean("sizeOfFamilyDAO");
	private static final StructuralTypeDAO structuralTypeDAO = (StructuralTypeDAO) context
			.getBean("structuralTypeDAO");

	public static void main(String[] args) {
		csvParse();
	}

	private static void csvParse() {
		ArrayList<String> files = new ArrayList<String>();
		files.add("98-316-XWE2011001-1501-ALTA.csv");
		files.add("98-316-XWE2011001-1501-BC.csv");
		files.add("98-316-XWE2011001-1501-MAN.csv");
		files.add("98-316-XWE2011001-1501-NB.csv");
		files.add("98-316-XWE2011001-1501-NL.csv");
		files.add("98-316-XWE2011001-1501-NS.csv");
		files.add("98-316-XWE2011001-1501-NVT.csv");
		files.add("98-316-XWE2011001-1501-NWT.csv");
		files.add("98-316-XWE2011001-1501-ONT.csv");
		files.add("98-316-XWE2011001-1501-PEI.csv");
		files.add("98-316-XWE2011001-1501-QUE.csv");
		files.add("98-316-XWE2011001-1501-SASK.csv");
		files.add("98-316-XWE2011001-1501-YT.csv");

		CSVReader reader = null;
		try {
			for (String file : files) {
				reader = new CSVReader(new FileReader(file));
				Da2011ProfileParser da2011ProfileParser = new Da2011ProfileParser();
				HashSet<DaCensusProfile> censusProfiles = da2011ProfileParser
						.parseDaCensusProfiles(reader);

				for (DaCensusProfile profile : censusProfiles) {
					System.out.println("Updating Population for " + profile.getDaId());
					daDAO.updatePopulation(profile.getPopulation(), profile.getDaId());
					
					System.out
							.println("Inserting Age for " + profile.getDaId());
					ageDAO.insertAge(profile.getAge(), profile.getDaId());

					System.out.println("Inserting ChildrenAtHome for "
							+ profile.getDaId());
					childrenAtHomeDAO.insertChildrenAtHome(
							profile.getChildrenAtHome(), profile.getDaId());

					System.out.println("Inserting HouseholdSize for "
							+ profile.getDaId());
					householdSizeDAO.insertHouseholdSize(
							profile.getHouseholdSize(), profile.getDaId());

					System.out.println("Inserting MaritalStatus for "
							+ profile.getDaId());
					maritalStatusDAO.insertMaritalStatus(
							profile.getMaritalStatus(), profile.getDaId());

					System.out.println("Inserting SizeOfFamily for "
							+ profile.getDaId());
					sizeOfFamilyDAO.insertSizeOfFamily(
							profile.getSizeOfFamily(), profile.getDaId());

					System.out.println("Inserting StructuralType for "
							+ profile.getDaId());
					structuralTypeDAO.insertStructuralType(
							profile.getStructuralType(), profile.getDaId());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}