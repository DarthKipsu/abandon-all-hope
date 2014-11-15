
package abandonallhope.logic;

import abandonallhope.domain.Person;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import java.util.List;

public class Collision {
	
	public static Survivor survivor(Zombie zombie, List<Survivor> survivors) {
		for (Survivor survivor : survivors) {
			if (zombie.occupiedArea().intersects(survivor.occupiedArea())) {
				return survivor;
			}
		}
		return null;
	}
	
	public static Point nearestPersonLocation(Person person, List<? extends Person> enemies) {
		Point nearest = nearestPerson(person, enemies).getLocation();
		return nearest;
	}
	
	public static Person nearestPerson(Person person, List<? extends Person> enemies) {
		Person nearest = enemies.get(0);
		Double nearesDifference = Double.MAX_VALUE;
		for (Person enemy : enemies) {
			double difference = distanceBetween(person.getLocation(), enemy.getLocation());
			if (difference < nearesDifference) {
				nearest = enemy;
				nearesDifference = difference;
			}
		}
		return nearest;
	}

	public static double distanceBetween(Point x, Point y) {
		return Math.abs(y.x - x.x) + Math.abs(y.y - x.y);
	}
	
}
