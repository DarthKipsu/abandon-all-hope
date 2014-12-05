package abandonallhope.events.action;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Trap;
import abandonallhope.domain.constructions.TrapType;
import abandonallhope.logic.Game;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles what happens after user clicks build trap button.
 *
 * @author kipsu
 */
public class TrapEvent implements EventHandler<ActionEvent> {

	private GameCanvas canvas;
	private Game game;
	private TrapType trapType;
	private Trap trap;

	/**
	 * Creates a new trap event.
	 *
	 * @param canvas game canvas
	 * @param game game where the trap will be built in
	 * @param trapType type of the trap
	 */
	public TrapEvent(GameCanvas canvas, Game game, TrapType trapType) {
		this.canvas = canvas;
		this.game = game;
		this.trapType = trapType;
	}

	@Override
	public void handle(ActionEvent t) {
		new SurvivorSelector(game.getSurvivors()).unselectAll();
		trap = new Trap(new Point(0, 500), trapType);
		if (game.getInventory().enoughResources(trap.getCost())) {
			canvas.removeWallBuildingEventListeners();
			canvas.removeSurvivorSelectorEventListener();
			canvas.addTrapHoverEventListener(trap);
		} else {
			game.getMessages().addMessage("Not enough resources to build!");
		}
	}
}
