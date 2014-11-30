
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import java.util.Random;

/**
 *
 * @author kipsu
 */
public class BulletLoot implements Loot {
	
	private Random random = new Random();

	@Override
	public String giveOut(Inventory inventory) {
		int amount = random.nextInt(22)/10 + 1;
		inventory.addPistolBullets(amount);
		return amount + " pistol bullets.";
	}
	
}
