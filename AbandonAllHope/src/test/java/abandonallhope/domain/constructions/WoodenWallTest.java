
package abandonallhope.domain.constructions;

import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

public class WoodenWallTest {
	
	private WoodenWall wall;
	
	@Test
	public void createsCorrectDimensionsForHorizonalWall() {
		createWall(Wall.Orientation.HORIZONAL);
		assertEquals(10, wall.width, 0.1);
		assertEquals(2, wall.height, 0.1);
	}
	
	@Test
	public void returnsCorrectOccupiedAreaForHorizonalWall() {
		createWall(Wall.Orientation.HORIZONAL);
		assertEquals(new Rectangle2D(10, 10, 10, 2), wall.occupiedArea());
	}
	
	@Test
	public void createsCorrectDimensionsForVerticalWall() {
		createWall(Wall.Orientation.VERTICAL);
		assertEquals(2, wall.width, 0.1);
		assertEquals(10, wall.height, 0.1);
	}
	
	@Test
	public void returnsCorrectOccupiedAreaForVerticalWall() {
		createWall(Wall.Orientation.VERTICAL);
		assertEquals(new Rectangle2D(10, 10, 2, 10), wall.occupiedArea());
	}
	
	@Test
	public void returnsWidth() {
		createWall(Wall.Orientation.VERTICAL);
		assertEquals(2, wall.getWidth(), 0.1);
	}
	
	@Test
	public void returnsHeight() {
		createWall(Wall.Orientation.VERTICAL);
		assertEquals(10, wall.getHeight(), 0.1);
	}
	
	@Test
	public void returnsCorrectColor() {
		createWall(Wall.Orientation.VERTICAL);
		assertEquals(Color.BROWN, wall.getColor());
	}
	
	@Test
	public void returnsCorrectLocation() {
		createWall(Wall.Orientation.VERTICAL);
		assertEquals(new Point(10, 10), wall.getLocation());
	}
	
	@Test
	public void changesLocationCorrectly() {
		createWall(Wall.Orientation.VERTICAL);
		wall.setLocation(new Point(5, 5));
		assertEquals(new Point(5, 5), wall.getLocation());
	}
	
	@Test
	public void canChangeOrientation() {
		createWall(Wall.Orientation.HORIZONAL);
		wall.changeOrientation();
		assertEquals(new Rectangle2D(10, 10, 2, 10), wall.occupiedArea());
	}
	
	@Test
	public void canChangeOrientation2() {
		createWall(Wall.Orientation.VERTICAL);
		wall.changeOrientation();
		assertEquals(new Rectangle2D(10, 10, 10, 2), wall.occupiedArea());
	}
	
	@Test
	public void hitPointsAtMaximumInTheBeginning() {
		createWall(Wall.Orientation.VERTICAL);
		assertEquals(500, wall.hitPoints);
	}
	
	@Test
	public void wallDoesNotBreakDownWithFullHitPoints() {
		createWall(Wall.Orientation.VERTICAL);
		assertFalse(wall.breakDown());
	}
	
	@Test
	public void doesNotBreakBeforeHitEnoughTimes() {
		createWall(Wall.Orientation.VERTICAL);
		callBreakDownSeveralTimes(499);
		assertFalse(wall.breakDown());
	}
	
	@Test
	public void breaksDownWhenHitEnoughTimes() {
		createWall(Wall.Orientation.VERTICAL);
		callBreakDownSeveralTimes(500);
		assertTrue(wall.breakDown());
	}
	
	private void createWall(Wall.Orientation o) {
		wall = new WoodenWall(o, new Point(10, 10));
	}
	
	private void callBreakDownSeveralTimes(int times) {
		for (int i = 0; i < times; i++) {
			wall.breakDown();
		}
	}
	
}
