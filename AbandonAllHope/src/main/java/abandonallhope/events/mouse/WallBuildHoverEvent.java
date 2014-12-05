
package abandonallhope.events.mouse;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Cost;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.ui.drawing.ConstructionHoverDrawer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Event that displays where the wall will be built when construction start point
 * has been set but end point has not.
 * @author kipsu
 */
public class WallBuildHoverEvent implements EventHandler<MouseEvent> {
	
	private Point start;
	private ConstructionHoverDrawer constrHoverDrawer;

	public WallBuildHoverEvent(Wall wall, ConstructionHoverDrawer constrHoverDrawer) {
		this.start = wall.getLocation();
		this.constrHoverDrawer = constrHoverDrawer;
		constrHoverDrawer.setConstructionDimensions(wall.occupiedArea());
	}

	@Override
	public void handle(MouseEvent t) {
		int width = (int) (t.getX() - start.x);
		int height = (int) (t.getY() - start.y);
		
		if (Math.abs(width) > Math.abs(height)) {
			height = 0;
		} else {
			width = 0;
		}
		constrHoverDrawer.updateBuildingDimensions(width, height);
	}
	
}
