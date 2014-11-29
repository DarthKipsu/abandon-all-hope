
package abandonallhope.domain;

import abandonallhope.domain.constructions.Wall;
import java.util.List;

/**
 * Game map object
 * @author kipsu
 */
public class Map {
	
	private double width;
	private double height;
	private List<Survivor> survivors;
	private List<Wall> walls;

	/**
	 * Creates a new game map
	 * @param width map width
	 * @param height map height
	 * @Param survivors list of survivors
	*/
	public Map(int width, int height, List<Survivor> survivors, List<Wall>walls) {
		this.width = width;
		this.height = height;
		this.survivors = survivors;
		this.walls = walls;
	}

	/**
	 * Creates a new game map
	 * @param size map side length
	 * @Param survivors list of survivors
	*/
	public Map(int size, List<Survivor> survivors, List<Wall>walls) {
		this(size, size, survivors, walls);
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
	
	/**
	 * Returns true if given point with displacements added contains a wall
	 * or a trap in game map.
	 * @param point point that needs to be validated
	 * @param dx displacement to x direction
	 * @param dy displacement to y direction
	 * @return true if location contains a wall
	 */
	public boolean hasObstacle(double x, double y) {
		for (Wall wall : walls) {
			if (wall.occupiedArea().contains(x, y)) {
				removeWallIfItBreaks(wall);
				return true;
			}
		}
		return false;
	}

	private void removeWallIfItBreaks(Wall wall) {
		if (wall.breakDown()) {
			walls.remove(wall);
		}
	}

	@Override
	public String toString() {
		return width + " x " + height;
	}
}
