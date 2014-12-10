package abandonallhope.events.mouse;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Trap;
import abandonallhope.logic.Game;
import abandonallhope.ui.GameCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Event handler to build traps once user clicks the build location.
 *
 * @author kipsu
 */
public class TrapBuildEvent implements EventHandler<MouseEvent> {

	private Game game;
	private GameCanvas canvas;
	private Trap trap;

	/**
	 * Build event will build the trap the player is hovering over game field on
	 * click. Will also remove trap building related event listeners.
	 *
	 * @param game game where the trap will be built.
	 * @param canvas canvas containing the event listeners.
	 * @param trap Trap type.
	 */
	public TrapBuildEvent(Game game, GameCanvas canvas, Trap trap) {
		this.game = game;
		this.canvas = canvas;
		this.trap = trap;
	}

	@Override
	public void handle(MouseEvent t) {
		if (game.getMap().hasTrap(t.getX(), t.getY())) {
			warnAboutTheOtherTrap();
		} else {
			buildTheTrap(t);
		}
	}

	private void warnAboutTheOtherTrap() {
		game.getMessages().addMessage("Can't build on top of another trap!");
	}

	private void buildTheTrap(MouseEvent t) {
		trap.setLocation(new Point(t.getX(), t.getY()));
		game.add(trap);
		game.getInventory().payResources(trap.getCost());
		canvas.removeTrapBuildingEventListeners();
	}

}
