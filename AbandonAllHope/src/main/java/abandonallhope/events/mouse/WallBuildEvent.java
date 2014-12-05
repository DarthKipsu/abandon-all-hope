package abandonallhope.events.mouse;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.logic.Game;
import abandonallhope.ui.GameCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Event handler to build walls when user clicks their build location.
 *
 * @author kipsu
 */
public class WallBuildEvent implements EventHandler<MouseEvent> {

	private Game game;
	private Inventory inventory;
	private GameCanvas canvas;
	private Wall wall;
	private boolean buildingIsFinal;
	
	private double startX;
	private double startY;
	private int wallAmount;

	/**
	 * Creates a new AllBuildEvent that will build the wall the player is
	 * hovering over game field on click. Will also remove event listeners
	 * related to building walls.
	 *
	 * @param game game where the wall will be built
	 * @param canvas canvas with event listeners to build the wall and it's
	 * shadow
	 * @param wall Wall type
	 */
	public WallBuildEvent(Game game, GameCanvas canvas, Wall wall) {
		this.game = game;
		this.canvas = canvas;
		this.wall = wall;
		inventory = game.getInventory();
		buildingIsFinal = false;
	}

	@Override
	public void handle(MouseEvent t) {
		if (buildingIsFinal) {
			buildWallsAndRemoveEventHandlers(t);
		} else {
			setStartLocationAndChangeEventHandlers(t);
		}
	}

	private void buildWallsAndRemoveEventHandlers(MouseEvent t) {
		startX = wall.getLocation().x;
		startY = wall.getLocation().y;
		Wall.Orientation orientation = setOrientation(t);
		addWallsToGame(orientation);
		canvas.removeWallBuildingEventListeners();
	}

	private Wall.Orientation setOrientation(MouseEvent t) {
		int buildingWidth = (int) (t.getX() - startX);
		int buildingHeight = (int) (t.getY() - startY);
		if (wallOrientationIsHorizonal(buildingWidth, buildingHeight)) {
			setHorizonalWallDimensions(buildingWidth, t);
			return Wall.Orientation.HORIZONAL;
		} else {
			setVerticalWallDimensions(buildingHeight, t);
			return Wall.Orientation.VERTICAL;
		}
	}

	private void addWallsToGame(Wall.Orientation orientation) {
		for (int i = 0; i < wallAmount && inventory.enoughResources(wall.getCost()); i++) {
			Wall newWall = new Wall(wall.getType(), orientation, new Point(startX, startY));
			game.add(newWall);
			inventory.payResources(wall.getCost());
			addWallWidthToCoordinates(orientation);
		}
	}

	private void addWallWidthToCoordinates(Wall.Orientation orientation) {
		if (orientation == Wall.Orientation.HORIZONAL) {
			startX += wall.getType().getWidth(orientation);
		} else {
			startY += wall.getType().getHeight(orientation);
		}
	}

	private static boolean wallOrientationIsHorizonal(int buildingWidth, int buildingHeight) {
		return Math.abs(buildingWidth) > Math.abs(buildingHeight);
	}

	private void setHorizonalWallDimensions(int buildingWidth, MouseEvent t) {
		setWallAmount(buildingWidth);
		if (t.getX() < startX) {
			startX -= decreaseWallStartingPointWithWidth();
		}
	}

	private void setVerticalWallDimensions(int buildingHeight, MouseEvent t) {
		setWallAmount(buildingHeight);
		if (t.getY() < startY) {
			startY -= decreaseWallStartingPointWithWidth();
		}
	}

	private void setWallAmount(int buildingHeight) {
		wallAmount = Math.abs(buildingHeight) / wall.getWidth();
		if (wallAmount / wall.getWidth() > inventory.getWood()) {
			wallAmount = inventory.getWood() * wall.getWidth();
		}
	}

	private double decreaseWallStartingPointWithWidth() {
		if (wallAmount > inventory.getWood()) {
			wallAmount = inventory.getWood();
		}
		return wallAmount * wall.getWidth();
	}

	private void setStartLocationAndChangeEventHandlers(MouseEvent t) {
		buildingIsFinal = true;
		wall.setLocation(new Point(t.getX(), t.getY()));
		canvas.changeToBuildHoverEventListener(wall);
	}

}
