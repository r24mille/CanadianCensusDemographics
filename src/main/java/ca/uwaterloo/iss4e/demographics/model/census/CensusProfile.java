package ca.uwaterloo.iss4e.demographics.model.census;


public interface CensusProfile {
	public boolean isPopulated();
	
	public Age getAge();
	
	public void setAge(Age age);
	
	public ChildrenAtHome getChildrenAtHome();

	public void setChildrenAtHome(ChildrenAtHome childrenAtHome);

	public Dwelling getDwelling();

	public void setDwelling(Dwelling dwelling);

	public HouseholdIncome getHouseholdIncome();

	public void setHouseholdIncome(HouseholdIncome householdIncome);

	public HouseholdSize getHouseholdSize();

	public void setHouseholdSize(HouseholdSize householdSize);

	public HouseholdType getHouseholdType();

	public void setHouseholdType(HouseholdType householdType);

	public LabourForceActivity getLabourForceActivity();

	public void setLabourForceActivity(LabourForceActivity labourForceActivity);

	public MaritalStatus getMaritalStatus();

	public void setMaritalStatus(MaritalStatus maritalStatus);

	public SizeOfFamily getSizeOfFamily();

	public void setSizeOfFamily(SizeOfFamily sizeOfFamily);

	public StructuralType getStructuralType();

	public void setStructuralType(StructuralType structuralType);
	
	public WorkCommuteTransportation getWorkCommuteTransportation();
	
	public void setWorkCommuteTransportation(WorkCommuteTransportation workCommuteTransportation);

}
