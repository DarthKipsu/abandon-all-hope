
package abandonallhope.events.handlers;

import abandonallhope.events.action.NewSurvivorEvent;

/**
 * Handles survivor additions.
 * @author kipsu
 */
public interface NewSurvivorEventHandler extends ResourceEventHandler {
	void handle(NewSurvivorEvent e);
}
