package abandonallhope.domain;

import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Weapon;
import javafx.scene.paint.Color;

/**
 * New survivor object
 *
 * @author kipsu
 */
public class Survivor extends MovingObject implements DrawableObject {

	private boolean selected;
	protected Point destination;
	private Weapon weapon;
	private Firearm gun;
	private String name;
	private int id;

	/**
	 * Constructor for the survivor class
	 *
	 * @param startingLocation location where the survivor will be placed when
	 * entering game
	 * @param map Map where the survivor is added
	 */
	public Survivor(Point startingLocation, Map map, String name, int id) {
		super(startingLocation, map, 3, Color.BLACK);
		this.name = name;
		this.id = id;
		this.speed = 0.5;
		selected = false;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Firearm getGun() {
		return gun;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setGun(Firearm gun) {
		this.gun = gun;
	}

	public boolean isSelected() {
		return selected;
	}

	public void select() {
		selected = true;
	}

	public void unselect() {
		selected = false;
	}

	/**
	 * Move towards another point in the game Moves until the destination is
	 * reached or a new destination set.
	 *
	 * @param destination
	 */
	public void moveTowards(Point destination) {
		this.destination = destination;
	}

	/**
	 * Move towards a direction if a direction is set and no obstacles are
	 * ahead. You can set direction with moveTowards()
	 */
	@Override
	public void move() {
		if (destination != null) {
			double dx = normalize(destination.x - location.x);
			double dy = normalize(destination.y - location.y);
			if (map.hasObstacle(location.x + dx, location.y + dy)) {
				stop();
			} else {
				moveOnce(dx, dy);
			}
		}
	}

	private void stop() {
		destination = null;
	}

	private void moveOnce(double dx, double dy) {
		move(dx, dy);
		if (hasReachedLocation()) {
			stop();
		}
	}

	protected boolean hasReachedLocation() {
		return Math.abs(location.x - destination.x) < 0.5
				&& Math.abs(location.y - destination.y) < 0.5;
	}

}
