
package abandonallhope.domain.weapons;

import abandonallhope.domain.weapons.Weapon;
import abandonallhope.domain.weapons.Axe;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WeaponTest {
	
	private Weapon weapon;
	
	@Before
	public void setUp() {
		weapon = new Axe();
	}
	
	@Test
	public void roundsToUseCannotBeNegative() {
		weapon.decreaseRoundsToUse();
		assertEquals(0, weapon.roundsToUse);
	}
	
}
