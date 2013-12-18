package ca.uwaterloo.iss4e.demographics.model.census;

public class SizeOfFamily {
	private int samplePercentage;
	private double totalCount;
	private double persons2Count;
	private double persons3Count;
	private double persons4Count;
	private double persons5orMoreCount;

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

	public double getPersons5orMoreCount() {
		return persons5orMoreCount;
	}

	public void setPersons5orMoreCount(double persons5orMoreCount) {
		this.persons5orMoreCount = persons5orMoreCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Size of Family\n");
		sb.append("Total Count: " + this.totalCount + "\n");
		sb.append("2: " + this.persons2Count + "\n");
		sb.append("3: " + this.persons3Count + "\n");
		sb.append("4: " + this.persons4Count + "\n");
		sb.append("5+: " + this.persons5orMoreCount + "\n");
		return sb.toString();
	}
}
