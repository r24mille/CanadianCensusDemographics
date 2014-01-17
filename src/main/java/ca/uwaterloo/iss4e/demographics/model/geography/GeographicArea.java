package ca.uwaterloo.iss4e.demographics.model.geography;

import java.util.List;

public interface GeographicArea {
	public void addPolygon(CensusPolygon censusPolygon);

	public String getProvinceName();

	public void setProvinceName(String provinceName);

	public int getProvinceId();

	public void setProvinceId(int provinceId);

	public List<CensusPolygon> getCensusPolygons();

	public void setCensusPolygons(List<CensusPolygon> censusPolygons);
}
