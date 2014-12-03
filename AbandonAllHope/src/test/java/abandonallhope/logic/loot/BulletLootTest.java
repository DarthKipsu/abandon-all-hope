
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BulletLootTest {
	
	private BulletLoot loot;
	private Inventory inventory;
	
	@Before
	public void setUp() {
		loot = new BulletLoot();
		inventory = new Inventory();
	}
	
	@Test
	public void dropsAtLeastOneBullet() {
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) > 0);
		}
	}
	
	@Test
	public void doesNotDropMoreThan3Bullets() {
		for (int i = 0; i < 100; i++) {
			assertTrue(Integer.parseInt(loot.giveOut(new Inventory()).substring(0, 1)) < 4);
		}
	}
	
	@Test
	public void placesBulletsIntoThePlayerInventory() {
		loot.giveOut(inventory);
		assertTrue(inventory.getPistolBullets().notEmpty());
	}
	
}
