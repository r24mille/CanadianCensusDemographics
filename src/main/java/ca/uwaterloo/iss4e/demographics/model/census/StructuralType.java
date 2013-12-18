package ca.uwaterloo.iss4e.demographics.model.census;

public class StructuralType {
	private int samplePercentage;
	private double totalCount;
	private double singleDetachedCount;
	private double semiDetachedCount;
	private double rowHouseCount;
	private double apartmentDuplexCount;
	private double apartmentLessThan5StoriesCount;
	private double apartment5StoriesOrMoreCount;
	private double otherSingleAttachedCount;
	private double movableDwellingCount;

	public double getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}

	public int getSamplePercentage() {
		return samplePercentage;
	}

	public void setSamplePercentage(int samplePercentage) {
		this.samplePercentage = samplePercentage;
	}

	public double getSingleDetachedCount() {
		return singleDetachedCount;
	}

	public void setSingleDetachedCount(double singleDetachedCount) {
		this.singleDetachedCount = singleDetachedCount;
	}

	public double getSemiDetachedCount() {
		return semiDetachedCount;
	}

	public void setSemiDetachedCount(double semiDetachedCount) {
		this.semiDetachedCount = semiDetachedCount;
	}

	public double getRowHouseCount() {
		return rowHouseCount;
	}

	public void setRowHouseCount(double rowHouseCount) {
		this.rowHouseCount = rowHouseCount;
	}

	public double getApartmentDuplexCount() {
		return apartmentDuplexCount;
	}

	public void setApartmentDuplexCount(double apartmentDuplexCount) {
		this.apartmentDuplexCount = apartmentDuplexCount;
	}

	public double getApartmentLessThan5StoriesCount() {
		return apartmentLessThan5StoriesCount;
	}

	public void setApartmentLessThan5StoriesCount(
			double apartmentLessThan5StoriesCount) {
		this.apartmentLessThan5StoriesCount = apartmentLessThan5StoriesCount;
	}

	public double getApartment5StoriesOrMoreCount() {
		return apartment5StoriesOrMoreCount;
	}

	public void setApartment5StoriesOrMoreCount(
			double apartment5StoriesOrMoreCount) {
		this.apartment5StoriesOrMoreCount = apartment5StoriesOrMoreCount;
	}

	public double getOtherSingleAttachedCount() {
		return otherSingleAttachedCount;
	}

	public void setOtherSingleAttachedCount(double otherSingleAttachedCount) {
		this.otherSingleAttachedCount = otherSingleAttachedCount;
	}

	public double getMovableDwellingCount() {
		return movableDwellingCount;
	}

	public void setMovableDwellingCount(double movableDwellingCount) {
		this.movableDwellingCount = movableDwellingCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Structural Type\n");
		sb.append("Total Count: " + this.totalCount + "\n");
		sb.append("Single-detached: " + this.singleDetachedCount + "\n");
		sb.append("Apartment 5+ Stories: " + this.apartment5StoriesOrMoreCount
				+ "\n");
		sb.append("Movable Dwelling: " + this.movableDwellingCount + "\n");
		sb.append("Semi-detached: " + this.semiDetachedCount + "\n");
		sb.append("Row House: " + this.rowHouseCount + "\n");
		sb.append("Duplex: " + this.apartmentDuplexCount + "\n");
		sb.append("Aparment <5 Stories: " + this.apartmentLessThan5StoriesCount
				+ "\n");
		sb.append("Other: " + this.otherSingleAttachedCount + "\n");
		return sb.toString();
	}
}
