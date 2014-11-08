
package abandonallhope.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ZombieTest {
	
	private Zombie zombie;
	private Map map;
	private List<Survivor> survivors;
	
	@Before
	public void setUp() {
		survivors = new ArrayList<>();
		map = new Map(30, survivors);
		zombie = new Zombie(new Point(10, 10), map);
		survivors.add(new Survivor(new Point(20,20), map));
	}
	
	@Test
	public void movesTowardsNearestSurvivor1() {
		zombie.move();
		assertEquals(new Point(11, 11), zombie.getLocation());
	}
	
	@Test
	public void movesTowardsNearestSurvivor2() {
		survivors.add(new Survivor(new Point(8, 8), map));
		zombie.move();
		assertEquals(new Point(9, 9), zombie.getLocation());
	}
	
	@Test
	public void movesTowardsNearestSurvivor3() {
		survivors.add(new Survivor(new Point(12, 8), map));
		survivors.add(new Survivor(new Point(6, 8), map));
		zombie.move();
		assertEquals(new Point(11, 9), zombie.getLocation());
	}
	
	@Test
	public void doesNotMoveIfOnTopOfSurvivor() {
		survivors.add(new Survivor(new Point(10, 10), map));
		zombie.move();
		assertEquals(new Point(10, 10), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresUp() {
		zombie.move(0, -1);
		assertEquals(new Point(10, 9), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresDown() {
		zombie.move(0, 1);
		assertEquals(new Point(10, 11), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresRight() {
		zombie.move(1, 0);
		assertEquals(new Point(11, 10), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresLeft() {
		zombie.move(-1, 0);
		assertEquals(new Point(9, 10), zombie.getLocation());
	}
	
	@Test
	public void canMoveDiagonally() {
		zombie.move(-1, -1);
		assertEquals(new Point(9, 9), zombie.getLocation());
	}
	
	@Test
	public void movesOnlyFiveSquaresUpWithOneMove() {
		zombie.move(0, -20);
		assertEquals(new Point(10, 9), zombie.getLocation());
	}
	
	@Test
	public void movesCorrectlyAfterSeveralSteps() {
		zombie.move(0, 20);
		zombie.move(50, 10);
		zombie.move(80, -20);
		assertEquals(new Point(12, 11), zombie.getLocation());
	}
	
	@Test
	public void printsZombieLocationCorrectly() {
		moveNtimes(1, 1, 2);
		assertEquals("Zombie location: 12,12", zombie.toString());
	}
	
	@Test
	public void zombieDoesNotMoveOutsideMap() {
		moveNtimes(-1, -1, 20);
		assertEquals(new Point(0, 0), zombie.getLocation());
	}
	
	@Test
	public void zombieDoesNotMoveOutsideMap2() {
		moveNtimes(1, 1, 25);
		assertEquals(new Point(30, 30), zombie.getLocation());
	}
	
	private void moveNtimes(int x, int y, int n) {
		for (int i = 0; i < n; i++) {
			zombie.move(x, y);
		}
	}
	
}
