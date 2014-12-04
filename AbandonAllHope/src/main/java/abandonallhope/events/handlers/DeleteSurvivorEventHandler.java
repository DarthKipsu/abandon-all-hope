
package abandonallhope.events.handlers;

import abandonallhope.events.action.DeleteSurvivorEvent;

/**
 * Handles survivor deletions.
 * @author kipsu
 */
public interface DeleteSurvivorEventHandler extends ResourceEventHandler {
	void handle(DeleteSurvivorEvent e);
}
