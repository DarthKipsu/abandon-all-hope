
package abandonallhope.domain.weapons;

import abandonallhope.domain.Inventory;

/**
 * Pistol firearm weapon
 * Short range and long reload time
 * @author kipsu
 */
public class Pistol extends Firearm {

	/**
	 * Constructor for Pistol type firearm
	 * @param bullets Bullet magazine connected with this type of weapon
	 */
	public Pistol(Inventory inventory) {
		super(50, 300, inventory.getPistolBullets());
	}

	@Override
	public String toString() {
		return "pistol";
	}
	
}
