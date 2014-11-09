
package abandonallhope.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
	
	private Point point;
	
	@Before
	public void setUp() {
		point = new Point(10, 10);
	}

	@Test
	public void translateChangesPointLocation() {
		point.translate(1, 1);
		assertEquals(new Point(11, 11), point);
	}

	@Test
	public void translateChangesPointLocation2() {
		point.translate(-1, -1);
		assertEquals(new Point(9, 9), point);
				
	}
	
	@Test
	public void canDistinctPointsFromAnother() {
		assertFalse(point.equals(new Point(8, 8)));
	}
	
	@Test
	public void canDistinctOtherObjectsFromPoints() {
		assertFalse(point.equals(new String()));
	}
	
	@Test
	public void toStringPrintsCorrectly() {
		assertEquals("(10.0, 10.0)", point.toString());
	}
	
}
