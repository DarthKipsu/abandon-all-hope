
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import java.util.Random;

/**
 * Decides how much wood to drop as loot.
 * @author kipsu
 */
public class WoodLoot implements Loot {
	
	private Random random = new Random();

	@Override
	public String giveOut(Inventory inventory) {
		int amount = random.nextInt(8) + 3;
		inventory.addWood(amount);
		return amount + " wood.";
	}
	
}
