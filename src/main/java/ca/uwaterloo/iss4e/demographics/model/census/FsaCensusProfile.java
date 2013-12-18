package ca.uwaterloo.iss4e.demographics.model.census;

public class FsaCensusProfile implements CensusProfile {
	private String fsaCode;
	private int population;
	private Age age;
	private ChildrenAtHome childrenAtHome;
	private Dwelling dwelling;
	private HouseholdIncome householdIncome;
	private HouseholdSize householdSize;
	private HouseholdType householdType;
	private LabourForceActivity labourForceActivity;
	private MaritalStatus maritalStatus;
	private SizeOfFamily sizeOfFamily;
	private StructuralType structuralType;
	private WorkCommuteTransportation workCommuteTransportation;

	public FsaCensusProfile(String fsaCode) {
		this.fsaCode = fsaCode;
	}

	/**
	 * <p>
	 * Checks the minimal number of demographics common to both the 2006 and
	 * 2011 census.
	 * </p>
	 * <p>
	 * This is a "good enough" indicator of whether the census profile has been
	 * populated.
	 * </p>
	 * 
	 * @return boolean
	 */
	public boolean isPopulated() {
		if (this.age != null && this.childrenAtHome != null
				&& this.householdSize != null && this.maritalStatus != null
				&& this.sizeOfFamily != null && this.structuralType != null) {
			return true;
		} else {
			return false;
		}
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	public String getFsaCode() {
		return fsaCode;
	}

	public void setFsaCode(String fsaCode) {
		this.fsaCode = fsaCode;
	}

	public ChildrenAtHome getChildrenAtHome() {
		return childrenAtHome;
	}

	public void setChildrenAtHome(ChildrenAtHome childrenAtHome) {
		this.childrenAtHome = childrenAtHome;
	}

	public Dwelling getDwelling() {
		return dwelling;
	}

	public void setDwelling(Dwelling dwelling) {
		this.dwelling = dwelling;
	}

	public HouseholdIncome getHouseholdIncome() {
		return householdIncome;
	}

	public void setHouseholdIncome(HouseholdIncome householdIncome) {
		this.householdIncome = householdIncome;
	}

	public HouseholdSize getHouseholdSize() {
		return householdSize;
	}

	public void setHouseholdSize(HouseholdSize householdSize) {
		this.householdSize = householdSize;
	}

	public HouseholdType getHouseholdType() {
		return householdType;
	}

	public void setHouseholdType(HouseholdType householdType) {
		this.householdType = householdType;
	}

	public LabourForceActivity getLabourForceActivity() {
		return labourForceActivity;
	}

	public void setLabourForceActivity(LabourForceActivity labourForceActivity) {
		this.labourForceActivity = labourForceActivity;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public SizeOfFamily getSizeOfFamily() {
		return sizeOfFamily;
	}

	public void setSizeOfFamily(SizeOfFamily sizeOfFamily) {
		this.sizeOfFamily = sizeOfFamily;
	}

	public StructuralType getStructuralType() {
		return structuralType;
	}

	public void setStructuralType(StructuralType structuralType) {
		this.structuralType = structuralType;
	}

	public WorkCommuteTransportation getWorkCommuteTransportation() {
		return workCommuteTransportation;
	}

	public void setWorkCommuteTransportation(
			WorkCommuteTransportation workCommuteTransportation) {
		this.workCommuteTransportation = workCommuteTransportation;
	}
}
