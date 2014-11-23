
package abandonallhope.events.action;

import abandonallhope.logic.Game;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles what happens after user clicks build wall button.
 * @author kipsu
 */
public class WallEvent implements EventHandler<ActionEvent> {
	
	public enum WallType {
		WOODEN
	}

	private GameCanvas canvas;
	private Game game;
	private WallType type;

	/**
	 * Creates a new wall event.
	 * @param canvas GameCanvas object containing information about drawing to
	 * game field.
	 * @param game Game object containing information about objects in game.
	 */
	public WallEvent(GameCanvas canvas, Game game, WallType type) {
		this.canvas = canvas;
		this.game = game;
		this.type = type;
	}
	
	@Override
	public void handle(ActionEvent t) {
		new SurvivorSelector(game.getSurvivors()).unselectAll();
		canvas.removeSurvivorSelectorEventListener();
		canvas.addWallHoverEventListener(type);
	}
	
}
