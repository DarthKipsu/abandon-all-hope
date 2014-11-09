
package abandonallhope.domain;

import java.awt.Point;
import java.util.List;

public class Map {
	
	private int width;
	private int height;
	private List<Survivor> survivors;

	public Map(int width, int height, List<Survivor> survivors) {
		this.width = width;
		this.height = height;
		this.survivors = survivors;
	}

	public Map(int size, List<Survivor> survivors) {
		this(size, size, survivors);
	}

	public List<Survivor> getSurvivors() {
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

	@Override
	public String toString() {
		return width + " x " + height;
	}
}
