
package abandonallhope.domain;

import abandonallhope.domain.constructions.Wall;
import abandonallhope.domain.constructions.WallType;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ZombieTest {
	
	private Zombie zombie;
	private double speed;
	private Map map;
	private List<Survivor> survivors;
	private List<Wall> walls;
	
	@Before
	public void setUp() {
		survivors = new ArrayList<>();
		walls = new ArrayList<>();
		map = new Map(30, survivors, walls);
		zombie = new Zombie(new Point(10, 10), map);
		speed = zombie.getSpeed();
		survivors.add(new Survivor(new Point(20,20), map));
	}
	
	@Test
	public void occupies3x3Space() {
		assertEquals(new Rectangle2D(8.5,8.5,3,3), zombie.occupiedArea());
	}
	
	@Test
	public void movesTowardsNearestSurvivor1() {
		zombie.move();
		assertEquals(new Point(10 + speed, 10 + speed), zombie.getLocation());
	}
	
	@Test
	public void movesTowardsNearestSurvivor2() {
		survivors.add(new Survivor(new Point(8, 8), map));
		zombie.move();
		assertEquals(new Point(10 - speed, 10 - speed), zombie.getLocation());
	}
	
	@Test
	public void movesTowardsNearestSurvivor3() {
		survivors.add(new Survivor(new Point(12, 8), map));
		survivors.add(new Survivor(new Point(6, 8), map));
		zombie.move();
		assertEquals(new Point(10 + speed, 10 - speed), zombie.getLocation());
	}
	
	@Test
	public void doesNotMoveIfOnTopOfSurvivor() {
		survivors.add(new Survivor(new Point(10, 10), map));
		zombie.move();
		assertEquals(new Point(10, 10), zombie.getLocation());
	}
	
	@Test
	public void doesNotMoveIfNoSurvivorsLeft() {
		survivors.clear();
		zombie.move();
		assertEquals(new Point(10, 10), zombie.getLocation());
	}
	
	@Test
	public void movesSpeedSquaresUp() {
		zombie.move(0, -1);
		assertEquals(new Point(10, 10 - speed), zombie.getLocation());
	}
	
	@Test
	public void movesFiveSquaresDown() {
		zombie.move(0, 1);
		assertEquals(new Point(10, 10 + speed), zombie.getLocation());
	}
	
	@Test
	public void movesSpeedSquaresRight() {
		zombie.move(1, 0);
		assertEquals(new Point(10 + speed, 10), zombie.getLocation());
	}
	
	@Test
	public void movesSpeedSquaresLeft() {
		zombie.move(-1, 0);
		assertEquals(new Point(10 - speed, 10), zombie.getLocation());
	}
	
	@Test
	public void canMoveDiagonally() {
		zombie.move(-1, -1);
		assertEquals(new Point(10 - speed,  10 - speed), zombie.getLocation());
	}
	
	@Test
	public void movesOnlySpeedSquaresUpWithOneMove() {
		zombie.move(0, -20);
		assertEquals(new Point(10, 10 - speed), zombie.getLocation());
	}
	
	@Test
	public void movesCorrectlyAfterSeveralSteps() {
		zombie.move(0, 20);
		zombie.move(50, 10);
		zombie.move(80, -20);
		assertEquals(10 + speed * 2, zombie.getLocation().x, 0.2);
		assertEquals(10 + speed, zombie.getLocation().y, 0.2);
	}
	
	@Test
	public void printsZombieLocationCorrectly() {
		moveNtimes(1, 1, 2);
		assertEquals("Zombie location: 10," + (int)(speed * 20) + ",10," + (int)(speed * 20), zombie.toString());
	}
	
	@Test
	public void zombieDoesNotMoveOutsideMap() {
		moveNtimes(-1, -1, 35);
		assertEquals(0, zombie.getLocation().x, 0.2);
		assertEquals(0, zombie.getLocation().y, 0.2);
	}
	
	@Test
	public void zombieDoesNotMoveOutsideMap2() {
		moveNtimes(1, 1, 75);
		assertEquals(30, zombie.getLocation().x, 0.2);
		assertEquals(30, zombie.getLocation().y, 0.2);
	}
	
	@Test
	public void zombieDoesNotPassAWall() {
		walls.add(new Wall(WallType.WOODEN, Wall.Orientation.HORIZONAL, new Point(5, 10.2)));
		zombie.move();
		assertFalse(new Point(10 + speed, 10 + speed).equals(zombie.location));
	}
	
	@Test
	public void zombieTakesRandomStepIfFacedWithWall() {
		walls.add(new Wall(WallType.WOODEN, Wall.Orientation.HORIZONAL, new Point(5, 10.2)));
		zombie.move();
		assertTrue(new Point(10 - speed, 10.0).equals(zombie.getLocation()) ||
				   new Point(10.0, 10 - speed).equals(zombie.getLocation()));
	}
	
	private void moveNtimes(int x, int y, int n) {
		for (int i = 0; i < n; i++) {
			zombie.move(x, y);
		}
	}
	
}
