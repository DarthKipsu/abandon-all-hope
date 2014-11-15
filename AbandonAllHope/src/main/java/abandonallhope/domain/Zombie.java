
package abandonallhope.domain;

import abandonallhope.logic.Collision;


public class Zombie extends Person {

	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map);
		this.speed = 0.3;
	}
	
	@Override
	public void move() {
		if (!map.getSurvivors().isEmpty()) {
			Point nearestSurvivor = Collision.nearestPersonLocation(this, map.getSurvivors());
			move(nearestSurvivor.x - location.x, nearestSurvivor.y - location.y);
		}
	}
	
}
