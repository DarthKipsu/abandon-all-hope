
package abandonallhope.logic;

import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
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
	 * @param source whose collisions are checked
	 * @param targets targets whose collisions are checked
	 * @return target who is being collided with, other wise null
	 */
	public static MovingObject hitTest(MovingObject source, List<? extends MovingObject> targets) {
		for (MovingObject target : targets) {
			if (source.occupiedArea().intersects(target.occupiedArea())) {
				return target;
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
		try {
			MovingObject nearest = enemies.get(0);
			return nearest(enemies, person, nearest);
		} catch (Exception e) {
			return null;
		}
	}

	private static MovingObject nearest(List<? extends MovingObject> enemies, MovingObject person, MovingObject nearest) {
		double nearesDifference = Double.MAX_VALUE;
		for (MovingObject enemy : enemies) {
			if (enemyIsTrapped(enemy)) {
				continue;
			}
			double difference = distanceBetween(person.getLocation(), enemy.getLocation());
			if (nearest.isTrapped() || difference < nearesDifference) {
				nearest = enemy;
				nearesDifference = difference;
			}
		}
		return nearest.isTrapped() ? null : nearest;
	}

	private static boolean enemyIsTrapped(MovingObject enemy) {
		if (enemy.isTrapped()) {
			return true;
		}
		return false;
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
