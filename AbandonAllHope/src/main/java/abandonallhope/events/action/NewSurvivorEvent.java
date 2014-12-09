
package abandonallhope.events.action;

import abandonallhope.domain.Survivor;

/**
 * Event called when new survivor is added in game.
 * @author kipsu
 */
public class NewSurvivorEvent {
	
	private Survivor survivor;

	/**
	 * Adds a new survivor to ui.
	 * @param survivor survivor to be added.
	 */
	public NewSurvivorEvent(Survivor survivor) {
		this.survivor = survivor;
	}

	public Survivor getSurvivor() {
		return survivor;
	}
	
}
