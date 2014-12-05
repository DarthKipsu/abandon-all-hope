package abandonallhope.domain.constructions;

import abandonallhope.domain.DrawableObject;
import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

/**
 * Walls are used to hold zombies from closing the survivors.
 * @author kipsu
 */
public class Wall implements DrawableObject {

	/**
	 * Orientation of the wall.
	 */
	public enum Orientation {
		HORIZONAL,
		VERTICAL
	};

	protected Point location;
	protected WallType type;
	protected int width;
	protected int height;
	protected int hitPoints;
	protected Color color;
	protected Cost cost;

	/**
	 * Create a new wall object.
	 * @param wallType Type of the wall
	 * @param o Orientation of the wall
	 * @param location Wall location
	 */
	public Wall(WallType wallType, Orientation o, Point location) {
		this.type = wallType;
		this.width = wallType.getWidth(o);
		this.height = wallType.getHeight(o);
		hitPoints = wallType.getMaxHP();
		this.color = wallType.getColor();
		this.cost = wallType.getCost();
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public Color getColor() {
		return color;
	}

	public WallType getType() {
		return type;
	}

	public Cost getCost() {
		return cost;
	}

	/**
	 * Set wall location
	 * @param location 
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	/**
	 * Saves the orientation of the wall by exchanging width and height.
	 */
	public void changeOrientation() {
		int savedHeight = height;
		height = width;
		width = savedHeight;
	}

	/**
	 * Wall will sustain a certain amount of hits. Colliding with a wall will decrease
	 * hit points, until they reach 0 when wall breaks down.
	 * @return true if wall breaks down.
	 */
	public boolean breakDown() {
		hitPoints--;
		changeWallColorToRedIfHitPointsTooLow();
		return hitPoints < 0;
	}

	private void changeWallColorToRedIfHitPointsTooLow() {
		if (hitPoints < 200) {
			color = Color.RED;
		}
	}
	
	/**
	 * Get the area this object is occupying at the moment
	 * @return occupied area
	 */
	@Override
	public Rectangle2D occupiedArea() {
		return new Rectangle2D(location.x, location.y, width, height);
	}
}
