
package abandonallhope.events.handlers;

import abandonallhope.events.action.NewWeaponEvent;

/**
 * Handles adding new weapons.
 * @author kipsu
 */
public interface NewWeaponEventHandler extends ResourceEventHandler {
	void handle(NewWeaponEvent e);
}
