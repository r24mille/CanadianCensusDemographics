package ca.uwaterloo.iss4e.demographics.model.census;

public class ChildrenAtHome {
	private int samplePercentage;
	private double totalCount;
	private double averagePerFamily;
	private double years5andUnderCount;
	private double years6to14Count;
	private double years15to17Count;
	private double years18to24Count;
	private double years25andOverCount;

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

	public double getAveragePerFamily() {
		return averagePerFamily;
	}

	public void setAveragePerFamily(double averagePerFamily) {
		this.averagePerFamily = averagePerFamily;
	}

	public double getYears5andUnderCount() {
		return years5andUnderCount;
	}

	public void setYears5andUnderCount(double years5andUnderCount) {
		this.years5andUnderCount = years5andUnderCount;
	}

	public double getYears6to14Count() {
		return years6to14Count;
	}

	public void setYears6to14Count(double years6to14Count) {
		this.years6to14Count = years6to14Count;
	}

	public double getYears15to17Count() {
		return years15to17Count;
	}

	public void setYears15to17Count(double years15to17Count) {
		this.years15to17Count = years15to17Count;
	}

	public double getYears18to24Count() {
		return years18to24Count;
	}

	public void setYears18to24Count(double years18to24Count) {
		this.years18to24Count = years18to24Count;
	}

	public double getYears25andOverCount() {
		return years25andOverCount;
	}

	public void setYears25andOverCount(double years25andOverCount) {
		this.years25andOverCount = years25andOverCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Children at Home\n");
		sb.append("Total: " + this.totalCount + "\n");
		sb.append("0-5: " + this.years5andUnderCount + "\n");
		sb.append("6-14: " + this.years6to14Count + "\n");
		sb.append("15-17: " + this.years15to17Count + "\n");
		sb.append("18-24: " + this.years18to24Count + "\n");
		sb.append("25+: " + this.years25andOverCount + "\n");
		sb.append("Average number children: " + this.averagePerFamily + "\n");
		return sb.toString();
	}
}
