
package abandonallhope.domain;

import java.awt.Point;

public class Zombie extends Person {

	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map);
		this.speed = 1;
	}
	
	@Override
	public void move() {
		if (!map.getSurvivors().isEmpty()) {
			Point nearestSurvivor = nearestSurvivor();
			move(nearestSurvivor.x - location.x, nearestSurvivor.y - location.y);
		}
	}
	
	private Point nearestSurvivor() {
		Point nearest = map.getSurvivors().get(0).getLocation();
		int nearesDifference = Integer.MAX_VALUE;
		for (Person survivor : map.getSurvivors()) {
			int difference = Math.abs(survivor.getLocation().x - location.x)
					+ Math.abs(survivor.getLocation().y - location.y);
			if (difference < nearesDifference) {
				nearest = survivor.getLocation();
				nearesDifference = difference;
			}
		}
		return nearest;
	}
	
}
