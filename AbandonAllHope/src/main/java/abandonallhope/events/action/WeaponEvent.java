package abandonallhope.events.action;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.logic.Game;
import abandonallhope.logic.ResourceEvents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Event for giving a survivor a new weapon.
 *
 * @author kipsu
 */
public class WeaponEvent implements ChangeListener<String> {

	private Inventory inventory;
	private Survivor survivor;
	private ResourceEvents resEvent;

	public WeaponEvent(Inventory inventory, Survivor survivor, Game game) {
		this.inventory = inventory;
		this.survivor = survivor;
		resEvent = game.getResourceEvents();
	}

	@Override
	public void changed(ObservableValue<? extends String> ov, String previousWeapon, String newWeapon) {
		if (newWeapon.equals("none")) {
			Weapon weapon = survivor.getWeapon();
			if (!inventory.containsWeapon(weapon)) {
				resEvent.triggerNewWeaponEvent(weapon);
			}
			inventory.addWeapons(weapon);
			survivor.setWeapon(null);
		} else {
			survivor.setWeapon(inventory.getWeapons().get(0));
			inventory.getWeapons().remove(0);
		}
	}

}
