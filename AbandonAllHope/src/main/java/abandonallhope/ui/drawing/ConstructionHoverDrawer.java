
package abandonallhope.ui.drawing;

import abandonallhope.logic.Game;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * class to draw construction hover shadows while hovering over game field during
 * build mode.
 * @author kipsu
 */
public class ConstructionHoverDrawer {
	
	private Game game;
	private GraphicsContext gc;
	private int upperLeftX;
	private int upperLeftY;
	private int width;
	private int height;

	/**
	 * Creates a new construction hover drawing object, used to draw shadows of
	 * constructions in build mode.
	 * @param game game object containing game content
	 * @param gc graphics context to draw the object with
	 */
	public ConstructionHoverDrawer(Game game, GraphicsContext gc) {
		this.game = game;
		this.gc = gc;
		upperLeftX = 0;
		upperLeftY = 0;
	}

	public void setConstructionDimensions(Rectangle2D wallDimension) {
			width = (int)wallDimension.getWidth();
			height = (int)wallDimension.getHeight();
	}
	
	/**
	 * Updates the coordinates where the shadow wall is drawn to.
	 * @param x
	 * @param y 
	 */
	public void updateUpperLeftCornerCoordinates(int x, int y) {
		upperLeftX = x;
		upperLeftY = y;
	}
	
	/**
	 * Draws the construction shadows of the object player is hovering over game field.
	 */
	public void drawConstructionShadows() {
		setGraphicsContextAttributes(Color.GRAY, Color.LIGHTGREEN, 1);
		gc.strokeRect(upperLeftX, upperLeftY, width, height);
	}

	private void setGraphicsContextAttributes(Color fill, Color stroke, int lineWidth) {
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);
	}
}
