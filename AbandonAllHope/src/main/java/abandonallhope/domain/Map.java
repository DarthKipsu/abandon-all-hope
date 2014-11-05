
package abandonallhope.domain;

import java.awt.Point;

public class Map {
	
	private int width;
	private int height;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Map(int size) {
		width = size;
		height = size;
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
