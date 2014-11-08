
package abandonallhope.domain;

import java.awt.Point;

public class Zombie extends Person {

	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map);
		this.speed = 1;
	}
	
	@Override
	public void move() {
		Point nearestSurvivor = nearestSurvivor();
		int dx = normalize(nearestSurvivor.x - location.x);
		int dy = normalize(nearestSurvivor.y - location.y);
		if (map.isValidMove(location, dx, dy)) {
			location.translate(dx, dy);
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
