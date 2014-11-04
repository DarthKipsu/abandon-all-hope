
package abandonallhope.domain;

import java.awt.Point;

public abstract class Person {
	
	private Point location;
	protected int speed;

	public Person(Point startingLocation) {
		location = startingLocation;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void move(int x, int y) {
		location.translate(normalize(x), normalize(y));
	}

	private int normalize(int x) {
		if (x == 0) return 0;
		else if (x > 0) return speed;
		else return -1 * speed;
	}
}
