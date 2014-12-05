
package abandonallhope.events.action;

import abandonallhope.domain.weapons.Weapon;

/**
 * Event called when weapon needs to be removed from weapons list in ui.
 * @author kipsu
 */
public class DeleteWeaponEvent {
	
	private Weapon weapon;

	public DeleteWeaponEvent(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	
}
