
package abandonallhope.events.mouse;

import abandonallhope.events.action.WallEvent;
import abandonallhope.ui.ConstructionHoverDrawer;
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
	 * @param type Type of the wall
	 * @param constrHoverDrawer drawer to draw the shadow of the wall being built.
	 */
	public WallHoverEvent(WallEvent.WallType type, ConstructionHoverDrawer constrHoverDrawer) {
		this.constrHoverDrawer = constrHoverDrawer;
		constrHoverDrawer.setConstructionDimensions(type);
	}

	@Override
	public void handle(MouseEvent t) {
		constrHoverDrawer.updateUpperLeftCornerCoordinates((int)t.getSceneX(), (int)t.getSceneY());
	}
	
}
