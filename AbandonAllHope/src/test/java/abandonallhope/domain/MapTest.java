
package abandonallhope.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapTest {
	
	public Map map;
	
	@Before
	public void setUp() {
		map = new Map(500, 500);
	}
	
	@Test
	public void movementToLeftUpperCornerIsValid() {
		assertTrue(map.isValidMove(0, 500));
	}
	
	@Test
	public void movementToRightUpperCornerIsValid() {
		assertTrue(map.isValidMove(500, 500));
	}
	
	@Test
	public void movementToLeftLowerCornerIsValid() {
		assertTrue(map.isValidMove(0, 0));
	}
	
	@Test
	public void movementToRightLowerCornerIsValid() {
		assertTrue(map.isValidMove(500, 0));
	}
	
	@Test
	public void movementToNegativeCoordinatesIsNotValid() {
		assertFalse(map.isValidMove(-1, -1));
	}
	
	@Test
	public void movementToOutsideCoordinatesIsNotValid() {
		assertFalse(map.isValidMove(501, 501));
	}
	
}
