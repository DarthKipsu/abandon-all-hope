
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains resource events used to update resource panel.
 * @author kipsu
 */
public class ResourceEvents {
	
	private Set<NewSurvivorEventHandler> newSurvivorEventHandlers;
	private Set<DeleteSurvivorEventHandler> deleteSurvivorEventHandlers;

	/**
	 * Creates a new resource event container class.
	 */
	public ResourceEvents() {
		newSurvivorEventHandlers = new HashSet<>();
		deleteSurvivorEventHandlers = new HashSet<>();
	}
	
	/**
	 * Adds a new resource event handler.
	 * @param event the event handler to be added
	 * @param type type of the handler
	 */
	public void addNewResourceEventHandler(ResourceEventHandler event, String type) {
		if (type.equals("newSurvivor")) {
			newSurvivorEventHandlers.add((NewSurvivorEventHandler)event);
		} else if (type.equals("deleteSurvivor")) {
			deleteSurvivorEventHandlers.add((DeleteSurvivorEventHandler)event);
		}
	}
	
	/**
	 * Triggers an event notifying resources panel to display a new survivor.
	 * @param survivor survivor to be displayed
	 */
	public void triggerNewSurvivorEvent(Survivor survivor) {
		NewSurvivorEvent newSurvivorEvent = new NewSurvivorEvent(survivor);
		for (NewSurvivorEventHandler nseh : newSurvivorEventHandlers) {
			nseh.handle(newSurvivorEvent);
		}
	}
	
	/**
	 * Triggers an event notifying resources panel to delete a survivor display.
	 * @param survivor survivor to be deleted.
	 */
	public void triggerDeleteSurvivorEvent(Survivor survivor) {
		DeleteSurvivorEvent deleteSurvivorEvent = new DeleteSurvivorEvent(survivor);
		for (DeleteSurvivorEventHandler dseh : deleteSurvivorEventHandlers) {
			dseh.handle(deleteSurvivorEvent);
		}
	}
}
