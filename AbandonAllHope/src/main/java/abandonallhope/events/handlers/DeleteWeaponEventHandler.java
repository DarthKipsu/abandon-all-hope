
package abandonallhope.events.handlers;

import abandonallhope.events.action.DeleteWeaponEvent;

/**
 * Handles removing weapons from ui view.
 * @author kipsu
 */
public interface DeleteWeaponEventHandler extends ResourceEventHandler {
	void handle(DeleteWeaponEvent e);
}
