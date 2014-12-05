
package abandonallhope.domain.constructions;

import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

public class WallTest {
	
	private Wall wall;
	
	@Test
	public void createsCorrectDimensionsForHorizonalWall() {
		createWall(WallType.WOODEN, Wall.Orientation.HORIZONAL);
		assertEquals(10, wall.width, 0.1);
		assertEquals(3, wall.height, 0.1);
	}
	
	@Test
	public void returnsCorrectOccupiedAreaForHorizonalWall() {
		createWall(WallType.WOODEN, Wall.Orientation.HORIZONAL);
		assertEquals(new Rectangle2D(10, 10, 10, 3), wall.occupiedArea());
	}
	
	@Test
	public void createsCorrectDimensionsForVerticalWall() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(3, wall.width, 0.1);
		assertEquals(10, wall.height, 0.1);
	}
	
	@Test
	public void returnsCorrectOccupiedAreaForVerticalWall() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(new Rectangle2D(10, 10, 3, 10), wall.occupiedArea());
	}
	
	@Test
	public void returnsWidth() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(3, wall.getWidth(), 0.1);
	}
	
	@Test
	public void returnsHeight() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(10, wall.getHeight(), 0.1);
	}
	
	@Test
	public void returnsCorrectCost() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(1, wall.getCost().getWood());
		assertEquals(0, wall.getCost().getMetal());
	}
	
	@Test
	public void returnsCorrectColor() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(Color.BROWN, wall.getColor());
	}
	
	@Test
	public void returnsCorrectColorWhenNotYetAoutToBreak() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		for (int i = 0; i < 300; i++) {
			wall.breakDown();
		}
		assertEquals(Color.BROWN, wall.getColor());
	}
	
	@Test
	public void getWallType() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(WallType.WOODEN, wall.getType());
	}
	
	@Test
	public void returnsCorrectColorIfAboutToBreak() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		for (int i = 0; i < 301; i++) {
			wall.breakDown();
		}
		assertEquals(Color.RED, wall.getColor());
	}
	
	@Test
	public void returnsCorrectLocation() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(new Point(10, 10), wall.getLocation());
	}
	
	@Test
	public void changesLocationCorrectly() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		wall.setLocation(new Point(5, 5));
		assertEquals(new Point(5, 5), wall.getLocation());
	}
	
	@Test
	public void canChangeOrientation() {
		createWall(WallType.WOODEN, Wall.Orientation.HORIZONAL);
		wall.changeOrientation();
		assertEquals(new Rectangle2D(10, 10, 3, 10), wall.occupiedArea());
	}
	
	@Test
	public void canChangeOrientation2() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		wall.changeOrientation();
		assertEquals(new Rectangle2D(10, 10, 10, 3), wall.occupiedArea());
	}
	
	@Test
	public void hitPointsAtMaximumInTheBeginning() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertEquals(500, wall.hitPoints);
	}
	
	@Test
	public void wallDoesNotBreakDownWithFullHitPoints() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		assertFalse(wall.breakDown());
	}
	
	@Test
	public void doesNotBreakBeforeHitEnoughTimes() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		callBreakDownSeveralTimes(499);
		assertFalse(wall.breakDown());
	}
	
	@Test
	public void breaksDownWhenHitEnoughTimes() {
		createWall(WallType.WOODEN, Wall.Orientation.VERTICAL);
		callBreakDownSeveralTimes(500);
		assertTrue(wall.breakDown());
	}
	
	private void createWall(WallType wallType, Wall.Orientation o) {
		wall = new Wall(wallType, o, new Point(10, 10));
	}
	
	private void callBreakDownSeveralTimes(int times) {
		for (int i = 0; i < times; i++) {
			wall.breakDown();
		}
	}
	
}
