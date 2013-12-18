package ca.uwaterloo.iss4e.demographics.model.census;

public class Age {
	private int samplePercentage;
	private double totalCount;
	private double years0to4Count;
	private double years5to9Count;
	private double years10to14Count;
	private double years15to19Count;
	private double years20to24Count;
	private double years25to29Count;
	private double years30to34Count;
	private double years35to39Count;
	private double years40to44Count;
	private double years45to49Count;
	private double years50to54Count;
	private double years55to59Count;
	private double years60to64Count;
	private double years65to69Count;
	private double years70to74Count;
	private double years75to79Count;
	private double years80to84Count;
	private double years85andOverCount;
	private double medianAge;

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

	public double getYears0to4Count() {
		return years0to4Count;
	}

	public void setYears0to4Count(double years0to4Count) {
		this.years0to4Count = years0to4Count;
	}

	public double getYears5to9Count() {
		return years5to9Count;
	}

	public void setYears5to9Count(double years5to9Count) {
		this.years5to9Count = years5to9Count;
	}

	public double getYears10to14Count() {
		return years10to14Count;
	}

	public void setYears10to14Count(double years10to14Count) {
		this.years10to14Count = years10to14Count;
	}

	public double getYears15to19Count() {
		return years15to19Count;
	}

	public void setYears15to19Count(double years15to19Count) {
		this.years15to19Count = years15to19Count;
	}

	public double getYears20to24Count() {
		return years20to24Count;
	}

	public void setYears20to24Count(double years20to24Count) {
		this.years20to24Count = years20to24Count;
	}

	public double getYears25to29Count() {
		return years25to29Count;
	}

	public void setYears25to29Count(double years25to29Count) {
		this.years25to29Count = years25to29Count;
	}

	public double getYears30to34Count() {
		return years30to34Count;
	}

	public void setYears30to34Count(double years30to34Count) {
		this.years30to34Count = years30to34Count;
	}

	public double getYears35to39Count() {
		return years35to39Count;
	}

	public void setYears35to39Count(double years35to39Count) {
		this.years35to39Count = years35to39Count;
	}

	public double getYears40to44Count() {
		return years40to44Count;
	}

	public void setYears40to44Count(double years40to44Count) {
		this.years40to44Count = years40to44Count;
	}

	public double getYears45to49Count() {
		return years45to49Count;
	}

	public void setYears45to49Count(double years45to49Count) {
		this.years45to49Count = years45to49Count;
	}

	public double getYears50to54Count() {
		return years50to54Count;
	}

	public void setYears50to54Count(double years50to54Count) {
		this.years50to54Count = years50to54Count;
	}

	public double getYears55to59Count() {
		return years55to59Count;
	}

	public void setYears55to59Count(double years55to59Count) {
		this.years55to59Count = years55to59Count;
	}

	public double getYears60to64Count() {
		return years60to64Count;
	}

	public void setYears60to64Count(double years60to64Count) {
		this.years60to64Count = years60to64Count;
	}

	public double getYears65to69Count() {
		return years65to69Count;
	}

	public void setYears65to69Count(double years65to69Count) {
		this.years65to69Count = years65to69Count;
	}

	public double getYears70to74Count() {
		return years70to74Count;
	}

	public void setYears70to74Count(double years70to74Count) {
		this.years70to74Count = years70to74Count;
	}

	public double getYears75to79Count() {
		return years75to79Count;
	}

	public void setYears75to79Count(double years75to79Count) {
		this.years75to79Count = years75to79Count;
	}

	public double getYears80to84Count() {
		return years80to84Count;
	}

	public void setYears80to84Count(double years80to84Count) {
		this.years80to84Count = years80to84Count;
	}

	public double getYears85andOverCount() {
		return years85andOverCount;
	}

	public void setYears85andOverCount(double years85andOverCount) {
		this.years85andOverCount = years85andOverCount;
	}

	public double getMedianAge() {
		return medianAge;
	}

	public void setMedianAge(double medianAge) {
		this.medianAge = medianAge;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Age\n");
		sb.append("Total Count: " + this.totalCount + "\n");
		sb.append("0-4: " + this.years0to4Count + "\n");
		sb.append("5-9: " + this.years5to9Count + "\n");
		sb.append("10-14: " + this.years10to14Count + "\n");
		sb.append("15-19: " + this.years15to19Count + "\n");
		sb.append("20-24: " + this.years20to24Count + "\n");
		sb.append("25-29: " + this.years25to29Count + "\n");
		sb.append("30-34: " + this.years30to34Count + "\n");
		sb.append("35-39: " + this.years35to39Count + "\n");
		sb.append("40-44: " + this.years40to44Count + "\n");
		sb.append("45-49: " + this.years45to49Count + "\n");
		sb.append("50-54: " + this.years50to54Count + "\n");
		sb.append("55-59: " + this.years55to59Count + "\n");
		sb.append("60-64: " + this.years60to64Count + "\n");
		sb.append("65-69: " + this.years65to69Count + "\n");
		sb.append("70-74: " + this.years70to74Count + "\n");
		sb.append("75-79: " + this.years75to79Count + "\n");
		sb.append("80-84: " + this.years80to84Count + "\n");
		sb.append("85+: " + this.years85andOverCount + "\n");
		sb.append("Median: " + this.medianAge + "\n");
		return sb.toString();
	}
}
