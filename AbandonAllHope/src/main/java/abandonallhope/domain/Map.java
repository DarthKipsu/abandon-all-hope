package abandonallhope.domain;

import abandonallhope.domain.constructions.Trap;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.logic.Items;
import java.util.List;

/**
 * Game map object
 *
 * @author kipsu
 */
public class Map {

	private double width;
	private double height;
	private Items items;

	/**
	 * Creates a new game map
	 * @param width width of the map
	 * @param height height of the map
	 * @param items items in game, used to check trap and wall locations
	 */
	public Map(int width, int height, Items items) {
		this.width = width;
		this.height = height;
		this.items = items;
	}

	/**
	 * Creates a new game map
	 * @param size map side length
	 * @param items items in game, used to check trap and wall locations
	 */
	public Map(int size, Items items) {
		this(size, size, items);
	}
	
	public List<Survivor> getSurvivors() {
		return items.getSurvivors();
	}

	/**
	 * Returns true if the given point is a valid move inside the game map
	 * @param point point that needs to be validated
	 * @return true if movement is valid
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
	 * @return true if movement is valid
	 */
	public boolean isValidMove(Point point, double dx, double dy) {
		return point.x + dx >= 0 && point.x + dx <= width &&
				point.y + dy >= 0 && point.y + dy <= height;
	}

	/**
	 * Returns true if given coordinates contains a wall in game.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return true if location contains a wall
	 */
	public boolean hasObstacle(double x, double y) {
		for (Wall wall : items.getWalls()) {
			if (wall.occupiedArea().contains(x, y)) {
				removeWallIfItBreaks(wall);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a zombie location contains a trap that has capacity left.
	 * @param point Location of a zombie
	 * @return true if zombie is trapped
	 */
	public boolean isTrapped(Point point) {
		for (Trap trap : items.getTraps()) {
			if (trap.occupiedArea().contains(point.x, point.y) && trap.hasCapacityLeft()) {
				trap.addZombie();
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a building location contains a trap.
	 * @param x x coordinate to check
	 * @param y y coordinate to check
	 * @return true if other trap is here
	 */
	public boolean hasTrap(double x, double y) {
		for (Trap trap : items.getTraps()) {
			if (trap.occupiedArea().contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	private void removeWallIfItBreaks(Wall wall) {
		if (wall.breakDown()) {
			items.getWalls().remove(wall);
		}
	}

	@Override
	public String toString() {
		return width + " x " + height;
	}
}
