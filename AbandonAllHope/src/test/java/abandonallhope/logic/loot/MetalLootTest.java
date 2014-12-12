
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.DayChanger;
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
		DayChanger ds = new DayChanger();
	}
	
	@Test
	public void dropsAtLeastTwoMetal() {
		DayChanger.day = 12;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) > 1);
		}
	}
	
	@Test
	public void dropsNoMoreThan5Metal() {
		DayChanger.day = 12;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) < 6);
		}
	}
	
	@Test
	public void dropsAtLeast1MetalAfterDay12() {
		DayChanger.day = 13;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) >0);
		}
	}
	
	@Test
	public void afterDay12DropsNoMoreThan2Metal() {
		DayChanger.day = 13;
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) < 3);
		}
	}
	
	@Test
	public void placesMetalIntoThePlayerInventory() {
		loot.giveOut(inventory);
		assertTrue(inventory.getMetal() > 0);
	}
	
}
