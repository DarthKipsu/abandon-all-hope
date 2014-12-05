package abandonallhope.logic;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Pistol;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.events.action.DeleteFirearmEvent;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.action.DeleteWeaponEvent;
import abandonallhope.events.action.NewFirearmEvent;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.action.NewWeaponEvent;
import abandonallhope.events.handlers.DeleteFirearmEventHandler;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.DeleteWeaponEventHandler;
import abandonallhope.events.handlers.NewFirearmEventHandler;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.events.handlers.NewWeaponEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResourceEventsTest {

	private ResourceEvents resEvents;

	@Before
	public void setUp() {
		resEvents = new ResourceEvents();
	}
	
	@Test
	public void noEventHandlersInTheBeginning() {
		assertTrue(resEvents.newSurvivorEventHandlers.isEmpty());
		assertTrue(resEvents.deleteSurvivorEventHandlers.isEmpty());
		assertTrue(resEvents.newFirearmEventHandlers.isEmpty());
		assertTrue(resEvents.deleteFirearmEventHandlers.isEmpty());
		assertTrue(resEvents.newWeaponEventHandlers.isEmpty());
		assertTrue(resEvents.deleteWeaponEventHandlers.isEmpty());
	}

	@Test
	public void newSurvivorEventsCanBeAdded() {
		resEvents.addNewResourceEventHandler(newSurvivorEvent(), "newSurvivor");
		assertEquals(1, resEvents.newSurvivorEventHandlers.size());
		assertTrue(resEvents.deleteSurvivorEventHandlers.isEmpty());
	}

	@Test
	public void deleteSurvivorEventsCanBeAdded() {
		resEvents.addNewResourceEventHandler(deleteSurvivorEvent(), "deleteSurvivor");
		assertEquals(1, resEvents.deleteSurvivorEventHandlers.size());
		assertTrue(resEvents.newSurvivorEventHandlers.isEmpty());
	}

	@Test
	public void newWeaponEventsCanBeAdded() {
		resEvents.addNewResourceEventHandler(newWeaponEvent(), "newWeapon");
		assertEquals(1, resEvents.newWeaponEventHandlers.size());
	}

	@Test
	public void deleteWeaponEventsCanBeAdded() {
		resEvents.addNewResourceEventHandler(deleteWeaponEvent(), "deleteWeapon");
		assertEquals(1, resEvents.deleteWeaponEventHandlers.size());
	}

	@Test
	public void newFirearmEventsCanBeAdded() {
		resEvents.addNewResourceEventHandler(newFirearmEvent(), "newFirearm");
		assertEquals(1, resEvents.newFirearmEventHandlers.size());
	}

	@Test
	public void deleteFirearmEventsCanBeAdded() {
		resEvents.addNewResourceEventHandler(deleteFirearmEvent(), "deleteFirearm");
		assertEquals(1, resEvents.deleteFirearmEventHandlers.size());
	}
	
	@Test
	public void newSurvivorEventsCanBeTriggered() {
		NewSurvivorEventHandlerMock res = (NewSurvivorEventHandlerMock) newSurvivorEvent();
		resEvents.addNewResourceEventHandler(res, "newSurvivor");
		resEvents.triggerNewSurvivorEvent(new Survivor(null, null, null, 1));
		assertEquals(1, res.handleEvents);
	}
	
	@Test
	public void deleteSurvivorEventsCanBeTriggered() {
		DeleteSurvivorEventHandlerMock res = (DeleteSurvivorEventHandlerMock) deleteSurvivorEvent();
		resEvents.addNewResourceEventHandler(res, "deleteSurvivor");
		resEvents.triggerDeleteSurvivorEvent(new Survivor(null, null, null, 1));
		assertEquals(1, res.handleEvents);
	}
	
	@Test
	public void deleteFirearmEventsCanBeTriggered() {
		DeleteFirearmEventHandlerMock res = (DeleteFirearmEventHandlerMock) deleteFirearmEvent();
		resEvents.addNewResourceEventHandler(res, "deleteFirearm");
		resEvents.triggerDeleteFirearmEvent(new Pistol(new Inventory()));
		assertEquals(1, res.handleEvents);
	}
	
	@Test
	public void deleteWeaponEventsCanBeTriggered() {
		DeleteWeaponEventHandlerMock res = (DeleteWeaponEventHandlerMock) deleteWeaponEvent();
		resEvents.addNewResourceEventHandler(res, "deleteWeapon");
		resEvents.triggerDeleteWeaponEvent(new Weapon(1, 1));
		assertEquals(1, res.handleEvents);
	}

	private ResourceEventHandler newSurvivorEvent() {
		return new NewSurvivorEventHandlerMock();
	}

	private ResourceEventHandler newFirearmEvent() {
		return new NewFirearmEventHandlerMock();
	}

	private ResourceEventHandler newWeaponEvent() {
		return new NewWeaponEventHandlerMock();
	}

	private ResourceEventHandler deleteSurvivorEvent() {
		return new DeleteSurvivorEventHandlerMock();
	}

	private ResourceEventHandler deleteFirearmEvent() {
		return new DeleteFirearmEventHandlerMock();
	}

	private ResourceEventHandler deleteWeaponEvent() {
		return new DeleteWeaponEventHandlerMock();
	}

	private class NewWeaponEventHandlerMock implements NewWeaponEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewWeaponEvent e) {
			handleEvents++;
		}
	}

	private class NewFirearmEventHandlerMock implements NewFirearmEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewFirearmEvent e) {
			handleEvents++;
		}
	}

	private class NewSurvivorEventHandlerMock implements NewSurvivorEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewSurvivorEvent e) {
			handleEvents++;
		}
	}

	private class DeleteSurvivorEventHandlerMock implements DeleteSurvivorEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(DeleteSurvivorEvent e) {
			handleEvents++;
		}
	}

	private class DeleteWeaponEventHandlerMock implements DeleteWeaponEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(DeleteWeaponEvent e) {
			handleEvents++;
		}
	}

	private class DeleteFirearmEventHandlerMock implements DeleteFirearmEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(DeleteFirearmEvent e) {
			handleEvents++;
		}
	}

}
