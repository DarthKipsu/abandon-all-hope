
package abandonallhope.events.action;

import abandonallhope.domain.weapons.Firearm;

/**
 * New firearm is added to game event.
 * @author kipsu
 */
public class NewFirearmEvent {
	
	private Firearm firearm;

	/**
	 * Sets a new firearm to select from ui.
	 * @param firearm 
	 */
	public NewFirearmEvent(Firearm firearm) {
		this.firearm = firearm;
	}

	public Firearm getFirearm() {
		return firearm;
	}
	
}
