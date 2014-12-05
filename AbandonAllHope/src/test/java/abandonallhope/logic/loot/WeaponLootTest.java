
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.events.action.NewWeaponEvent;
import abandonallhope.events.handlers.NewWeaponEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import abandonallhope.logic.ResourceEvents;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WeaponLootTest {
	
	private WeaponLoot loot;
	private Inventory inventory;
	private ResourceEvents resEvents;
	
	@Before
	public void setUp() {
		resEvents = new ResourceEvents();
		loot = new WeaponLoot(resEvents);
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
	
	@Test
	public void createsNewFirearmEventWhenInventoryDoesNotYetContainTheFirearm() {
		NewWeaponEventHandlerMock nfehm = (NewWeaponEventHandlerMock)newWeaponMock();
		resEvents.addNewResourceEventHandler(nfehm, "newWeapon");
		loot.giveOut(inventory);
		assertEquals(1, nfehm.handleEvents);
	}
	
	@Test
	public void doesNotCreateNewFirearmEventIfFirearmAlreadyInInventory() {
		NewWeaponEventHandlerMock nfehm = (NewWeaponEventHandlerMock)newWeaponMock();
		resEvents.addNewResourceEventHandler(nfehm, "newWeapon");
		inventory.addWeapons(new Axe());
		loot.giveOut(inventory);
		assertEquals(0, nfehm.handleEvents);
	}

	private ResourceEventHandler newWeaponMock() {
		return new NewWeaponEventHandlerMock();
	}

	private class NewWeaponEventHandlerMock implements NewWeaponEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewWeaponEvent e) {
			handleEvents++;
		}
	}
	
}
