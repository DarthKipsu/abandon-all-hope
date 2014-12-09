
package abandonallhope.events.action;

import abandonallhope.domain.weapons.Weapon;

/**
 * Event called when a new weapon is added in game.
 * @author kipsu
 */
public class NewWeaponEvent {
	
	private Weapon weapon;

	/**
	 * Adds a new weapon to display in ui.
	 * @param weapon 
	 */
	public NewWeaponEvent(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	
}
