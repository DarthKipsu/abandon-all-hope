
package abandonallhope.domain;

import javafx.geometry.Rectangle2D;

/**
 * Moving object is a superclass for moving objects and contains methods to move
 * objects around.
 * @author kipsu
 */
public abstract class MovingObject {
	
	protected Point location;
	protected Map map;
	protected double speed;
	protected double width;

	public MovingObject(Point startingLocation, Map map, double width) {
		location = startingLocation;
		this.map = map;
		this.width = width;
	}
	
	public Point getLocation() {
		return location;
	}

	public double getSpeed() {
		return speed;
	}
	
	/**
	 * Move to direction x,y
	 * The length of the movement is based on objects speed
	 * @param x direction x
	 * @param y direction y
	 */
	public void move(double x, double y) {
		double dx = normalize(x);
		double dy = normalize(y);
		if (map.isValidMove(location, dx, dy)) {
			location.translate(dx, dy);
		}
	}
	
	public abstract void move();
	
	/**
	 * Get the area this object is occupying at the moment
	 * @return occupied area
	 */
	public Rectangle2D occupiedArea() {
		return new Rectangle2D(location.x - width / 2, location.y - width / 2, width, width);
	}

	protected double normalize(double direction) {
		if (direction == 0) {
			return 0;
		} else if (direction > 0) {
			return speed;
		} else {
			return -1 * speed;
		}
	}

	@Override
	public String toString() {
		return String.format("%s location: %.1f,%.1f", getClass().getName().substring(22), location.x, location.y);
	}
}
