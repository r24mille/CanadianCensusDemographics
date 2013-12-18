package ca.uwaterloo.iss4e.demographics.model.geography;

import java.util.ArrayList;
import java.util.List;

public class ForwardSortationArea implements GeographicArea {
	private String fsaCode;
	private String provinceName;
	private int provinceId;
	private List<CensusPolygon> polygonPatches;

	public ForwardSortationArea() {
		this.polygonPatches = new ArrayList<CensusPolygon>();
	}

	public ForwardSortationArea(String fsaCode, String provinceName,
			int provinceId) {
		this.fsaCode = fsaCode;
		this.provinceName = provinceName;
		this.provinceId = provinceId;
		this.polygonPatches = new ArrayList<CensusPolygon>();
	}

	public void addPolygon(CensusPolygon censusPolygon) {
		this.polygonPatches.add(censusPolygon);
	}

	public String getFsaCode() {
		return fsaCode;
	}

	public void setFsaCode(String fsaCode) {
		this.fsaCode = fsaCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public List<CensusPolygon> getPolygonPatches() {
		return polygonPatches;
	}

	public void setPolygonPatches(List<CensusPolygon> polygonPatches) {
		this.polygonPatches = polygonPatches;
	}
}
