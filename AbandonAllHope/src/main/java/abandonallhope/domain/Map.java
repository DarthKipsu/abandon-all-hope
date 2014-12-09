package abandonallhope.domain;

import abandonallhope.domain.constructions.Trap;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.logic.Game;
import java.util.List;

/**
 * Game map object
 *
 * @author kipsu
 */
public class Map {

	private double width;
	private double height;
	private Game game;

	/**
	 * Creates a new game map
	 *
	 * @param width map width
	 * @param height map height
	 * @Param survivors list of survivors
	 */
	public Map(int width, int height, Game game) {
		this.width = width;
		this.height = height;
		this.game = game;
	}

	/**
	 * Creates a new game map
	 *
	 * @param size map side length
	 * @Param survivors list of survivors
	 */
	public Map(int size, Game game) {
		this(size, size, game);
	}

	/**
	 * Returns list of survivors from game.
	 *
	 * @return
	 */
	public List<Survivor> getSurvivors() {
		return game.getSurvivors();
	}

	/**
	 * Returns true if the given point is a valid move inside the game map
	 *
	 * @param point point that needs to be validated
	 */
	public boolean isValidMove(Point point) {
		return point.x >= 0 && point.x <= width &&
				point.y >= 0 && point.y <= height;
	}

	/**
	 * Returns true if the given point with the displacements added is a valid
	 * move inside the game map
	 *
	 * @param point point that needs to be validated
	 * @param dx displacement to x direction
	 * @param dy displacement to y direction
	 */
	public boolean isValidMove(Point point, double dx, double dy) {
		return point.x + dx >= 0 && point.x + dx <= width &&
				point.y + dy >= 0 && point.y + dy <= height;
	}

	/**
	 * Returns true if given coordinates contains a wall in game.
	 *
	 * @param x
	 * @param y
	 * @return true if location contains a wall
	 */
	public boolean hasObstacle(double x, double y) {
		for (Wall wall : game.getWalls()) {
			if (wall.occupiedArea().contains(x, y)) {
				removeWallIfItBreaks(wall);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a zombie location contains a trap that has capacity left.
	 *
	 * @param point Location of a zombie
	 * @return true if zombie is trapped
	 */
	public boolean isTrapped(Point point) {
		for (Trap trap : game.getTraps()) {
			if (trap.occupiedArea().contains(point.x, point.y) && trap.hasCapacityLeft()) {
				trap.addZombie();
				return true;
			}
		}
		return false;
	}

	private void removeWallIfItBreaks(Wall wall) {
		if (wall.breakDown()) {
			game.getWalls().remove(wall);
		}
	}

	@Override
	public String toString() {
		return width + " x " + height;
	}
}
