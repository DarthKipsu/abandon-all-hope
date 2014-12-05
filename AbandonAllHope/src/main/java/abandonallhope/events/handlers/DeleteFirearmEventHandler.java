
package abandonallhope.events.handlers;

import abandonallhope.events.action.DeleteFirearmEvent;

/**
 * Handles deleting firearms from ui drop down lists.
 * @author kipsu
 */
public interface DeleteFirearmEventHandler {
	void handle(DeleteFirearmEvent e);
}
