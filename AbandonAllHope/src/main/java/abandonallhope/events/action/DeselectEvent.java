
package abandonallhope.events.action;

import abandonallhope.logic.Items;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Event used to deselect all survivors and cancel building.
 * @author kipsu
 */
public class DeselectEvent implements EventHandler<ActionEvent> {
	
	private GameCanvas canvas;
	private Items items;

	/**
	 * Deselect all survivors and cancel building.
	 * @param canvas
	 * @param items 
	 */
	public DeselectEvent(GameCanvas canvas, Items items) {
		this.canvas = canvas;
		this.items = items;
	}

	@Override
	public void handle(ActionEvent t) {
		new SurvivorSelector(items.getSurvivors()).unselectAll();
		canvas.removeWallBuildingEventListeners();
		canvas.removeTrapBuildingEventListeners();
	}
	
}
