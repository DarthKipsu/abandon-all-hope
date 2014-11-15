
package abandonallhope.logic;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import java.util.List;

public final class Collision {

	private Collision() {
	}
	
	public static Survivor survivor(Zombie zombie, List<Survivor> survivors) {
		for (Survivor survivor : survivors) {
			if (zombie.occupiedArea().intersects(survivor.occupiedArea())) {
				return survivor;
			}
		}
		return null;
	}
	
	public static Point nearestSurvivor(Zombie zombie, List<Survivor> survivors) {
		Point nearest = survivors.get(0).getLocation();
		Double nearesDifference = Double.MAX_VALUE;
		for (Survivor survivor : survivors) {
			double difference = distanceBetween(zombie.getLocation(), survivor.getLocation());
			if (difference < nearesDifference) {
				nearest = survivor.getLocation();
				nearesDifference = difference;
			}
		}
		return nearest;
	}

	private static double distanceBetween(Point x, Point y) {
		return Math.abs(y.x - x.x) + Math.abs(y.y - x.y);
	}
	
}
