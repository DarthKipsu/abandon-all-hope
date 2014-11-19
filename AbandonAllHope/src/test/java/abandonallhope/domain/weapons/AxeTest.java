
package abandonallhope.domain.weapons;

import abandonallhope.domain.weapons.Axe;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AxeTest {
	
	private Axe axe;
	
	@Before
	public void setUp() {
		axe = new Axe();
	}

	@Test
	public void rangeIs3() {
		assertEquals(3.0, axe.getRange(), 0.1);
	}

	@Test
	public void canBeUsedImmediately() {
		assertTrue(axe.canBeUsed());
	}

	@Test
	public void cannotBeUsedTwiceAtTheSameTime() {
		axe.use();
		assertFalse(axe.canBeUsed());
	}

	@Test
	public void canBeUsedAgainAfter120Frames() {
		axe.use();
		decreaseRounds(120);
		assertTrue(axe.canBeUsed());
	}

	@Test
	public void canNotBeUsedAgainAfter119Frames() {
		axe.use();
		decreaseRounds(119);
		assertFalse(axe.canBeUsed());
	}
	
	private void decreaseRounds(int frames) {
		for (int i = 0; i < frames; i++) {
			axe.decreaseRoundsToUse();
		}
	}
	
}
