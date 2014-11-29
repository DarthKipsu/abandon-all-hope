package abandonallhope.domain;

import abandonallhope.logic.Collision;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * New zombie object
 *
 * @author kipsu
 */
public class Zombie extends MovingObject implements DrawableObject {

	private Random zombieBrain;

	/**
	 * Constructor for the zombie class
	 *
	 * @param startingLocation location where the zombie will be placed when
	 * entering game
	 * @param map Map where the zombie is added
	 */
	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map, 3, Color.GREEN);
		this.speed = 0.3;
		zombieBrain = new Random();
	}

	/**
	 * Move towards the nearest survivor, if any survivors are left alive
	 */
	@Override
	public void move() {
		if (!map.getSurvivors().isEmpty()) {
			Point nearestSurvivor = Collision.nearestPersonLocation(this, map.getSurvivors());
			double dx = normalize(nearestSurvivor.x - location.x);
			double dy = normalize(nearestSurvivor.y - location.y);
			if (map.hasObstacle(location.x + dx, location.y + dy)) {
				tryToGoAroundTheObstacle(dx, dy);
			} else {
				move(dx, dy);
			}
		}
	}

	private void tryToGoAroundTheObstacle(double dx, double dy) {
		if (zombieBrain.nextDouble() < 0.5) {
			move(-dx, 0);
		} else {
			move(0, -dy);
		}
	}

}
