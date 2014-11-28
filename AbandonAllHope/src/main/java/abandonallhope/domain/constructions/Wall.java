package abandonallhope.domain.constructions;

import abandonallhope.domain.DrawableObject;
import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

/**
 * Superclass for walls.
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
	protected double width;
	protected double height;
	protected int hitPoints;
	protected Color color;

	public Wall(WallType wallType, Orientation o, Point location) {
		this.width = wallType.getWidth(o);
		this.height = wallType.getHeight(o);
		hitPoints = wallType.getMaxHP();
		this.color = wallType.getColor();
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	@Override
	public Color getColor() {
		return color;
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
		double savedHeight = height;
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
		return hitPoints < 0;
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
