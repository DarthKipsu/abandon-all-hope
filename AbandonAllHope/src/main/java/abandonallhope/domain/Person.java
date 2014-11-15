
package abandonallhope.domain;

import javafx.geometry.Rectangle2D;

public abstract class Person {
	
	protected Point location;
	protected Map map;
	protected double speed;

	public Person(Point startingLocation, Map map) {
		location = startingLocation;
		this.map = map;
	}
	
	public Point getLocation() {
		return location;
	}

	public double getSpeed() {
		return speed;
	}
	
	public void move(double x, double y) {
		double dx = normalize(x);
		double dy = normalize(y);
		if (map.isValidMove(location, dx, dy)) {
			location.translate(dx, dy);
		}
	}
	
	public abstract void move();
	
	public Rectangle2D occupiedArea() {
		return new Rectangle2D(location.x - 1, location.y - 1, 2, 2);
	}

	protected double normalize(double direction) {
		if (direction == 0) return 0;
		else if (direction > 0) return speed;
		else return -1 * speed;
	}

	@Override
	public String toString() {
		return String.format("%s location: %.1f,%.1f", getClass().getName().substring(22), location.x, location.y);
	}
}
