package abandonallhope.events.action;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.logic.ResourceEvents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Event handling giving a survivor a new firearm.
 * @author kipsu
 */
public class FirearmEvent implements ChangeListener<String> {

	private Inventory inventory;
	private Survivor survivor;
	private ResourceEvents resEvent;

	/**
	 * Creates a new firearm event handling firearm displays in ui.
	 * @param inventory player inventory
	 * @param survivor survivor whose weapon is changed
	 * @param resEvent event container to trigger events with
	 */
	public FirearmEvent(Inventory inventory, Survivor survivor, ResourceEvents resEvent) {
		this.inventory = inventory;
		this.survivor = survivor;
		this.resEvent = resEvent;
	}

	@Override
	public void changed(ObservableValue<? extends String> ov, String previousFirearm, String newFirearm) {
		if (newFirearm.equals("none")) {
			removeSurvivorFirearm();
		} else {
			giveSurvivorAFirearm();
		}
	}

	private void removeSurvivorFirearm() {
		Firearm firearm = survivor.getGun();
		setAvailableForOtherSurvivors(firearm);
		inventory.addFireamrs(firearm);
		survivor.setGun(null);
	}

	private void setAvailableForOtherSurvivors(Firearm firearm) {
		if (!inventory.containsFirearm(firearm)) {
			resEvent.triggerNewFirearmEvent(firearm);
		}
	}

	private void giveSurvivorAFirearm() {
		Firearm firearm = getFromInventory();
		survivor.setGun(firearm);
		setUnavailableForOtherSurvivors(firearm);
	}

	private Firearm getFromInventory() {
		Firearm firearm = inventory.getGuns().get(0);
		inventory.getGuns().remove(0);
		return firearm;
	}

	private void setUnavailableForOtherSurvivors(Firearm firearm) {
		if (inventory.getGuns().isEmpty()) {
			resEvent.triggerDeleteFirearmEvent(firearm);
		}
	}

}
