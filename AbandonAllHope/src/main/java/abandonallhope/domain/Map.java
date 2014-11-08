
package abandonallhope.domain;

import java.awt.Point;
import java.util.List;

public class Map {
	
	private int width;
	private int height;
	private List<Person> survivors;

	public Map(int width, int height, List<Person> survivors) {
		this.width = width;
		this.height = height;
		this.survivors = survivors;
	}

	public Map(int size, List<Person> survivors) {
		this(size, size, survivors);
	}

	public List<Person> getSurvivors() {
		return survivors;
	}
	
	public boolean isValidMove(Point point) {
		return point.x >= 0 && point.x <= width && 
				point.y >= 0 && point.y <= height;
	}
	
	public boolean isValidMove(Point point, int dx, int dy) {
		return point.x + dx >= 0 && point.x + dx <= width && 
				point.y + dy >= 0 && point.y + dy <= height;
	}
}
