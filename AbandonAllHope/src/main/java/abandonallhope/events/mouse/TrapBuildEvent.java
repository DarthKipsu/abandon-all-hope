package abandonallhope.events.mouse;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Trap;
import abandonallhope.logic.Items;
import abandonallhope.ui.GameCanvas;
import abandonallhope.ui.MessagePanel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Event handler to build traps once user clicks the build location.
 * @author kipsu
 */
public class TrapBuildEvent implements EventHandler<MouseEvent> {

	private Items items;
	private GameCanvas canvas;
	private Trap trap;

	/**
	 * Build event will build the trap the player is hovering over game field on
	 * click. Will also remove trap building related event listeners.
	 * @param items items object where the trap will be built.
	 * @param canvas canvas containing the event listeners.
	 * @param trap Trap type.
	 */
	public TrapBuildEvent(Items items, GameCanvas canvas, Trap trap) {
		this.items = items;
		this.canvas = canvas;
		this.trap = trap;
	}

	@Override
	public void handle(MouseEvent t) {
		if (items.getMap().hasTrap(t.getX(), t.getY())) {
			warnAboutTheOtherTrap();
		} else {
			buildTheTrap(t);
		}
	}

	private void warnAboutTheOtherTrap() {
		MessagePanel.addMessage("Can't build on top of another trap!");
	}

	private void buildTheTrap(MouseEvent t) {
		trap.setLocation(new Point(t.getX(), t.getY()));
		items.add(trap);
		items.getInventory().payResources(trap.getCost());
		canvas.removeTrapBuildingEventListeners();
	}

}
