
package abandonallhope.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapTest {
	
	private Map map;
	private List<Person> survivors;
	
	@Before
	public void setUp() {
		survivors = new ArrayList<>();
		map = new Map(500, 500, survivors);
		survivors.add(new Survivor(new Point(50, 20), map));
		survivors.add(new Survivor(new Point(20, 50), map));
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
	
}
