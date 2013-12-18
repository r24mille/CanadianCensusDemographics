package ca.uwaterloo.iss4e.demographics.model.census;

public class WorkCommuteTransportation {
	private int samplePercentage;
	private double carTruckVanDriver;
	private double carTruckVanPassenger;
	private double publicTransit;
	private double walked;
	private double bicycle;
	private double motorcycle;
	private double taxicab;
	private double otherMethod;
	private double totalCount;

	public int getSamplePercentage() {
		return samplePercentage;
	}

	public void setSamplePercentage(int samplePercentage) {
		this.samplePercentage = samplePercentage;
	}

	public double getCarTruckVanDriver() {
		return carTruckVanDriver;
	}

	public void setCarTruckVanDriver(double carTruckVanDriver) {
		this.carTruckVanDriver = carTruckVanDriver;
	}

	public double getCarTruckVanPassenger() {
		return carTruckVanPassenger;
	}

	public void setCarTruckVanPassenger(double carTruckVanPassenger) {
		this.carTruckVanPassenger = carTruckVanPassenger;
	}

	public double getPublicTransit() {
		return publicTransit;
	}

	public void setPublicTransit(double publicTransit) {
		this.publicTransit = publicTransit;
	}

	public double getWalked() {
		return walked;
	}

	public void setWalked(double walked) {
		this.walked = walked;
	}

	public double getBicycle() {
		return bicycle;
	}

	public void setBicycle(double bicycle) {
		this.bicycle = bicycle;
	}

	public double getMotorcycle() {
		return motorcycle;
	}

	public void setMotorcycle(double motorcycle) {
		this.motorcycle = motorcycle;
	}

	public double getTaxicab() {
		return taxicab;
	}

	public void setTaxicab(double taxicab) {
		this.taxicab = taxicab;
	}

	public double getOtherMethod() {
		return otherMethod;
	}

	public void setOtherMethod(double otherMethod) {
		this.otherMethod = otherMethod;
	}

	public double getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}

}
