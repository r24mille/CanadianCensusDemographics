package ca.uwaterloo.iss4e.demographics.model.census;

public class HouseholdSize {
	private int samplePercentage;
	private double totalCount;
	private double averageNumberPersons;
	private double persons1Count;
	private double persons2Count;
	private double persons3Count;
	private double persons4Count; // 2011 format
	private double persons5Count; // 2011 format
	private double persons4to5Count; // 2006 format
	private double persons6orMoreCount;

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

	public double getAverageNumberPersons() {
		return averageNumberPersons;
	}

	public void setAverageNumberPersons(double averageNumberPersons) {
		this.averageNumberPersons = averageNumberPersons;
	}

	public double getPersons1Count() {
		return persons1Count;
	}

	public void setPersons1Count(double persons1Count) {
		this.persons1Count = persons1Count;
	}

	public double getPersons2Count() {
		return persons2Count;
	}

	public void setPersons2Count(double persons2Count) {
		this.persons2Count = persons2Count;
	}

	public double getPersons3Count() {
		return persons3Count;
	}

	public void setPersons3Count(double persons3Count) {
		this.persons3Count = persons3Count;
	}

	public double getPersons4Count() {
		return persons4Count;
	}

	public void setPersons4Count(double persons4Count) {
		this.persons4Count = persons4Count;
	}

	public double getPersons5Count() {
		return persons5Count;
	}

	public void setPersons5Count(double persons5Count) {
		this.persons5Count = persons5Count;
	}

	/**
	 * <p>
	 * The persons4to5Count field is often set explicitly for 2006 census data.
	 * The 2011 census measures the persons4Count and persons5Count
	 * individually.
	 * </p>
	 * 
	 * <p>
	 * If field is not set (eg. 2011 survey) the value can be inferred by
	 * returning (persons4Count + persons5Count). However, this does not set the
	 * persons4to5Count field.
	 * </p>
	 * 
	 * @return persons4to5Count
	 */
	public double getPersons4to5Count() {
		if (persons4to5Count > 0) {
			return persons4to5Count;
		} else {
			return (persons4Count + persons5Count);
		}
	}

	public void setPersons4to5Count(double persons4to5Count) {
		this.persons4to5Count = persons4to5Count;
	}

	public double getPersons6orMoreCount() {
		return persons6orMoreCount;
	}

	public void setPersons6orMoreCount(double persons6orMoreCount) {
		this.persons6orMoreCount = persons6orMoreCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Household Size\n");
		sb.append("Total: " + this.totalCount + "\n");
		sb.append("1 Person: " + this.persons1Count + "\n");
		sb.append("2 People: " + this.persons2Count + "\n");
		sb.append("3 People: " + this.persons3Count + "\n");
		sb.append("4 People: " + this.persons4Count + "\n");
		sb.append("5 People: " + this.persons5Count + "\n");
		sb.append("4-5 People: " + this.persons4to5Count + "\n");
		sb.append("6+ People: " + this.persons6orMoreCount + "\n");
		sb.append("Average Size: " + this.averageNumberPersons + "\n");
		return sb.toString();
	}
}
