
package abandonallhope.events.mouse;

import abandonallhope.domain.constructions.Trap;
import abandonallhope.ui.drawing.ConstructionHoverDrawer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author kipsu
 */
public class TrapHoverEvent implements EventHandler<MouseEvent> {
	
	private ConstructionHoverDrawer constrHoverDrawer;

	public TrapHoverEvent(Trap trap, ConstructionHoverDrawer constrHoverDrawer) {
		this.constrHoverDrawer = constrHoverDrawer;
		constrHoverDrawer.setConstructionDimensions(trap.occupiedArea());
	}

	@Override
	public void handle(MouseEvent t) {
		constrHoverDrawer.updateUpperLeftCornerCoordinates((int)t.getX(), (int)t.getY());
	}
	
}
