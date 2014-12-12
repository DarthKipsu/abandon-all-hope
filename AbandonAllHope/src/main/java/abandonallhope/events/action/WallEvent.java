package abandonallhope.events.action;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.domain.constructions.WallType;
import abandonallhope.logic.Items;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import abandonallhope.ui.MessagePanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles what happens after user clicks build wall button.
 * @author kipsu
 */
public class WallEvent implements EventHandler<ActionEvent> {

	private GameCanvas canvas;
	private Items items;
	private WallType wallType;
	private Wall wall;

	/**
	 * Creates a new wall event.
	 *
	 * @param canvas GameCanvas object containing information about drawing to game field.
	 * @param items Items object containing information about objects in game.
	 * @param wallType walltype to be built
	 */
	public WallEvent(GameCanvas canvas, Items items, WallType wallType) {
		this.canvas = canvas;
		this.items = items;
		this.wallType = wallType;
	}

	@Override
	public void handle(ActionEvent t) {
		removeSurvivorSelections();
		createNewWall();
		if (enoughResourcesForAtLeastOneWall()) {
			removePreviousEventListeners();
			canvas.addWallHoverEventListener(wall);
		} else {
			showErrorMessage();
		}
	}

	private void removeSurvivorSelections() {
		new SurvivorSelector(items.getSurvivors()).unselectAll();
	}

	private void createNewWall() {
		this.wall = new Wall(wallType, Wall.Orientation.HORIZONAL, new Point(0, 500));
	}

	private boolean enoughResourcesForAtLeastOneWall() {
		return items.getInventory().enoughResources(wall.getCost());
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
