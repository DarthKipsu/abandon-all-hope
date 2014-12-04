package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
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

	private ResourceEventHandler newSurvivorEvent() {
		return new NewSurvivorEventHandlerMock();
	}

	private ResourceEventHandler deleteSurvivorEvent() {
		return new DeleteSurvivorEventHandlerMock();
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

}
