
package abandonallhope.domain;

import abandonallhope.logic.Collision;
import javafx.scene.paint.Color;

/**
 * New zombie object
 * @author kipsu
 */
public class Zombie extends MovingObject implements DrawableObject {

	/**
	 * Constructor for the zombie class
	 * @param startingLocation location where the zombie will be placed when
	 * entering game
	 * @param map Map where the zombie is added
	 */
	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map,3, Color.GREEN);
		this.speed = 0.3;
	}
	
	/**
	 * Move towards the nearest survivor, if any survivors are left alive
	 */
	@Override
	public void move() {
		if (!map.getSurvivors().isEmpty()) {
			Point nearestSurvivor = Collision.nearestPersonLocation(this, map.getSurvivors());
			move(nearestSurvivor.x - location.x, nearestSurvivor.y - location.y);
		}
	}
	
}
