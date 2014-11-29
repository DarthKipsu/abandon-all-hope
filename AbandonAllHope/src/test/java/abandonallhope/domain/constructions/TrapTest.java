
package abandonallhope.domain.constructions;

import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import static org.junit.Assert.*;
import org.junit.Test;

public class TrapTest {
	
	private Trap trap;
	
	@Test
	public void returnsCorrectColor() {
		createTrap(TrapType.BEARIRON);
		assertEquals(Color.SANDYBROWN, trap.getColor());
	}
	
	@Test
	public void returnsCorrectLocation() {
		createTrap(TrapType.BEARIRON);
		assertEquals(new Point(10, 10), trap.getLocation());
	}
	
	@Test
	public void returnsCorrectOccupatedArea() {
		createTrap(TrapType.BEARIRON);
		assertEquals(new Rectangle2D(10, 10, 5, 5), trap.occupiedArea());
	}
	
	@Test
	public void hasCapacityInTheBeginning() {
		createTrap(TrapType.BEARIRON);
		assertTrue(trap.hasCapacityLeft());
	}
	
	@Test
	public void doesNotHAveCapacityAfterFilledWithZombies() {
		createTrap(TrapType.BEARIRON);
		trap.addZombie();
		assertFalse(trap.hasCapacityLeft());
	}
	
	private void createTrap(TrapType type) {
		trap = new Trap(new Point(10, 10), TrapType.BEARIRON);
	}
	
}
