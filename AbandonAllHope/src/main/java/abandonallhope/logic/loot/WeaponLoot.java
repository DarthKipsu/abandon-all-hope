
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Weapon;

/**
 * Used to add melee weapons to inventory.
 * @author kipsu
 */
public class WeaponLoot implements Loot {

	@Override
	public String giveOut(Inventory inventory) {
		Weapon weapon = new Axe();
		inventory.addWeapons(weapon);
		return weapon.toString();
	}
	
}
