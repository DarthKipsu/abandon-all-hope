package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.logic.ResourceEvents;

/**
 * Used to add melee weapons to inventory.
 *
 * @author kipsu
 */
public class WeaponLoot implements Loot {

	private ResourceEvents resEvents;

	public WeaponLoot(ResourceEvents resEvents) {
		this.resEvents = resEvents;
	}

	@Override
	public String giveOut(Inventory inventory) {
		Weapon weapon = new Axe();
		if (!inventory.containsWeapon(weapon)) {
			resEvents.triggerNewWeaponEvent(weapon);
		}
		inventory.addWeapons(weapon);
		return weapon.toString();
	}

}
