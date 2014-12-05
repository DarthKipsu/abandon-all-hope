
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.ResourceEvents;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WeaponLootTest {
	
	private WeaponLoot loot;
	private Inventory inventory;
	
	@Before
	public void setUp() {
		loot = new WeaponLoot(new ResourceEvents());
		inventory = new Inventory();
	}
	
	@Test
	public void returnsTheNameOfTheWeaponAdded() {
		assertEquals("axe", loot.giveOut(inventory));
	}
	
	@Test
	public void addsTheGunToTheInventory() {
		loot.giveOut(inventory);
		assertEquals(1, inventory.getWeapons().size());
	}
	
}
