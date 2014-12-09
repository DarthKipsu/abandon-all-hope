
package abandonallhope.domain;

/**
 * Point object is used to store a persons location on the game map
 * @author kipsu
 */
public class Point {
	
	/**
	 * X coordinate for the location
	 */
	public double x;
	/**
	 * Y coordinate for the location
	 */
	public double y;

	/**
	 * Constructor for a new point object
	 * Point object is used to store a persons location on the game map
	 * @param x starting location x
	 * @param y starting location y
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Add the x and y amounts to the point
	 * @param x movement in x direction
	 * @param y movement in y direction
	 */
	public void translate(double x, double y) {
		this.x += x;
		this.y += y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point compararison = (Point) obj;
			return x == compararison.x && y == compararison.y;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
