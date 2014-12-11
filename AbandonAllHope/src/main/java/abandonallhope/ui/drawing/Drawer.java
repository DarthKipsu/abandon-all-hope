
package abandonallhope.ui.drawing;

import abandonallhope.domain.DrawableObject;
import abandonallhope.logic.Game;
import abandonallhope.logic.Items;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Superclass for drawer classes
 * @author kipsu
 */
public abstract class Drawer {
	
	protected Items items;
	protected GraphicsContext gc;

	/**
	 * Creates a new drawer object
	 * @param items game containing information about what to draw
	 * @param gc Graphics context to add the drawings into
	 */
	public Drawer(Items items, GraphicsContext gc) {
		this.items = items;
		this.gc = gc;
	}
	
	protected void setGCcolor(Color color) {
		gc.setFill(color);
	}
	
	protected void draw(DrawableObject object) {
		Rectangle2D location = object.occupiedArea();
		gc.fillRect(location.getMinX(), 
				location.getMinY(), 
				location.getWidth(), 
				location.getHeight());
	}
	
}
