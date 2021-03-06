
package abandonallhope.domain.weapons;

import abandonallhope.domain.Inventory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PistolTest {
	
	Firearm pistol;
	Magazine bullets;

	@Before
	public void setUp() {
		Inventory inventory = new Inventory();
		bullets = inventory.getPistolBullets();
		pistol = new Pistol(inventory);
	}

	@Test
	public void testSomeMethod() {
		
	}

	@Test
	public void rangeIs50() {
		assertEquals(50.0, pistol.getRange(), 0.1);
	}

	@Test
	public void canNotUsedImmediatelyWithoutBullets() {
		assertFalse(pistol.canBeUsed());
	}

	@Test
	public void canBeUsedImmediatelyWithBullets() {
		bullets.add(1);
		assertTrue(pistol.canBeUsed());
	}

	@Test
	public void cannotBeUsedTwiceAtTheSameTime() {
		bullets.add(2);
		pistol.use();
		assertFalse(pistol.canBeUsed());
	}

	@Test
	public void canBeUsedAgainAfter600Frames() {
		bullets.add(2);
		pistol.use();
		decreaseRounds(300);
		assertTrue(pistol.canBeUsed());
	}

	@Test
	public void canNotUsedAgainAfter600FramesIfNoBullets() {
		bullets.add(1);
		pistol.use();
		decreaseRounds(300);
		assertFalse(pistol.canBeUsed());
	}

	@Test
	public void canNotBeUsedAgainAfter299Frames() {
		bullets.add(2);
		pistol.use();
		decreaseRounds(299);
		assertFalse(pistol.canBeUsed());
	}
	
	@Test
	public void returnsACorrectName() {
		assertEquals("pistol", pistol.toString());
	}
	
	private void decreaseRounds(int frames) {
		for (int i = 0; i < frames; i++) {
			pistol.decreaseRoundsToUse();
		}
	}
	
}
