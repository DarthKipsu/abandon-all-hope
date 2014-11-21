
package abandonallhope.domain;

import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Weapon;

/**
 * New survivor object
 * @author kipsu
 */
public class Survivor extends MovingObject {
	
	private boolean selected;
	private Point destination;
	private Weapon weapon;
	private Firearm gun;

	/**
	 * Constructor for the survivor class
	 * @param startingLocation location where the survivor will be placed when
	 * entering game
	 * @param map Map where the survivor is added
	 */
	public Survivor(Point startingLocation, Map map) {
		super(startingLocation, map, 2);
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
	 * Move towards another point in the game
	 * Moves until the destination is reached or a new destination set.
	 * @param destination 
	 */
	public void moveTowards(Point destination) {
		this.destination = destination;
	}

	/**
	 * Move towards a direction if a direction is set.
	 * You can set direction with moveTowards()
	 */
	@Override
	public void move() {
		if (destination != null) {
			move(destination.x - location.x, destination.y - location.y);
			if (hasReachedLocation()) {
				destination = null;
			}
		}
	}
	
	protected boolean hasReachedLocation() {
		return Math.abs(location.x - destination.x) < 0.5 &&
			   Math.abs(location.y - destination.y) < 0.5;
	}
	
}
