
package abandonallhope.domain;

import java.awt.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SurvivorTest {
	
	private Survivor survivor;
	private Map map;
	
	@Before
	public void setUp() {
		map = new Map(30);
		survivor = new Survivor(new Point(10, 10), map);
	}
	
	@Test
	public void movesFiveSquaresUp() {
		survivor.move(0, -1);
		assertEquals(new Point(10, 8), survivor.getLocation());
	}
	
	@Test
	public void movesFiveSquaresDown() {
		survivor.move(0, 1);
		assertEquals(new Point(10, 12), survivor.getLocation());
	}
	
	@Test
	public void movesFiveSquaresRight() {
		survivor.move(1, 0);
		assertEquals(new Point(12, 10), survivor.getLocation());
	}
	
	@Test
	public void movesFiveSquaresLeft() {
		survivor.move(-1, 0);
		assertEquals(new Point(8, 10), survivor.getLocation());
	}
	
	@Test
	public void canMoveDiagonally() {
		survivor.move(-1, -1);
		assertEquals(new Point(8, 8), survivor.getLocation());
	}
	
	@Test
	public void movesOnlyFiveSquaresUpWithOneMove() {
		survivor.move(0, -20);
		assertEquals(new Point(10, 8), survivor.getLocation());
	}
	
	@Test
	public void movesCorrectlyAfterSeveralSteps() {
		survivor.move(0, 20);
		survivor.move(50, 10);
		survivor.move(80, -20);
		assertEquals(new Point(14, 12), survivor.getLocation());
	}
	
	@Test
	public void printsSurvivorLocationCorrectly() {
		moveNtimes(1, 1, 2);
		assertEquals("Survivor location: 14,14", survivor.toString());
	}
	
	@Test
	public void survivorDoesNotMoveOutsideMap() {
		moveNtimes(-1, -1, 10);
		assertEquals(new Point(0, 0), survivor.getLocation());
	}
	
	@Test
	public void survivorDoesNotMoveOutsideMap2() {
		moveNtimes(1, 1, 15);
		assertEquals(new Point(30, 30), survivor.getLocation());
	}
	
	private void moveNtimes(int x, int y, int n) {
		for (int i = 0; i < n; i++) {
			survivor.move(x, y);
		}
	}
	
}
