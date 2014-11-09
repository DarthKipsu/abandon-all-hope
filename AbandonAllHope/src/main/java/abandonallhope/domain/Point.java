
package abandonallhope.domain;

public class Point {
	
	public double x;
	public double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
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
