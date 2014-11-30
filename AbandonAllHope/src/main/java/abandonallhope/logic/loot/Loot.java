
package abandonallhope.logic.loot;

import abandonallhope.domain.Inventory;

/**
 * Objects implementing this class will give out loot to players after zombie kill.
 * @author kipsu
 */
public interface Loot {
	public String giveOut(Inventory inventory);
}
