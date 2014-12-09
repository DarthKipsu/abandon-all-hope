
package abandonallhope.events.action;

import abandonallhope.domain.Survivor;

/**
 * Called when a survivor is removed from the game.
 * @author kipsu
 */
public class DeleteSurvivorEvent {
	
	private Survivor survivor;

	/**
	 * Delete given survivor from ui.
	 * @param survivor 
	 */
	public DeleteSurvivorEvent(Survivor survivor) {
		this.survivor = survivor;
	}

	public Survivor getSurvivor() {
		return survivor;
	}
	
}
