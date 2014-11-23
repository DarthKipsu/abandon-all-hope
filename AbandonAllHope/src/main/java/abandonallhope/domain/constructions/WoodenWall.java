
package abandonallhope.domain.constructions;

import abandonallhope.domain.Point;
import javafx.scene.paint.Color;

/**
 * Wooden wall object. The least resistant wall in game.
 * @author kipsu
 */
public class WoodenWall extends Wall {

	/**
	 * Creates a new wooden wall object.
	 * @param o Orientation of the wall: horizonal or vertical
	 * @param location upper left corner of the wall
	 */
	public WoodenWall(Orientation o, Point location) {
		super(o, 10, 2, 500, location, Color.BROWN);
	}
	
}
