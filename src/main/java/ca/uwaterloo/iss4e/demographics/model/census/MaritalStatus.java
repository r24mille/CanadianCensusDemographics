package ca.uwaterloo.iss4e.demographics.model.census;

public class MaritalStatus {
	private int samplePercentage;
	private double totalCount;
	private double neverMarriedCount;
	private double marriedOrCommonlawCount;
	private double separatedCount;
	private double divorcedCount;
	private double widowedCount;

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

	public double getNeverMarriedCount() {
		return neverMarriedCount;
	}

	public void setNeverMarriedCount(double neverMarriedCount) {
		this.neverMarriedCount = neverMarriedCount;
	}

	public double getMarriedOrCommonlawCount() {
		return marriedOrCommonlawCount;
	}

	public void setMarriedOrCommonlawCount(double marriedOrCommonlawCount) {
		this.marriedOrCommonlawCount = marriedOrCommonlawCount;
	}

	public double getSeparatedCount() {
		return separatedCount;
	}

	public void setSeparatedCount(double separatedCount) {
		this.separatedCount = separatedCount;
	}

	public double getDivorcedCount() {
		return divorcedCount;
	}

	public void setDivorcedCount(double divocedCount) {
		this.divorcedCount = divocedCount;
	}

	public double getWidowedCount() {
		return widowedCount;
	}

	public void setWidowedCount(double widowedCount) {
		this.widowedCount = widowedCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Marital Status\n");
		sb.append("Total : " + this.totalCount + "\n");
		sb.append("Married/common law: " + this.marriedOrCommonlawCount + "\n");
		sb.append("Never Married: " + this.neverMarriedCount + "\n");
		sb.append("Separated: " + this.separatedCount + "\n");
		sb.append("Divorced: " + this.divorcedCount + "\n");
		sb.append("Widowed: " + this.widowedCount + "\n");
		return sb.toString();
	}
}
