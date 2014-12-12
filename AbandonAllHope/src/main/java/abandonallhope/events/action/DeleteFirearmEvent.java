
package abandonallhope.events.action;

import abandonallhope.domain.weapons.Firearm;

/**
 * Deletes a firearm from ui drop down list.
 * @author kipsu
 */
public class DeleteFirearmEvent {
	
	private Firearm firearm;

	/**
	 * Delete given firearm from ui.
	 * @param firearm firearm to be added
	 */
	public DeleteFirearmEvent(Firearm firearm) {
		this.firearm = firearm;
	}

	public Firearm getFirearm() {
		return firearm;
	}
}
