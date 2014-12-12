package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.events.action.*;
import abandonallhope.events.handlers.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains resource events used to update resource panel.
 *
 * @author kipsu
 */
public class ResourceEvents {

	protected Set<NewSurvivorEventHandler> newSurvivorEventHandlers;
	protected Set<DeleteSurvivorEventHandler> deleteSurvivorEventHandlers;
	protected Set<NewWeaponEventHandler> newWeaponEventHandlers;
	protected Set<DeleteWeaponEventHandler> deleteWeaponEventHandlers;
	protected Set<NewFirearmEventHandler> newFirearmEventHandlers;
	protected Set<DeleteFirearmEventHandler> deleteFirearmEventHandlers;

	/**
	 * Creates a new resource event container class.
	 */
	public ResourceEvents() {
		newSurvivorEventHandlers = new HashSet<>();
		deleteSurvivorEventHandlers = new HashSet<>();
		newWeaponEventHandlers = new HashSet<>();
		deleteWeaponEventHandlers = new HashSet<>();
		newFirearmEventHandlers = new HashSet<>();
		deleteFirearmEventHandlers = new HashSet<>();
	}

	/**
	 * Adds a new resource event handler.
	 *
	 * @param event the event handler to be added
	 * @param type type of the handler
	 */
	public void addNewResourceEventHandler(ResourceEventHandler event, String type) {
		if (type.equals("newSurvivor")) {
			newSurvivorEventHandlers.add((NewSurvivorEventHandler) event);
		} else if (type.equals("deleteSurvivor")) {
			deleteSurvivorEventHandlers.add((DeleteSurvivorEventHandler) event);
		} else if (type.equals("newWeapon")) {
			newWeaponEventHandlers.add((NewWeaponEventHandler) event);
		} else if (type.equals("deleteWeapon")) {
			deleteWeaponEventHandlers.add((DeleteWeaponEventHandler) event);
		} else if (type.equals("newFirearm")) {
			newFirearmEventHandlers.add((NewFirearmEventHandler) event);
		} else if (type.equals("deleteFirearm")) {
			deleteFirearmEventHandlers.add((DeleteFirearmEventHandler) event);
		}
	}

	/**
	 * Triggers an event notifying resources panel to display a new survivor.
	 *
	 * @param survivor survivor to be displayed
	 */
	public void triggerNewSurvivorEvent(Survivor survivor) {
		NewSurvivorEvent newSurvivorEvent = new NewSurvivorEvent(survivor);
		newSurvivorEventHandlers
				.parallelStream()
				.forEach(nseh -> nseh.handle(newSurvivorEvent));
	}

	/**
	 * Triggers an event notifying resources panel to delete a survivor display.
	 *
	 * @param survivor survivor to be deleted.
	 */
	public void triggerDeleteSurvivorEvent(Survivor survivor) {
		DeleteSurvivorEvent deleteSurvivorEvent = new DeleteSurvivorEvent(survivor);
		deleteSurvivorEventHandlers
				.parallelStream()
				.forEach(dseh -> dseh.handle(deleteSurvivorEvent));
	}

	/**
	 * Triggers an event notifying resources panel to display a new weapon.
	 *
	 * @param weapon weapon to be added
	 */
	public void triggerNewWeaponEvent(Weapon weapon) {
		NewWeaponEvent newWeaponEvent = new NewWeaponEvent(weapon);
		newWeaponEventHandlers
				.parallelStream()
				.forEach(nweh -> nweh.handle(newWeaponEvent));
	}

	/**
	 * Triggers an event notifying resources panel to delete a weapon from
	 * display.
	 *
	 * @param weapon weapon to be added
	 */
	public void triggerDeleteWeaponEvent(Weapon weapon) {
		DeleteWeaponEvent deleteWeaponEvent = new DeleteWeaponEvent(weapon);
		deleteWeaponEventHandlers
				.parallelStream()
				.forEach(dweh -> dweh.handle(deleteWeaponEvent));
	}

	/**
	 * Triggers an event notifying resources panel to display a new firearm.
	 *
	 * @param firearm weapon to be added
	 */
	public void triggerNewFirearmEvent(Firearm firearm) {
		NewFirearmEvent newFirearmEvent = new NewFirearmEvent(firearm);
		newFirearmEventHandlers
				.parallelStream()
				.forEach(nfeh -> nfeh.handle(newFirearmEvent));
	}

	/**
	 * Triggers an event notifying resources panel to delete a firearm from
	 * display.
	 *
	 * @param firearm weapon to be added
	 */
	public void triggerDeleteFirearmEvent(Firearm firearm) {
		DeleteFirearmEvent deleteFirearmEvent = new DeleteFirearmEvent(firearm);
		deleteFirearmEventHandlers
				.parallelStream()
				.forEach(dfeh -> dfeh.handle(deleteFirearmEvent));
	}
}
