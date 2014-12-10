package abandonallhope.domain;

import abandonallhope.logic.Collision;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * New zombie object
 *
 * @author kipsu
 */
public class Zombie extends MovingObject implements DrawableObject {

	private Random zombieBrain;
	private List<Zombie> zombies;
	private double dx;
	private double dy;

	/**
	 * Constructor for the zombie class
	 *
	 * @param startingLocation location where the zombie will be placed when
	 * entering game
	 * @param map Map where the zombie is added
	 */
	public Zombie(Point startingLocation, Map map, List<Zombie> zombies) {
		super(startingLocation, map, 3, Color.LIME);
		this.speed = 0.3;
		this.zombies = zombies;
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
			makeAMove();
			checkIfTrapped();
		}
	}

	private boolean canMove() {
		return !map.getSurvivors().isEmpty() && !trapped;
	}

	private void setDirection() {
		Point nearestSurvivor = Collision.nearestPersonLocation(this, map.getSurvivors());
		double distance = Collision.distanceBetween(location, nearestSurvivor);
		double jitterX = distance * (zombieBrain.nextDouble() - 0.5);
		double jitterY = distance * (zombieBrain.nextDouble() - 0.5);
		dx = normalize(nearestSurvivor.x + jitterX - location.x);
		dy = normalize(nearestSurvivor.y + jitterY - location.y);
	}

	private void makeAMove() {
		if (wayIsBlocked()) {
			tryToGoAroundTheObstacle(dx, dy);
		} else {
			super.move(dx, dy);
		}
	}

	private boolean wayIsBlocked() {
		return map.hasObstacle(location.x + dx, location.y + dy) || anotherZombieIsBlockingTheWay();
	}

	private boolean anotherZombieIsBlockingTheWay() {
		for (Zombie zombie : zombies) {
			if (!zombie.equals(this) && !zombie.isTrapped() && isBlockingTheWay(zombie)) {
				return true;
			}
		}
		return false;
	}

	protected boolean isBlockingTheWay(Zombie zombie) {
		return zombie.location.x > location.x - 1 &&
				zombie.location.x < location.x + 1 &&
				zombie.location.y > location.y - 1 &&
				zombie.location.y < location.y + 1;
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
