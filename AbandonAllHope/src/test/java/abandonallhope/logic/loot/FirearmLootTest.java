
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.logic.ResourceEvents;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FirearmLootTest {
	
	private FirearmLoot loot;
	private Inventory inventory;
	
	@Before
	public void setUp() {
		loot = new FirearmLoot(new ResourceEvents());
		inventory = new Inventory();
	}
	
	@Test
	public void returnsTheNameOfTheGunAdded() {
		assertEquals("pistol", loot.giveOut(inventory));
	}
	
	@Test
	public void addsTheGunToTheInventory() {
		loot.giveOut(inventory);
		assertEquals(1, inventory.getGuns().size());
	}
	
}
