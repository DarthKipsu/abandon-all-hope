
package abandonallhope.domain.weapons;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KatanaTest {
	
	private Katana katana;
	
	@Before
	public void setUp() {
		katana = new Katana();
	}

	@Test
	public void rangeIs5() {
		assertEquals(5.0, katana.getRange(), 0.1);
	}

	@Test
	public void canBeUsedImmediately() {
		assertTrue(katana.canBeUsed());
	}

	@Test
	public void cannotBeUsedTwiceAtTheSameTime() {
		katana.use();
		assertFalse(katana.canBeUsed());
	}

	@Test
	public void canBeUsedAgainAfter1Frames() {
		katana.use();
		katana.decreaseRoundsToUse();
		assertTrue(katana.canBeUsed());
	}

	@Test
	public void returnsACorrectName() {
		assertEquals("katana", katana.toString());
	}
	
}
