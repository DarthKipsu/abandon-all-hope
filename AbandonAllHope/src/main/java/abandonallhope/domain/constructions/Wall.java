package abandonallhope.domain.constructions;

import abandonallhope.domain.DrawableObject;
import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

/**
 * Superclass for walls.
 * @author kipsu
 */
public abstract class Wall implements DrawableObject {

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

	public Wall(Orientation o, double width, double height, int maxHP, 
			Point location, Color color) {
		if (o == Orientation.HORIZONAL) {
			this.width = width;
			this.height = height;
		} else {
			this.width = height;
			this.height = width;
		}
		hitPoints = maxHP;
		this.location = location;
		this.color = color;
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

	public Color getColor() {
		return color;
	}

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
	public Rectangle2D occupiedArea() {
		return new Rectangle2D(location.x, location.y, width, height);
	}
}
