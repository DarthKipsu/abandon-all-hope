
package abandonallhope.events.mouse;

import abandonallhope.domain.constructions.Wall;
import abandonallhope.ui.drawing.ConstructionHoverDrawer;
import javafx.event.EventHandler; 
import javafx.scene.input.MouseEvent;

/**
 * Event detecting if there's mouse hovering above the target.
 * @author kipsu
 */
public class WallHoverEvent implements EventHandler<MouseEvent> {
	
	private ConstructionHoverDrawer constrHoverDrawer;

	/**
	 * Creates event to detect where user mouse is over the game field and pass
	 * that information to the construction hover drawer.
	 * @param wall Type of the wall
	 * @param constrHoverDrawer drawer to draw the shadow of the wall being built.
	 */
	public WallHoverEvent(Wall wall, ConstructionHoverDrawer constrHoverDrawer) {
		this.constrHoverDrawer = constrHoverDrawer;
		constrHoverDrawer.setConstructionDimensions(wall.occupiedArea());
	}

	@Override
	public void handle(MouseEvent t) {
		constrHoverDrawer.updateUpperLeftCornerCoordinates((int)t.getX(), (int)t.getY());
	}
	
}
