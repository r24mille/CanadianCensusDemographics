package ca.uwaterloo.iss4e.demographics;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.uwaterloo.iss4e.demographics.dao.census.DaCensusProfileDAO;
import ca.uwaterloo.iss4e.demographics.model.census.DaCensusProfile;
import ca.uwaterloo.iss4e.demographics.model.geography.Province;

public class Testing {
	public static void main(String[] args) {
		
//		 GeometryFactory geometryFactory =
//		 JTSFactoryFinder.getGeometryFactory();
//		 
//		 Coordinate[] coords = new Coordinate[] {new Coordinate(4, 0), new
//		 Coordinate(2, 2), new Coordinate(4, 4), new Coordinate(6, 2), new
//		 Coordinate(4, 0) };
//		 
//		 LinearRing ring = geometryFactory.createLinearRing( coords );
//		 LinearRing holes[] = null; // use LinearRing[] to represent holes
//		 Polygon polygon = geometryFactory.createPolygon(ring, holes );
//		 
//		 Coordinate coord = new Coordinate(4, 2); Point point =
//		 geometryFactory.createPoint(coord);
//		 
//		 System.out.println(polygon.contains(point));
		 

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		String testing = "-52.7752049817159,47.53091508605882 -52.77523899973838,47.53133063308882 -52.775996950931656,47.53127948250297 -52.77681560740959,47.53080957253148 -52.7777839267128,47.53072555707646 -52.7779633244197,47.53094968065795 -52.77891895903656,47.531679709067646 -52.7812578010419,47.52986143341109 -52.781883139605185,47.5294542055585 -52.782134197553134,47.52932579370126 -52.782163108753835,47.52931648610674 -52.78015390385734,47.52653400955164 -52.77791513908521,47.523209880415905 -52.776823844218896,47.523282858546445 -52.77568337560098,47.52350467582133 -52.7749496406271,47.52366175177499 -52.770818057074095,47.52469810712315 -52.76952133618346,47.5252383594462 -52.770276733965844,47.52597357704576 -52.77123146253368,47.526855986427805 -52.771362015961344,47.52703661965611 -52.77420816305245,47.52594878424972 -52.774749156688344,47.52662436734983 -52.77588715426347,47.52623946125049 -52.77672417882617,47.52631622831332 -52.77734284557391,47.52668441368975 -52.777388065657064,47.527519955626786 -52.77768599105002,47.52811638298937 -52.777454289603966,47.52833704711095 -52.77711748647103,47.52876247831213 -52.77657855203746,47.52925948738627 -52.77574111563429,47.52988762646472 -52.77506462648515,47.530065788650234 -52.7752049817159,47.53091508605882 ";
		String[] results = testing.split("[\\s,]");
		
		for(String part : results) {
			System.out.println(Double.parseDouble(part));
		}
		
	}
}
