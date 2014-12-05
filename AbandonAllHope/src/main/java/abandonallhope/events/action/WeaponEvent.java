package abandonallhope.events.action;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.logic.Game;
import abandonallhope.logic.ResourceEvents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

/**
 * Event for giving a survivor a new weapon.
 *
 * @author kipsu
 */
public class WeaponEvent implements ChangeListener<String> {

	private Inventory inventory;
	private Survivor survivor;
	private ResourceEvents resEvent;
	private ComboBox combo;

	public WeaponEvent(Inventory inventory, Survivor survivor, Game game, ComboBox combo) {
		this.inventory = inventory;
		this.survivor = survivor;
		this.combo = combo;
		resEvent = game.getResourceEvents();
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
		if (!inventory.containsWeapon(weapon)) {
			resEvent.triggerNewWeaponEvent(weapon);
		}
		inventory.addWeapons(weapon);
		survivor.setWeapon(null);
	}

	private void giveSurvivorAWeapon() {
		Weapon weapon = inventory.getWeapons().get(0);
		survivor.setWeapon(weapon);
		inventory.getWeapons().remove(0);
		if (inventory.getWeapons().isEmpty()) {
			resEvent.triggerDeleteWeaponEvent(weapon);
		}
	}

}
