package abandonallhope.events.action;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.logic.ResourceEvents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Event for giving a survivor a new weapon.
 * @author kipsu
 */
public class WeaponEvent implements ChangeListener<String> {

	private Inventory inventory;
	private Survivor survivor;
	private ResourceEvents resEvent;

	/**
	 * Creates a new weapon event handling changing survivor weapons.
	 * @param inventory inventory containing weapons
	 * @param survivor survivor whose weapon is changed
	 * @param resEvent resource events used to trigger events
	 */
	public WeaponEvent(Inventory inventory, Survivor survivor, ResourceEvents resEvent) {
		this.inventory = inventory;
		this.survivor = survivor;
		this.resEvent = resEvent;
	}

	@Override
	public void changed(ObservableValue<? extends String> ov, String previousWeapon, String newWeapon) {
		if (newWeapon.equals("none")) {
			removeSurvivorWeapon();
		} else {
			giveSurvivorAWeapon();
		}
	}

	private void removeSurvivorWeapon() {
		Weapon weapon = survivor.getWeapon();
		setAvailableForOtherSurvivors(weapon);
		inventory.addWeapons(weapon);
		survivor.setWeapon(null);
	}

	private void setAvailableForOtherSurvivors(Weapon weapon) {
		if (!inventory.containsWeapon(weapon)) {
			resEvent.triggerNewWeaponEvent(weapon);
		}
	}

	private void giveSurvivorAWeapon() {
		Weapon weapon = getWeaponFromInventory();
		survivor.setWeapon(weapon);
		setUnavailableForOtherSurvivors(weapon);
	}

	private Weapon getWeaponFromInventory() {
		Weapon weapon = inventory.getWeapons().get(0);
		inventory.getWeapons().remove(0);
		return weapon;
	}

	private void setUnavailableForOtherSurvivors(Weapon weapon) {
		if (inventory.getWeapons().isEmpty()) {
			resEvent.triggerDeleteWeaponEvent(weapon);
		}
	}

}
