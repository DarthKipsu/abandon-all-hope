package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import java.util.Random;

/**
 * Will divide loot from a zombie.
 *
 * @author kipsu
 */
public class LootDistributor {

	private Inventory inventory;
	private Random random;
	private final Loot[] loot;

	/**
	 * Create new loot distributor to distribute loot to player after killing zombies.
	 * @param inventory inventory where the loot is added
	 */
	public LootDistributor(Inventory inventory) {
		this.inventory = inventory;
		random = new Random();
		loot = new Loot[]{
			new BulletLoot(),
			new WeaponLoot(),
			new FirearmLoot()
		};
	}
	
	/**
	 * Used to add random loot to player, after killing a zombie.
	 * @return string containing the loot type and amount
	 */
	public String getLoot() {
		return loot[random.nextInt(loot.length)].giveOut(inventory);
	}

}
