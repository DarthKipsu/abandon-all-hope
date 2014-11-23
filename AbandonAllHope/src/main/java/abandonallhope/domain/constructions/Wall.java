package abandonallhope.domain.constructions;

import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;

/**
 * Superclass for walls.
 * @author kipsu
 */
public abstract class Wall {

	/**
	 * Orientation of the wall.
	 */
	public enum Orientation {
		HORIZONAL,
		VERTICAL
	};

	protected Point upperLeftCorner;
	protected double width;
	protected double height;
	protected int hitPoints;

	public Wall(Orientation o, double width, double height, int maxHP, Point location) {
		if (o == Orientation.HORIZONAL) {
			this.width = width;
			this.height = height;
		} else {
			this.width = height;
			this.height = width;
		}
		hitPoints = maxHP;
		upperLeftCorner = location;
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
		return new Rectangle2D(upperLeftCorner.x, upperLeftCorner.y, width, height);
	}
}
