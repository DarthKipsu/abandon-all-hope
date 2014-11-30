
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Pistol;
import java.util.Random;

/**
 * Used to add firearms to inventory.
 * @author kipsu
 */
public class FirearmLoot implements Loot {
	
	private Random random = new Random();

	@Override
	public String giveOut(Inventory inventory) {
		inventory.addFireamrs(new Pistol(inventory));
		return "pistol.";
	}
	
}
