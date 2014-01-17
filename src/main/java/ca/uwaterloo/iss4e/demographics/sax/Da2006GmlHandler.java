package ca.uwaterloo.iss4e.demographics.sax;

import java.util.HashSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ca.uwaterloo.iss4e.demographics.model.geography.CensusCoordinate;
import ca.uwaterloo.iss4e.demographics.model.geography.CensusPolygon;
import ca.uwaterloo.iss4e.demographics.model.geography.DisseminationArea;
import ca.uwaterloo.iss4e.demographics.model.geography.PathType;

public class Da2006GmlHandler extends DefaultHandler {
	HashSet<DisseminationArea> das = new HashSet<DisseminationArea>();

	private double[] digits;
	private boolean dauid;
	private boolean cduid;
	private boolean pruid;
	private boolean coords;
	private boolean boundingBox;
	private StringBuffer coordBuffer;
	private PathType pathType;
	private CensusPolygon censusPolygon;
	private DisseminationArea da;

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		/*
		 * Each DisseminationArea element is a new Dissemination Area.
		 */
		if (qName.equalsIgnoreCase("DisseminationArea")) {
			da = new DisseminationArea();
		}

		/*
		 * Set up some general information about the Dissemination Area. This
		 * data only occurs once per DisseminationArea element.
		 */
		if (qName.equalsIgnoreCase("daUid")) {
			dauid = true;
		} else if (qName.equalsIgnoreCase("cdUid")) {
			cduid = true;
		} else if (qName.equalsIgnoreCase("prUid")) {
			pruid = true;
		} else if (qName.equalsIgnoreCase("gml:boundedBy")) {
			boundingBox = true;
		}

		/*
		 * A DisseminationArea may be made up of several gml:polygonMember
		 * elements. Additionally, each gml:polygonMember may have external and
		 * internal paths which is flagged on each coordinate from gml:coordinates.
		 */
		if (qName.equalsIgnoreCase("gml:polygonMember")) {
			censusPolygon = new CensusPolygon();
		} else if (qName.equalsIgnoreCase("gml:outerBoundaryIs")) {
			pathType = PathType.EXTERIOR;
		} else if (qName.equalsIgnoreCase("gml:innerBoundaryIs")) {
			pathType = PathType.INTERIOR;
		}

		if (qName.equalsIgnoreCase("gml:coordinates") && !boundingBox) {
			coords = true;
			coordBuffer = new StringBuffer();
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equalsIgnoreCase("daUid")) {
			dauid = false;
		} else if (qName.equalsIgnoreCase("cdUid")) {
			cduid = false;
		} else if (qName.equalsIgnoreCase("prUid")) {
			pruid = false;
		} else if (qName.equalsIgnoreCase("gml:boundedBy")) {
			boundingBox = false;
		}

		/*
		 * At the end tag of a position list take the parsed digits and add them
		 * to the CensusPolygon as Coordinates.
		 */
		if (qName.equalsIgnoreCase("gml:coordinates") && !boundingBox) {
			System.out.println("Splitting data: "
					+ coordBuffer.toString().substring(0, 30)
					+ " [...] "
					+ coordBuffer.toString().substring(
							coordBuffer.toString().length() - 30));

			String[] strings = coordBuffer.toString().split("[\\s,]");
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
				 * Trust that pathType has been flagged by gml:outerBoundaryIs or
				 * gml:innerBoundaryIs prior to gml:coordinates.
				 */
				CensusCoordinate censusCoordinate = new CensusCoordinate(
						longitude, latitude);
				censusCoordinate.setPathType(pathType);
				censusPolygon.addCensusCoordinate(censusCoordinate);
			}

			digits = null;
			coords = false;
			coordBuffer = null;
		}

		if (qName.equalsIgnoreCase("gml:polygonMember")) {
			da.addPolygon(censusPolygon);
			censusPolygon = null;
		}

		/*
		 * Inner elements of DA have been populated. The DA is complete. Add it
		 * to the list.
		 */
		if (qName.equalsIgnoreCase("DisseminationArea")) {
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
		} else if (pruid) {
			int provinceId = Integer.parseInt(new String(ch, start, length));
			da.setProvinceId(provinceId);
		} else if (coords) {
			coordBuffer.append(new String(ch, start, length));
		}

	}

	public HashSet<DisseminationArea> getDas() {
		return das;
	}
}
