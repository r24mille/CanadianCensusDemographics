package ca.uwaterloo.iss4e.demographics.sax;

import java.util.HashSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusDivisionType;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;
import ca.uwaterloo.iss4e.demographics.model.geography.PathType;

public class Da2011GmlHandler extends DefaultHandler {
	HashSet<DisseminationArea> das = new HashSet<DisseminationArea>();

	private double[] digits;
	private boolean dauid;
	private boolean cduid;
	private boolean cdname;
	private boolean cdtype;
	private boolean pruid;
	private boolean prname;
	private boolean posList;
	private StringBuffer posBuffer;
	private PathType pathType;
	private CensusPolygon censusPolygon;
	private DisseminationArea da;

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		/*
		 * Each gml:featureMember is a new Dissemination Area.
		 */
		if (qName.equalsIgnoreCase("gml:featureMember")) {
			da = new DisseminationArea();
		}

		/*
		 * Set up some general information about the Dissemination Area.
		 * This data only occurs once per gml:featureMember
		 */
		if (qName.equalsIgnoreCase("fme:DAUID")) {
			dauid = true;
		} else if (qName.equalsIgnoreCase("fme:CDUID")) {
			cduid = true;
		} else if (qName.equalsIgnoreCase("fme:CDNAME")) {
			cdname = true;
		} else if (qName.equalsIgnoreCase("fme:CDTYPE")) {
			cdtype = true;
		} else if (qName.equalsIgnoreCase("fme:PRUID")) {
			pruid = true;
		} else if (qName.equalsIgnoreCase("fme:PRNAME")) {
			prname = true;
		}

		/*
		 * A gml:featureMember may be made up of several gml:PolygonPatch
		 * elements. Additionally, each gml:PolygonPatch may have external and
		 * internal paths which is flagged on each coordinate from gml:posList.
		 */
		if (qName.equalsIgnoreCase("gml:PolygonPatch")) {
			censusPolygon = new CensusPolygon();
		} else if (qName.equalsIgnoreCase("gml:exterior")) {
			pathType = PathType.EXTERIOR;
		} else if (qName.equalsIgnoreCase("gml:interior")) {
			pathType = PathType.INTERIOR;
		}

		if (qName.equalsIgnoreCase("gml:posList")) {
			posList = true;
			posBuffer = new StringBuffer();
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equalsIgnoreCase("fme:DAUID")) {
			dauid = false;
		} else if (qName.equalsIgnoreCase("fme:CDUID")) {
			cduid = false;
		} else if (qName.equalsIgnoreCase("fme:CDNAME")) {
			cdname = false;
		} else if (qName.equalsIgnoreCase("fme:CDTYPE")) {
			cdtype = false;
		} else if (qName.equalsIgnoreCase("fme:PRUID")) {
			pruid = false;
		} else if (qName.equalsIgnoreCase("fme:PRNAME")) {
			prname = false;
		}

		/*
		 * At the end tag of a position list take the parsed digits and add them
		 * to the CensusPolygon as CensusCoordinates.
		 */
		if (qName.equalsIgnoreCase("gml:posList")) {
			System.out.println("Splitting data: "
					+ posBuffer.toString().substring(0, 30)
					+ " [...] "
					+ posBuffer.toString().substring(
							posBuffer.toString().length() - 30));

			String[] strings = posBuffer.toString().split(" ");
			digits = new double[strings.length];
			for (int i = 0; i < strings.length; i++) {
				digits[i] = Double.parseDouble(strings[i]);
			}

			int j = 0;
			while (j + 1 < digits.length) {
				double latitude = digits[j];
				j = j + 1;
				double longitude = digits[j];
				j = j + 1;
				
				/*
				 * Trust that pathType has been flagged by gml:exterior or
				 * gml:interior prior to gml:posList.
				 */
				CensusCoordinate censusCoordinate = new CensusCoordinate(longitude, latitude);
				censusCoordinate.setPathType(pathType);
				censusPolygon.addCensusCoordinate(censusCoordinate);
			}

			digits = null;
			posList = false;
			posBuffer = null;
		}

		if (qName.equalsIgnoreCase("gml:PolygonPatch")) {
			da.addPolygon(censusPolygon);
			censusPolygon = null;
		}

		/*
		 * Inner elements of DA have been populated. The DA is complete. Add
		 * it to the list.
		 */
		if (qName.equalsIgnoreCase("gml:featureMember")) {
			das.add(da);
			da = null;
		}

	}

	public void characters(char ch[], int start, int length)
			throws SAXException {

		if (dauid) {
			int daId = Integer.parseInt(new String(ch, start, length));
			da.setDaId(daId);
		} else if (cduid) {
			int cdId = Integer.parseInt(new String(ch, start, length));
			da.setCensusDivisionId(cdId);
		} else if (cdname) {
			String cdName = new String(ch, start, length);
			da.setCensusDivisionName(cdName);
		} else if (cdtype) {
			String cdType = new String(ch, start, length);
			da.setCensusDivisionType(CensusDivisionType.fromString(cdType));
		} else if (pruid) {
			int provinceId = Integer.parseInt(new String(ch, start, length));
			da.setProvinceId(provinceId);
		} else if (prname) {
			String provinceName = new String(ch, start, length);
			da.setProvinceName(provinceName);
		} else if (posList) {
			posBuffer.append(new String(ch, start, length));
		}

	}

	public HashSet<DisseminationArea> getDas() {
		return das;
	}
}
