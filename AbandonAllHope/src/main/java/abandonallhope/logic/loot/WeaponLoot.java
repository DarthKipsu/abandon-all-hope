
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Axe;
import java.util.Random;

/**
 * Used to add melee weapons to inventory.
 * @author kipsu
 */
public class WeaponLoot implements Loot {
	
	private Random random = new Random();

	@Override
	public String giveOut(Inventory inventory) {
		inventory.addWeapons(new Axe());
		return "axe.";
	}
	
}
