package abandonallhope.events.mouse;

import abandonallhope.domain.constructions.Trap;
import abandonallhope.ui.drawing.ConstructionHoverDrawer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Event for updating hover coordinates for construction hover drawer
 *
 * @author kipsu
 */
public class TrapHoverEvent implements EventHandler<MouseEvent> {

	private ConstructionHoverDrawer constrHoverDrawer;

	/**
	 * Creates a new event to update hover coordinates
	 *
	 * @param trap trap to be built
	 * @param constrHoverDrawer drawer to draw the trap shadows
	 */
	public TrapHoverEvent(Trap trap, ConstructionHoverDrawer constrHoverDrawer) {
		this.constrHoverDrawer = constrHoverDrawer;
		constrHoverDrawer.setConstructionDimensions(trap.occupiedArea());
	}

	@Override
	public void handle(MouseEvent t) {
		constrHoverDrawer.updateUpperLeftCornerCoordinates((int) t.getX(), (int) t.getY());
	}

}
