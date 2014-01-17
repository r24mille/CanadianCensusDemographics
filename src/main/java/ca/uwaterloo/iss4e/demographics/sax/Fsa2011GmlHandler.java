package ca.uwaterloo.iss4e.demographics.sax;

import java.util.HashSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.ForwardSortationArea;
import ca.uwaterloo.iss4e.demographics.model.geography.PathType;

public class Fsa2011GmlHandler extends DefaultHandler {
	HashSet<ForwardSortationArea> fsas = new HashSet<ForwardSortationArea>();

	private double[] digits;
	private boolean cfsauid;
	private boolean pruid;
	private boolean prname;
	private boolean posList;
	private StringBuffer posBuffer;
	private PathType pathType;
	private CensusPolygon censusPolygon;
	private ForwardSortationArea fsa;

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		/*
		 * Each gml:featureMember is a new Forward Sortation Area.
		 */
		if (qName.equalsIgnoreCase("gml:featureMember")) {
			fsa = new ForwardSortationArea();
		}

		/*
		 * Set up some general information about the Forward Sortation Area.
		 * This data only occurs once per gml:featureMember
		 */
		if (qName.equalsIgnoreCase("fme:CFSAUID")) {
			cfsauid = true;
		} else if (qName.equalsIgnoreCase("fme:PRUID")) {
			pruid = true;
		} else if (qName.equalsIgnoreCase("fme:PRNAME")) {
			prname = true;
		}

		/*
		 * A gml:featureMember may be made up of several gml:CensusPolygon
		 * elements. Additionally, each gml:CensusPolygon may have external and
		 * internal paths which is flagged on each coordinate from gml:posList.
		 */
		if (qName.equalsIgnoreCase("gml:CensusPolygon")) {
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
		
		if (qName.equalsIgnoreCase("fme:CFSAUID")) {
			cfsauid = false;
		} else if (qName.equalsIgnoreCase("fme:PRUID")) {
			pruid = false;
		} else if (qName.equalsIgnoreCase("fme:PRNAME")) {
			prname = false;
		}

		/*
		 * At the end tag of a position list take the parsed digits and add them
		 * to the CensusPolygon as Coordinates.
		 */
		if (qName.equalsIgnoreCase("gml:posList")) {
			System.out.println("Splitting data: " + posBuffer.toString().substring(0, 30) + " [...] " + posBuffer.toString().substring(posBuffer.toString().length() - 30));
			
			String[] strings = posBuffer.toString().split(" ");
			digits = new double[strings.length];
			for (int i = 0; i < strings.length; i++) {
				digits[i] = Double.parseDouble(strings[i]);
			}
			
			int j = 0;
			while (j+1 < digits.length) {
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
				censusPolygon.addCoordinate(censusCoordinate);
			}

			digits = null;
			posList = false;
			posBuffer = null;
		}
		
		if (qName.equalsIgnoreCase("gml:CensusPolygon")) {
			fsa.addPolygon(censusPolygon);
			censusPolygon = null;
		}

		/*
		 * Inner elements of FSA have been populated. The FSA is complete. Add
		 * it to the list.
		 */
		if (qName.equalsIgnoreCase("gml:featureMember")) {
			fsas.add(fsa);
		}

	}

	public void characters(char ch[], int start, int length)
			throws SAXException {

		if (cfsauid) {
			String fsaCode = new String(ch, start, length);
			fsa.setFsaCode(fsaCode);
		} else if (pruid) {
			int provinceId = Integer.parseInt(new String(ch, start, length));
			fsa.setProvinceId(provinceId);
		} else if (prname) {
			String provinceName = new String(ch, start, length);
			fsa.setProvinceName(provinceName);
		} else if (posList) {
			posBuffer.append(new String(ch, start, length));
		}

	}

	public HashSet<ForwardSortationArea> getFsas() {
		return fsas;
	}
}
