
package abandonallhope.domain;

import abandonallhope.domain.constructions.Trap;
import abandonallhope.domain.constructions.TrapType;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.domain.constructions.WallType;
import abandonallhope.logic.Game;
import abandonallhope.logic.Items;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapTest {
	
	private Items items;
	private Map map;
	private List<Survivor> survivors;
	
	@Before
	public void setUp() {
		Game game = new Game(500);
		items = game.getItems();
		survivors = items.getSurvivors();
		map = items.getMap();
		survivors.add(new Survivor(new Point(50, 20), map, "name", 1));
		survivors.add(new Survivor(new Point(20, 50), map, "name", 2));
	}
	
	@Test
	public void movementToLeftUpperCornerIsValid() {
		assertTrue(map.isValidMove(new Point(0, 0)));
	}
	
	@Test
	public void movementToRightUpperCornerIsValid() {
		assertTrue(map.isValidMove(new Point(500, 0)));
	}
	
	@Test
	public void movementToLeftLowerCornerIsValid() {
		assertTrue(map.isValidMove(new Point(0, 500)));
	}
	
	@Test
	public void movementToRightLowerCornerIsValid() {
		assertTrue(map.isValidMove(new Point(500, 500)));
	}
	
	@Test
	public void movementToNegativeCoordinatesIsNotValid() {
		assertFalse(map.isValidMove(new Point(-1, -1)));
	}
	
	@Test
	public void movementToOutsideCoordinatesIsNotValid() {
		assertFalse(map.isValidMove(new Point(501, 501)));
	}
	
	@Test
	public void movementDownFromLowerLeftCornerIsNotValid() {
		assertFalse(map.isValidMove(new Point(0, 500), 0, 1));
	}
	
	@Test
	public void movementRightFromLowerRirghtCornerIsNotValid() {
		assertFalse(map.isValidMove(new Point(500, 500), 1, 0));
	}
	
	@Test
	public void movementUpFromUpperRirghtCornerIsNotValid() {
		assertFalse(map.isValidMove(new Point(500, 0), 0, -1));
	}
	
	@Test
	public void movementLeftFromUpperLeftCornerIsNotValid() {
		assertFalse(map.isValidMove(new Point(0, 0), -1, 0));
	}
	
	@Test
	public void getSurvivors() {
		assertEquals(2, map.getSurvivors().size());
	}
	
	@Test
	public void toStringReturnsCorrectString() {
		assertEquals("500.0 x 500.0", map.toString());
	}
	
	@Test
	public void removeWallsWhenTheyBreakUp() {
		Wall wall = new Wall(WallType.WOODEN, Wall.Orientation.VERTICAL, new Point(10, 10));
		items.add(wall);
		hitAWall(500);
		assertFalse(items.getWalls().isEmpty());
		map.hasObstacle(10, 10);
		assertTrue(items.getWalls().isEmpty());
	}
	
	@Test
	public void givesInfomationAboutTraps() {
		items.add(new Trap(new Point(10, 10), TrapType.PIT));
		assertTrue(map.hasTrap(10, 10));
	}
	
	@Test
	public void doesNotGiveWrongInfomationAboutTraps() {
		items.add(new Trap(new Point(10, 10), TrapType.PIT));
		assertFalse(map.hasTrap(9, 9));
	}

	private void hitAWall(int times) {
		for (int i = 0; i < times; i++) {
			map.hasObstacle(10, 10);
		}
	}
	
}
