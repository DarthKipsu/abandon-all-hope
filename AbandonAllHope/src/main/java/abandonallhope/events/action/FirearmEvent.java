
package abandonallhope.events.action;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.logic.ResourceEvents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

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
	 * @param inventory
	 * @param survivor
	 * @param resEvent
	 * @param combo 
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
		if (!inventory.containsFirearm(firearm)) {
			resEvent.triggerNewFirearmEvent(firearm);
		}
		inventory.addFireamrs(firearm);
		survivor.setGun(null);
	}

	private void giveSurvivorAFirearm() {
		Firearm firearm = inventory.getGuns().get(0);
		survivor.setGun(firearm);
		inventory.getGuns().remove(0);
		if (inventory.getGuns().isEmpty()) {
			resEvent.triggerDeleteFirearmEvent(firearm);
		}
	}
	
}
