
package abandonallhope.domain.weapons;

import abandonallhope.domain.DrawableObject;
import abandonallhope.domain.Map;
import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import javafx.scene.paint.Color;

/**
 * Bullets are used to represent the location of a bullet moving towards a zombie
 * @author kipsu
 */
public class Bullet extends MovingObject implements DrawableObject {
	
	protected double x;
	protected double y;
	private int distanceLeft;

	public Bullet(Point startingLocation, Map map, Point destination, int distance) {
		super(startingLocation, map, 2, Color.BLACK);
		this.speed = 1;
		distanceLeft = distance;
		setXandY(destination);
	}

	private void setXandY(Point destination) {
		x = destination.x - location.x;
		y = destination.y - location.y;
		double distanceTogether = Math.abs(x) + Math.abs(y);
		x = x / distanceTogether;
		y = y / distanceTogether;
	}
	
	/**
	 * Returns true if bullet has moved it's maximum distance.
	 * Bullet should be removed from the game once the distance has been reached.
	 * @return true if bullet has reached max distance
	 */
	public boolean hasReachedMaxDistance() {
		return distanceLeft < 1;
	}

	@Override
	public void move() {
		if (map.isValidMove(location, x, y)) {
			location.translate(x, y);
			distanceLeft--;
		}
	}
	
}