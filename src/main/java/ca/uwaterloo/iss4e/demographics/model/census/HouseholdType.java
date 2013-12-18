package ca.uwaterloo.iss4e.demographics.model.census;

public class HouseholdType {
	private int samplePercentage;
	private double totalCount;
	private double oneFamilyCount;
	private double multipleFamilyCount;
	private double nonFamilyCount;

	public int getSamplePercentage() {
		return samplePercentage;
	}

	public void setSamplePercentage(int samplePercentage) {
		this.samplePercentage = samplePercentage;
	}

	public double getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}

	public double getOneFamilyCount() {
		return oneFamilyCount;
	}

	public void setOneFamilyCount(double oneFamilyCount) {
		this.oneFamilyCount = oneFamilyCount;
	}

	public double getMultipleFamilyCount() {
		return multipleFamilyCount;
	}

	public void setMultipleFamilyCount(double multipleFamilyCount) {
		this.multipleFamilyCount = multipleFamilyCount;
	}

	public double getNonFamilyCount() {
		return nonFamilyCount;
	}

	public void setNonFamilyCount(double nonFamilyCount) {
		this.nonFamilyCount = nonFamilyCount;
	}

}
