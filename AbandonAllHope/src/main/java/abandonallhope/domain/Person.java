
package abandonallhope.domain;

import java.awt.Point;

public abstract class Person {
	
	protected Point location;
	protected Map map;
	protected int speed;

	public Person(Point startingLocation, Map map) {
		location = startingLocation;
		this.map = map;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void move(int x, int y) {
		int dx = normalize(x);
		int dy = normalize(y);
		if (map.isValidMove(location, dx, dy)) {
			location.translate(dx, dy);
		}
	}
	
	public void move() {
	}

	protected int normalize(int direction) {
		if (direction == 0) return 0;
		else if (direction > 0) return speed;
		else return -1 * speed;
	}

	@Override
	public String toString() {
		return getClass().getName().substring(22) + " location: " + location.x + "," + location.y;
	}
}
