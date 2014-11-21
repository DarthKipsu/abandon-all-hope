
package abandonallhope.domain.weapons;

import abandonallhope.domain.Map;
import abandonallhope.domain.Point;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BulletTest {
	
	private Bullet bullet;
	
	@Test
	public void setsXandYcorrectly1() {
		createBullet(10, 10, 20, 10, 20);
		assertEquals(1, bullet.x, 0.1);
		assertEquals(0, bullet.y, 0.1);
	}
	
	@Test
	public void setsXandYcorrectly2() {
		createBullet(10, 10, 10, 20, 20);
		assertEquals(0, bullet.x, 0.1);
		assertEquals(1, bullet.y, 0.1);
	}
	
	@Test
	public void setsXandYcorrectly3() {
		createBullet(10, 10, 0, 10, 20);
		assertEquals(-1, bullet.x, 0.1);
		assertEquals(0, bullet.y, 0.1);
	}
	
	@Test
	public void setsXandYcorrectly4() {
		createBullet(10, 10, 10, 0, 20);
		assertEquals(0, bullet.x, 0.1);
		assertEquals(-1, bullet.y, 0.1);
	}
	
	@Test
	public void setsXandYcorrectlyDiagonally1() {
		createBullet(10, 10, 20, 20, 20);
		assertEquals(0.5, bullet.x, 0.1);
		assertEquals(0.5, bullet.y, 0.1);
	}
	
	@Test
	public void setsXandYcorrectlyDiagonally2() {
		createBullet(10, 10, 20, 0, 20);
		assertEquals(0.5, bullet.x, 0.1);
		assertEquals(-0.5, bullet.y, 0.1);
	}
	
	@Test
	public void hasReachedMaxDistanceAfterEnoughTimePass() {
		createBullet(10, 10, 20, 20, 5);
		moveBullet(5);
		assertTrue(bullet.hasReachedMaxDistance());
	}
	
	@Test
	public void hasNotReachedMaxDistanceAfterEnoughHasNotTimePassed() {
		createBullet(10, 10, 20, 20, 5);
		moveBullet(4);
		assertFalse(bullet.hasReachedMaxDistance());
	}

	private void moveBullet(int distance) {
		for (int i = 0; i < distance; i++) {
			bullet.move();
		}
	}
	
	public void createBullet(int startX, int startY, int destX, int destY, int distance) {
		bullet = new Bullet(
				new Point(startX, startY), 
				new Map(500, new ArrayList()), 
				new Point(destX, destY), 
				distance
		);
	}
	
}
