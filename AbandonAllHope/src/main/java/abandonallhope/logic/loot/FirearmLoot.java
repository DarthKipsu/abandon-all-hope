
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Pistol;

/**
 * Used to add firearms to inventory.
 * @author kipsu
 */
public class FirearmLoot implements Loot {

	@Override
	public String giveOut(Inventory inventory) {
		Firearm firearm = new Pistol(inventory);
		inventory.addFireamrs(firearm);
		return firearm.toString();
	}
	
}
