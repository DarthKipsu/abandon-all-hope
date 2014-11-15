
package abandonallhope.domain.items;

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
