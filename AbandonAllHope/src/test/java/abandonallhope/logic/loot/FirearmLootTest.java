
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.weapons.Pistol;
import abandonallhope.events.action.NewFirearmEvent;
import abandonallhope.events.handlers.NewFirearmEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import abandonallhope.logic.GameTest;
import abandonallhope.logic.ResourceEvents;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FirearmLootTest {
	
	private FirearmLoot loot;
	private Inventory inventory;
	private ResourceEvents resEvents;
	
	@Before
	public void setUp() {
		resEvents = new ResourceEvents();
		loot = new FirearmLoot(resEvents);
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
	
	@Test
	public void createsNewFirearmEventWhenInventoryDoesNotYetContainTheFirearm() {
		NewFirearmEventHandlerMock nfehm = (NewFirearmEventHandlerMock)newFirearmMock();
		resEvents.addNewResourceEventHandler(nfehm, "newFirearm");
		loot.giveOut(inventory);
		assertEquals(1, nfehm.handleEvents);
	}
	
	@Test
	public void doesNotCreateNewFirearmEventIfFirearmAlreadyInInventory() {
		NewFirearmEventHandlerMock nfehm = (NewFirearmEventHandlerMock)newFirearmMock();
		resEvents.addNewResourceEventHandler(nfehm, "newFirearm");
		inventory.addFireamrs(new Pistol(inventory));
		loot.giveOut(inventory);
		assertEquals(0, nfehm.handleEvents);
	}

	private ResourceEventHandler newFirearmMock() {
		return new NewFirearmEventHandlerMock();
	}

	private class NewFirearmEventHandlerMock implements NewFirearmEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewFirearmEvent e) {
			handleEvents++;
		}
	}
	
}
