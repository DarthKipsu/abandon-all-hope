
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.DayChanger;
import java.util.Random;

/**
 * Decides how much metal to give to player
 * @author kipsu
 */
public class MetalLoot implements Loot {
	
	private Random random = new Random();

	@Override
	public String giveOut(Inventory inventory) {
		int amount = random.nextInt(4) + 2;
		if (DayChanger.day > 12) {
			amount /= 2;
		}
		inventory.addMetal(amount);
		return amount + " metal.";
	}
	
}
