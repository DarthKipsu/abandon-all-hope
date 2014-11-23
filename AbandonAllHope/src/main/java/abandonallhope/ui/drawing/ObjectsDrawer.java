package abandonallhope.ui.drawing;

import abandonallhope.domain.DrawableObject;
import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.logic.Game;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class be used to draw survivors, zombies, bullets and other drawable objects
 * @author kipsu
 */
public class ObjectsDrawer extends Drawer {
	
	/**
	 * Creates a new drawable objects drawer class
	 *
	 * @param game game containing the objects who will be drawn
	 * @param gc graphics context where the objects will be displayed
	 */
	public ObjectsDrawer(Game game, GraphicsContext gc) {
		super(game, gc);
	}

	/**
	 * Draw each survivor in game
	 */
	public void drawSurvivors() {
		setGraphicsContextAttributes(Color.RED, 2);
		for (Survivor survivor : game.getSurvivors()) {
			setGCcolor(survivor.getColor());
			draw(survivor);
			if (survivor.isSelected()) {
				drawSelectionMarkerAroundSurvivor(survivor.getLocation());
			}
		}
	}

	/**
	 * Draw each drawable object in the list.
	 * @param objects List of drawableObjects.
	 */
	public void drawObjects(List<? extends DrawableObject> objects) {
		for (DrawableObject obj : objects) {
			setGCcolor(obj.getColor());
			draw(obj);
		}
	}

	private void setGraphicsContextAttributes(Color stroke, int lineWidth) {
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);
	}

	private void drawSelectionMarkerAroundSurvivor(Point location) {
		gc.strokeRect(location.x - 3.5, location.y - 3.5, 7.0, 7.0);
	}
	
}
