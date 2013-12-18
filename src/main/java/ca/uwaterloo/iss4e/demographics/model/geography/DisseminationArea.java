package ca.uwaterloo.iss4e.demographics.model.geography;

import java.util.ArrayList;
import java.util.List;

public class DisseminationArea implements GeographicArea {
	private int daId;
	private int censusDivisionId;
	private int provinceId;
	private String censusDivisionName;
	private String provinceName;
	private String dataQualityFlags;
	private CensusDivisionType censusDivisionType;
	private List<CensusPolygon> polygonPatches;

	public DisseminationArea() {
		this.polygonPatches = new ArrayList<CensusPolygon>();
	}

	public DisseminationArea(int daId, int censusDivisionId, int provinceId,
			String censusDivisionName, CensusDivisionType censusDivisionType,
			String provinceName, String dataQualityFlags) {
		this.daId = daId;
		this.censusDivisionId = censusDivisionId;
		this.provinceId = provinceId;
		this.censusDivisionName = censusDivisionName;
		this.censusDivisionType = censusDivisionType;
		this.provinceName = provinceName;
		this.dataQualityFlags = dataQualityFlags;
		this.polygonPatches = new ArrayList<CensusPolygon>();
	}

	public void addPolygon(CensusPolygon censusPolygon) {
		this.polygonPatches.add(censusPolygon);
	}

	public int getDaId() {
		return daId;
	}

	public void setDaId(int daId) {
		this.daId = daId;
	}

	public int getCensusDivisionId() {
		return censusDivisionId;
	}

	public void setCensusDivisionId(int censusDivisionId) {
		this.censusDivisionId = censusDivisionId;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getCensusDivisionName() {
		return censusDivisionName;
	}

	public void setCensusDivisionName(String censusDivisionName) {
		this.censusDivisionName = censusDivisionName;
	}

	public CensusDivisionType getCensusDivisionType() {
		return censusDivisionType;
	}

	public void setCensusDivisionType(CensusDivisionType censusDivisionType) {
		this.censusDivisionType = censusDivisionType;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getDataQualityFlags() {
		return dataQualityFlags;
	}

	public void setDataQualityFlags(String dataQualityFlags) {
		this.dataQualityFlags = dataQualityFlags;
	}

	public List<CensusPolygon> getPolygonPatches() {
		return polygonPatches;
	}

	public void setPolygonPatches(List<CensusPolygon> polygonPatches) {
		this.polygonPatches = polygonPatches;
	}

}
