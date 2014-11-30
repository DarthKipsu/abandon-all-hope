
package abandonallhope.domain;

import abandonallhope.domain.weapons.Magazine;

/**
 * Contains inventory for the player resources.
 * @author kipsu
 */
public class Inventory {
	
	private Magazine pistolBullets;

	/**
	 * Create empty inventory.
	 */
	public Inventory() {
		pistolBullets = new Magazine();
	}

	public Magazine getPistolBullets() {
		return pistolBullets;
	}
	
	public void addPistolBullets(int amount) {
		pistolBullets.add(amount);
	}
}
