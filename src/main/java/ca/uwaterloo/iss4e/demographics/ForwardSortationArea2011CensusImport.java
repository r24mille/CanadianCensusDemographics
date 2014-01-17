package ca.uwaterloo.iss4e.demographics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.bytecode.opencsv.CSVReader;
import ca.uwaterloo.iss4e.demographics.csv.Fsa2011ProfileParser;
import ca.uwaterloo.iss4e.demographics.dao.census.AgeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.ChildrenAtHomeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.HouseholdSizeDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.MaritalStatusDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.SizeOfFamilyDAO;
import ca.uwaterloo.iss4e.demographics.dao.census.StructuralTypeDAO;
import ca.uwaterloo.iss4e.demographics.dao.geography.ForwardSortationAreaDAO;
import ca.uwaterloo.iss4e.demographics.model.census.FsaCensusProfile;

public class ForwardSortationArea2011CensusImport {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static final ForwardSortationAreaDAO forwardSortationAreaDAO = (ForwardSortationAreaDAO) context
			.getBean("forwardSortationAreaDAO");
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
		String file = "98-316-XWE2011001-1601.csv";
		csvParse(file);
	}

	private static void csvParse(String file) {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(file));
			Fsa2011ProfileParser fsa2011ProfileParser = new Fsa2011ProfileParser();
			HashSet<FsaCensusProfile> censusProfiles = fsa2011ProfileParser
					.parseFsaCensusProfiles(reader);
			
			for (FsaCensusProfile profile : censusProfiles) {
				System.out.println("Updating Population for " + profile.getFsaCode());
				forwardSortationAreaDAO.updatePopulation(profile.getPopulation(), profile.getFsaCode());
				
				System.out.println("Inserting Age for " + profile.getFsaCode());
				ageDAO.insertAge(profile.getAge(), profile.getFsaCode());
				
				System.out.println("Inserting ChildrenAtHome for " + profile.getFsaCode());
				childrenAtHomeDAO.insertChildrenAtHome(profile.getChildrenAtHome(), profile.getFsaCode());
				
				System.out.println("Inserting HouseholdSize for " + profile.getFsaCode());
				householdSizeDAO.insertHouseholdSize(profile.getHouseholdSize(), profile.getFsaCode());
				
				System.out.println("Inserting MaritalStatus for " + profile.getFsaCode());
				maritalStatusDAO.insertMaritalStatus(profile.getMaritalStatus(), profile.getFsaCode());
				
				System.out.println("Inserting SizeOfFamily for " + profile.getFsaCode());
				sizeOfFamilyDAO.insertSizeOfFamily(profile.getSizeOfFamily(), profile.getFsaCode());
				
				System.out.println("Inserting StructuralType for " + profile.getFsaCode());
				structuralTypeDAO.insertStructuralType(profile.getStructuralType(), profile.getFsaCode());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
