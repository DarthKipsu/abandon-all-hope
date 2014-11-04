
package abandonallhope.domain;

public class Map {
	
	private int width;
	private int height;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean isValidMove(int x, int y) {
		return x >= 0 && x <= width && y >= 0 && y <= height;
	}
}
