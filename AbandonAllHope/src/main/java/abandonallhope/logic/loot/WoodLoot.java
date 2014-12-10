
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.DayChanger;
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
		if (DayChanger.day > 15) {
			amount /= 2;
		}
		inventory.addWood(amount);
		return amount + " wood.";
	}
	
}
