package abandonallhope.events.action;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Trap;
import abandonallhope.domain.constructions.TrapType;
import abandonallhope.logic.Items;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import abandonallhope.ui.MessagePanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles what happens after user clicks build trap button.
 * @author kipsu
 */
public class TrapEvent implements EventHandler<ActionEvent> {

	private GameCanvas canvas;
	private Items items;
	private TrapType trapType;
	private Trap trap;

	/**
	 * Creates a new trap event.
	 * @param canvas game canvas
	 * @param items items where the trap will be built in
	 * @param trapType type of the trap
	 */
	public TrapEvent(GameCanvas canvas, Items items, TrapType trapType) {
		this.canvas = canvas;
		this.items = items;
		this.trapType = trapType;
	}

	@Override
	public void handle(ActionEvent t) {
		removeSurvivorSelections();
		createNewTrap();
		if (enoughResourcesToBuild()) {
			removePreviousEventListeners();
			canvas.addTrapHoverEventListener(trap);
		} else {
			showErrorMessage();
		}
	}

	private void removeSurvivorSelections() {
		new SurvivorSelector(items.getSurvivors()).unselectAll();
	}

	private void createNewTrap() {
		trap = new Trap(new Point(0, 500), trapType);
	}

	private boolean enoughResourcesToBuild() {
		return items.getInventory().enoughResources(trap.getCost());
	}

	private void removePreviousEventListeners() {
		canvas.removeWallBuildingEventListeners();
		canvas.removeTrapBuildingEventListeners();
		canvas.removeSurvivorSelectorEventListener();
	}

	private void showErrorMessage() {
		MessagePanel.addMessage("Not enough resources to build!");
	}
}
