
package abandonallhope.logic;

import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class CollisionTest {
	
	@Test
	public void distanceBetweenPoints() {
		Point point1 = new Point(10, 10);
		Point point2 = new Point(5, 5);
		assertEquals(10, Collision.distanceBetween(point1, point2), 0.1);
	}
	
	@Test
	public void distanceBetweenPoints2() {
		Point point1 = new Point(5, 5);
		Point point2 = new Point(10, 10);
		assertEquals(10, Collision.distanceBetween(point1, point2), 0.1);
	}
	
	@Test
	public void returnNullIfNoEnemies() {
		assertNull(Collision.nearestPerson(new Survivor(null, null, null, 1), new ArrayList<MovingObject>()));
	}
	
}
