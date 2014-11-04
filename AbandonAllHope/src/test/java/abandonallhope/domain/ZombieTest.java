
package abandonallhope.domain;

import java.awt.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ZombieTest {
	
	private Zombie zombie;
	
	@Before
	public void setUp() {
		zombie = new Zombie(new Point(10, 10));
	}
	
	@Test
	public void movesFiveSquaresUp() {
		zombie.move(0, -1);
		assertEquals(new Point(10, 5), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresDown() {
		zombie.move(0, 1);
		assertEquals(new Point(10, 15), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresRight() {
		zombie.move(1, 0);
		assertEquals(new Point(15, 10), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresLeft() {
		zombie.move(-1, 0);
		assertEquals(new Point(5, 10), zombie.getLocation());
	}
	
	@Test
	public void canMoveDiagonally() {
		zombie.move(-1, -1);
		assertEquals(new Point(5, 5), zombie.getLocation());
	}
	
	@Test
	public void movesOnlyFiveSquaresUpWithOneMove() {
		zombie.move(0, -20);
		assertEquals(new Point(10, 5), zombie.getLocation());
	}
	
	@Test
	public void movesCorrectlyAfterSeveralSteps() {
		zombie.move(0, 20);
		zombie.move(50, 10);
		zombie.move(80, -20);
		assertEquals(new Point(20, 15), zombie.getLocation());
	}
	
}
