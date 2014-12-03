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
	private double dx;
	private double dy;

	/**
	 * Constructor for the zombie class
	 *
	 * @param startingLocation location where the zombie will be placed when
	 * entering game
	 * @param map Map where the zombie is added
	 */
	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map, 3, Color.LIME);
		this.speed = 0.3;
		zombieBrain = new Random();
		trapped = false;
	}

	/**
	 * Move towards the nearest survivor, if any survivors are left alive. Mark
	 * zombie as trapped if it walks to a trap.
	 */
	@Override
	public void move() {
		if (canMove()) {
			setDirection();
			if (map.hasObstacle(location.x + dx, location.y + dy)) {
				tryToGoAroundTheObstacle(dx, dy);
			} else {
				super.move(dx, dy);
			}
			checkIfTrapped();
		}
	}

	private boolean canMove() {
		return !map.getSurvivors().isEmpty() && !trapped;
	}

	private void setDirection() {
		Point nearestSurvivor = Collision.nearestPersonLocation(this, map.getSurvivors());
		dx = normalize(nearestSurvivor.x - location.x);
		dy = normalize(nearestSurvivor.y - location.y);
	}

	private void tryToGoAroundTheObstacle(double dx, double dy) {
		if (zombieBrain.nextDouble() < 0.5) {
			super.move(-dx, 0);
		} else {
			super.move(0, -dy);
		}
	}

	private void checkIfTrapped() {
		if (map.isTrapped(location)) {
			color = Color.DARKRED;
			trapped = true;
		}
	}

}
