package abandonallhope.events.action;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.domain.constructions.WallType;
import abandonallhope.logic.Game;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles what happens after user clicks build wall button.
 *
 * @author kipsu
 */
public class WallEvent implements EventHandler<ActionEvent> {

	private GameCanvas canvas;
	private Game game;
	private WallType wallType;
	private Wall wall;

	/**
	 * Creates a new wall event.
	 *
	 * @param canvas GameCanvas object containing information about drawing to
	 * game field.
	 * @param game Game object containing information about objects in game.
	 */
	public WallEvent(GameCanvas canvas, Game game, WallType wallType) {
		this.canvas = canvas;
		this.game = game;
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
		new SurvivorSelector(game.getSurvivors()).unselectAll();
	}

	private void createNewWall() {
		this.wall = new Wall(wallType, Wall.Orientation.HORIZONAL, new Point(0, 500));
	}

	private boolean enoughResourcesForAtLeastOneWall() {
		return game.getInventory().enoughResources(wall.getCost());
	}

	private void removePreviousEventListeners() {
		canvas.removeWallBuildingEventListeners();
		canvas.removeTrapBuildingEventListeners();
		canvas.removeSurvivorSelectorEventListener();
	}

	private void showErrorMessage() {
		game.getMessages().addMessage("Not enough resources to build!");
	}

}
