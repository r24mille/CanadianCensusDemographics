package ca.uwaterloo.iss4e.demographics.model.census;

public class LabourForceActivity {
	private int samplePercentage;
	private double totalCount;
	private double employedCount;
	private double unemployedCount;
	private double notInLabourForceCount;

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

	public double getEmployedCount() {
		return employedCount;
	}

	public void setEmployedCount(double employedCount) {
		this.employedCount = employedCount;
	}

	public double getUnemployedCount() {
		return unemployedCount;
	}

	public void setUnemployedCount(double unemployedCount) {
		this.unemployedCount = unemployedCount;
	}

	public double getNotInLabourForceCount() {
		return notInLabourForceCount;
	}

	public void setNotInLabourForceCount(double notInLabourForceCount) {
		this.notInLabourForceCount = notInLabourForceCount;
	}

}
