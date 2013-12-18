package ca.uwaterloo.iss4e.demographics;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.uwaterloo.iss4e.demographics.dao.census.DaCensusProfileDAO;
import ca.uwaterloo.iss4e.demographics.model.census.DaCensusProfile;
import ca.uwaterloo.iss4e.demographics.model.geography.Province;

public class Testing {
	public static void main(String[] args) {
		/*
		 * GeometryFactory geometryFactory =
		 * JTSFactoryFinder.getGeometryFactory();
		 * 
		 * Coordinate[] coords = new Coordinate[] {new Coordinate(4, 0), new
		 * Coordinate(2, 2), new Coordinate(4, 4), new Coordinate(6, 2), new
		 * Coordinate(4, 0) };
		 * 
		 * LinearRing ring = geometryFactory.createLinearRing( coords );
		 * LinearRing holes[] = null; // use LinearRing[] to represent holes
		 * Polygon polygon = geometryFactory.createPolygon(ring, holes );
		 * 
		 * Coordinate coord = new Coordinate(4, 2); Point point =
		 * geometryFactory.createPoint(coord);
		 * 
		 * System.out.println(polygon.contains(point));
		 */

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		DaCensusProfileDAO daCensusProfileDAO = (DaCensusProfileDAO) context
				.getBean("daCensusProfileDAO");
		Collection<DaCensusProfile> daCensusProfiles = daCensusProfileDAO
				.getDaCensusProfiles(Province.PEI_PROV_ID);
	}
}
