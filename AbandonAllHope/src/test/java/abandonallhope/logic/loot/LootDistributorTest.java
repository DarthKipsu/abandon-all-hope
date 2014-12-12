
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.DayChanger;
import abandonallhope.logic.ResourceEvents;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LootDistributorTest {
	
	private LootDistributor distributor;
	private Inventory inventory;
	
	@Before
	public void setUp() {
		inventory = new Inventory();
		distributor = new LootDistributor(new ResourceEvents(), inventory);
		DayChanger ds = new DayChanger();
	}
	
	@Test
	public void doesntReturnEmptyLoot() {
		assertNotNull(distributor.getLoot());
	}
	
	@Test
	public void collectsLootFrom10Zombies() {
		distributor.collectLootFrom(10);
		assertTrue(collectAllLoot() > 10);
	}

	private int collectAllLoot() {
		int loot = 0;
		loot += inventory.getGuns().size();
		loot += inventory.getWeapons().size();
		loot += inventory.getPistolBullets().getBullets();
		loot += inventory.getMetal();
		loot += inventory.getWood();
		return loot;
	}
	
}
