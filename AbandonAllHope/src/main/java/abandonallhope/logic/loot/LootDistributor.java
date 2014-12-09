package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.ResourceEvents;
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
	public LootDistributor(Inventory inventory, ResourceEvents resEvents) {
		this.inventory = inventory;
		random = new Random();
		loot = addLootTypes(resEvents);
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
		for (int i = 0; i < amount; i++) {
			getLoot();
		}
	}

	private Loot[] addLootTypes(ResourceEvents resEvents) {
		BulletLoot bullets = new BulletLoot();
		WoodLoot wood = new WoodLoot();
		return new Loot[]{
			bullets, bullets, bullets,
			new WeaponLoot(resEvents),
			new FirearmLoot(resEvents),
			new MetalLoot(),
			wood, wood
		};
	}

}
