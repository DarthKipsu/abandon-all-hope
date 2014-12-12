
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.DayChanger;
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
		DayChanger ds = new DayChanger();
	}
	
	@Test
	public void dropsAtLeastThreeWood() {
		DayChanger.day = 12;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 2).trim()) > 2);
		}
	}
	
	@Test
	public void dropsAtLeast1WoodAfterDay12() {
		DayChanger.day = 13;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 2).trim()) > 0);
		}
	}
	
	@Test
	public void dropsNoMoreThan10Wood() {
		DayChanger.day = 12;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 2).trim()) < 11);
		}
	}
	
	@Test
	public void dropsNoMoreThan3WoodAfterDay12() {
		DayChanger.day = 13;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 2).trim()) < 4);
		}
	}
	
	@Test
	public void placesMetalIntoThePlayerInventory() {
		loot.giveOut(inventory);
		assertTrue(inventory.getWood() > 0);
	}
	
}
