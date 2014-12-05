
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.action.DeleteWeaponEvent;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.action.NewWeaponEvent;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.DeleteWeaponEventHandler;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.events.handlers.NewWeaponEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains resource events used to update resource panel.
 * @author kipsu
 */
public class ResourceEvents {
	
	protected Set<NewSurvivorEventHandler> newSurvivorEventHandlers;
	protected Set<DeleteSurvivorEventHandler> deleteSurvivorEventHandlers;
	protected Set<NewWeaponEventHandler> newWeaponEventHandlers;
	protected Set<DeleteWeaponEventHandler> deleteWeaponEventHandlers;

	/**
	 * Creates a new resource event container class.
	 */
	public ResourceEvents() {
		newSurvivorEventHandlers = new HashSet<>();
		deleteSurvivorEventHandlers = new HashSet<>();
		newWeaponEventHandlers = new HashSet<>();
		deleteWeaponEventHandlers = new HashSet<>();
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
		} else if (type.equals("newWeapon")) {
			newWeaponEventHandlers.add((NewWeaponEventHandler)event);
		} else if (type.equals("deleteWeapon")) {
			deleteWeaponEventHandlers.add((DeleteWeaponEventHandler)event);
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
	
	/**
	 * Triggers an event notifying resources panel to display a new weapon.
	 * @param weapon weapon to be added
	 */
	public void triggerNewWeaponEvent(Weapon weapon) {
		NewWeaponEvent newWeaponEvent = new NewWeaponEvent(weapon);
		for (NewWeaponEventHandler nweh : newWeaponEventHandlers) {
			nweh.handle(newWeaponEvent);
		}
	}
	
	/**
	 * Triggers an event notifying resources panel to delete a weapon from display.
	 * @param weapon weapon to be added
	 */
	public void triggerDeleteWeaponEvent(Weapon weapon) {
		DeleteWeaponEvent deleteWeaponEvent = new DeleteWeaponEvent(weapon);
		for (DeleteWeaponEventHandler dweh : deleteWeaponEventHandlers) {
			dweh.handle(deleteWeaponEvent);
		}
	}
}
