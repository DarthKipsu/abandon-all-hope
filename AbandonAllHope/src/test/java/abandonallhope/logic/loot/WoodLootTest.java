
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WoodLootTest {
	
	private WoodLoot loot;
	private Inventory inventory;
	
	@Before
	public void setUp() {
		loot = new WoodLoot();
		inventory = new Inventory();
	}
	
	@Test
	public void dropsAtLeastThreeWood() {
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 2).trim()) > 2);
		}
	}
	
	@Test
	public void dropsNoMoreThan10Wood() {
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 2).trim()) < 11);
		}
	}
	
	@Test
	public void placesMetalIntoThePlayerInventory() {
		loot.giveOut(inventory);
		assertTrue(inventory.getWood() > 0);
	}
	
}
