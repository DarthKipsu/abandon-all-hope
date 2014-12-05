
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MetalLootTest {
	
	private MetalLoot loot;
	private Inventory inventory;
	
	@Before
	public void setUp() {
		loot = new MetalLoot();
		inventory = new Inventory();
	}
	
	@Test
	public void dropsAtLeastTwoMetal() {
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) > 1);
		}
	}
	
	@Test
	public void dropsNoMoreThan5Metal() {
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) < 6);
		}
	}
	
	@Test
	public void placesMetalIntoThePlayerInventory() {
		loot.giveOut(inventory);
		assertTrue(inventory.getMetal() > 0);
	}
	
}
