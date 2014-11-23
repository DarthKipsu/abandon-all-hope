package abandonallhope.ui;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.logic.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This will draw walls in the game.
 *
 * @author kipsu
 */
public class WallDrawer {

	private Game game;
	private GraphicsContext gc;

	/**
	 * Creates a new WallDrawer object, handling drawing walls in game.
	 *
	 * @param game game containing the walls
	 * @param gc Graphics context used to draw the objects
	 */
	public WallDrawer(Game game, GraphicsContext gc) {
		this.game = game;
		this.gc = gc;
	}

	/**
	 * draws any wall objects in game.
	 */
	public void drawWalls() {
		setGraphicsContextAttributes(Color.GRAY, Color.BLACK, 1);
		for (Wall wall : game.getWalls()) {
			drawWall(wall);
		}
	}

	private void setGraphicsContextAttributes(Color fill, Color stroke, int lineWidth) {
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);
	}

	private void drawWall(Wall wall) {
		Point location = wall.getUpperLeftCorner();
		gc.fillRect(location.x, location.y, wall.getWidth(), wall.getHeight());
	}

}
