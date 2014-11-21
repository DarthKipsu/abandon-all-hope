
package abandonallhope.logic;

import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import java.util.List;

/**
 * Class containing static methods to check if two objects collide or which is 
 * the nearest by other object 
 * @author kipsu
 */
public class Collision {
	
	/**
	 * Checks if a zombie is colliding with at least one survivor and returns
	 * the survivor if so.
	 * @param zombie Zombie whose collisions are checked
	 * @param survivors List of live survivors
	 * @return Survivor, if colliding with one, null otherwise
	 */
	public static Survivor survivor(Zombie zombie, List<Survivor> survivors) {
		for (Survivor survivor : survivors) {
			if (zombie.occupiedArea().intersects(survivor.occupiedArea())) {
				return survivor;
			}
		}
		return null;
	}
	
	/**
	 * Return the point containing the nearest zombie or survivor.
	 * @param person Zombie or a survivor whose vicinity is checked
	 * @param enemies list of zombies or survivors among which the point is searched from
	 * @return Point object for the nearest found person
	 */
	public static Point nearestPersonLocation(MovingObject person, List<? extends MovingObject> enemies) {
		Point nearest = nearestPerson(person, enemies).getLocation();
		return nearest;
	}
	
	/**
	 * Return the nearest zombie or survivor
	 * @param person Zombie or a survivor whose vicinity is checked
	 * @param enemies list of zombies or survivors among which the point is searched from
	 * @return The nearest found person
	 */
	public static MovingObject nearestPerson(MovingObject person, List<? extends MovingObject> enemies) {
		MovingObject nearest = enemies.get(0);
		Double nearesDifference = Double.MAX_VALUE;
		for (MovingObject enemy : enemies) {
			double difference = distanceBetween(person.getLocation(), enemy.getLocation());
			if (difference < nearesDifference) {
				nearest = enemy;
				nearesDifference = difference;
			}
		}
		return nearest;
	}

	/**
	 * Get the absolute distance between two points. Point order has no significance.
	 * @param x Point object x
	 * @param y Point object y
	 * @return Absolute distance between the points as double
	 */
	public static double distanceBetween(Point x, Point y) {
		return Math.abs(y.x - x.x) + Math.abs(y.y - x.y);
	}
	
}
