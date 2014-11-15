
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import java.util.List;

public final class Collision {

	private Collision() {
	}
	
	public static Survivor survivor(Zombie zombie, List<Survivor> survivors) {
		for (Survivor survivor : survivors) {
			if (zombie.occupiedArea().intersects(survivor.occupiedArea())) {
				return survivor;
			}
		}
		return null;
	}
	
}
