
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Pistol;
import abandonallhope.logic.ResourceEvents;

/**
 * Used to add firearms to inventory.
 * @author kipsu
 */
public class FirearmLoot implements Loot {

	private ResourceEvents resEvents;

	public FirearmLoot(ResourceEvents resEvents) {
		this.resEvents = resEvents;
	}

	@Override
	public String giveOut(Inventory inventory) {
		Firearm firearm = new Pistol(inventory);
		if (!inventory.containsFirearm(firearm)) {
			resEvents.triggerNewFirearmEvent(firearm);
		}
		inventory.addFireamrs(firearm);
		return firearm.toString();
	}
	
}
