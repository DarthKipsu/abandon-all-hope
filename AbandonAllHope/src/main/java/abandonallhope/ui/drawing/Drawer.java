
package abandonallhope.ui.drawing;

import abandonallhope.domain.DrawableObject;
import abandonallhope.logic.Game;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Superclass for drawer classes
 * @author kipsu
 */
public abstract class Drawer {
	
	protected Game game;
	protected GraphicsContext gc;

	public Drawer(Game game, GraphicsContext gc) {
		this.game = game;
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
