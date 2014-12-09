
package abandonallhope.events.action;

import abandonallhope.logic.Game;
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
	private Game game;

	/**
	 * Deselect all survivors and cancel building.
	 * @param canvas
	 * @param game 
	 */
	public DeselectEvent(GameCanvas canvas, Game game) {
		this.canvas = canvas;
		this.game = game;
	}

	@Override
	public void handle(ActionEvent t) {
		new SurvivorSelector(game.getSurvivors()).unselectAll();
		canvas.removeWallBuildingEventListeners();
		canvas.removeTrapBuildingEventListeners();
	}
	
}
