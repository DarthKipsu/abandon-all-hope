package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.DayChanger;
import abandonallhope.logic.ResourceEvents;
import java.util.Random;

/**
 * Will divide loot from a zombie.
 * @author kipsu
 */
public class LootDistributor {

	private Inventory inventory;
	private Random random;
	private final Loot[] loot;

	/**
	 * Create new loot distributor to distribute loot to player after killing zombies.
	 * @param resEvents Resource events used to trigger events
	 * @param inventory inventory where the loot is added
	 */
	public LootDistributor(ResourceEvents resEvents, Inventory inventory) {
		random = new Random();
		loot = addLootTypes(resEvents);
		this.inventory = inventory;
	}
	
	/**
	 * Used to add random loot to player, after killing a zombie.
	 * @return string containing the loot type and amount
	 */
	public String getLoot() {
		return loot[random.nextInt(loot.length)].giveOut(inventory);
	}
	
	/**
	 * Collect loot from n amount of zombies at once
	 * @param amount amount of zombies to collect from
	 */
	public void collectLootFrom(int amount) {
		for (int i = 0; i < amount;) {
			getLoot();
			i++;
			if (DayChanger.day > 12) {
				i++;
			}
		}
	}

	private Loot[] addLootTypes(ResourceEvents resEvents) {
		BulletLoot bullets = new BulletLoot();
		WoodLoot wood = new WoodLoot();
		return new Loot[]{
			bullets, bullets,
			new WeaponLoot(resEvents),
			new FirearmLoot(resEvents),
			new MetalLoot(),
			wood, wood
		};
	}

}
