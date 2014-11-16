
package abandonallhope.domain;

import java.util.List;

/**
 * Game map object
 * @author kipsu
 */
public class Map {
	
	private double width;
	private double height;
	private List<Survivor> survivors;

	/**
	 * Creates a new game map
	 * @param width map width
	 * @param height map height
	 * @Param survivors list of survivors
	*/
	public Map(int width, int height, List<Survivor> survivors) {
		this.width = width;
		this.height = height;
		this.survivors = survivors;
	}

	/**
	 * Creates a new game map
	 * @param size map side length
	 * @Param survivors list of survivors
	*/
	public Map(int size, List<Survivor> survivors) {
		this(size, size, survivors);
	}

	public List<Survivor> getSurvivors() {
		return survivors;
	}
	
	/**
	 * Returns true if the given point is a valid move inside the game map
	 * @param point point that needs to be validated
	*/
	public boolean isValidMove(Point point) {
		return point.x >= 0 && point.x <= width && 
				point.y >= 0 && point.y <= height;
	}
	
	/**
	 * Returns true if the given point with the displacements added is a valid 
	 * move inside the game map
	 * @param point point that needs to be validated
	 * @param dx displacement to x direction
	 * @param dy displacement to y direction
	*/
	public boolean isValidMove(Point point, double dx, double dy) {
		return point.x + dx >= 0 && point.x + dx <= width && 
				point.y + dy >= 0 && point.y + dy <= height;
	}

	@Override
	public String toString() {
		return width + " x " + height;
	}
}
