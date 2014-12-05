
package abandonallhope.events.handlers;

import abandonallhope.events.action.NewFirearmEvent;

/**
 * Handles adding new firearms.
 * @author kipsu
 */
public interface NewFirearmEventHandler {
	void handle(NewFirearmEvent e);
}
