
package abandonallhope.events.handlers;

import abandonallhope.events.action.NewFirearmEvent;

/**
 * Handles adding new firearms.
 * @author kipsu
 */
public interface NewFirearmEventHandler extends ResourceEventHandler {
	void handle(NewFirearmEvent e);
}
