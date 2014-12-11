package abandonallhope.ui;

import abandonallhope.domain.constructions.*;
import abandonallhope.ui.drawing.*;
import abandonallhope.events.mouse.*;
import abandonallhope.logic.Items;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Contains game field presentation and events.
 *
 * @author kipsu
 */
public class GameCanvas implements EventHandler {

	private Items items;
	private Canvas canvas;
	private GraphicsContext gc;
	private Image background;

	private ObjectsDrawer objectsDrawer;
	private ConstructionHoverDrawer constrHoverDrawer;

	private SurvivorEvent survivorSelectionEvent;
	private WallHoverEvent wallHoverEvent;
	private WallBuildEvent wallBuildEvent;
	private WallBuildHoverEvent wallBuildHoverEvent;
	private TrapHoverEvent trapHoverEvent;
	private TrapBuildEvent trapBuildEvent;

	/**
	 * Creates a new game canvas and drawer classes to draw objects on game
	 * field.
	 *
	 * @param items
	 */
	public GameCanvas(Items items) {
		this.items = items;
		canvas = new Canvas(500, 500);
		gc = canvas.getGraphicsContext2D();
		background = new Image("/tausta.jpg");
		objectsDrawer = new ObjectsDrawer(items, gc);
		constrHoverDrawer = new ConstructionHoverDrawer(items, gc);
		survivorSelectionEvent = new SurvivorEvent(items);
		addSurvivorSelectorEventListener();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	@Override
	public void handle(Event t) {
		resetCanvasBase();
		objectsDrawer.drawObjects(items.getTraps());
		objectsDrawer.drawObjects(items.getWalls());
		objectsDrawer.drawSurvivors();
		objectsDrawer.drawObjects(items.getZombies());
		objectsDrawer.drawObjects(items.getBullets());
		if (wallHoverEvent != null || trapHoverEvent != null) {
			constrHoverDrawer.drawConstructionShadows();
		}
	}

	/**
	 * Adds a selection event to game canvas for selecting survivors.
	 */
	public void addSurvivorSelectorEventListener() {
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, survivorSelectionEvent);
	}

	/**
	 * Removes a survivor selection event from game canvas.
	 */
	public void removeSurvivorSelectorEventListener() {
		try {
			canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, survivorSelectionEvent);
		} catch (NullPointerException e) {
			// ignore
		}
	}

	/**
	 * Adds event listeners to display a shadow of a wall before building it and
	 * listener that will handle the wall building once the building location is
	 * clicked.
	 *
	 * @param wall wall element containing information about the wall type
	 */
	public void addWallHoverEventListener(Wall wall) {
		wallHoverEvent = new WallHoverEvent(wall, constrHoverDrawer);
		wallBuildEvent = new WallBuildEvent(items, this, wall);
		canvas.addEventHandler(MouseEvent.MOUSE_MOVED, wallHoverEvent);
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, wallBuildEvent);
	}

	/**
	 * Removes wall hover event from game canvas.
	 *
	 * @param wall wall element containing information about the wall type
	 */
	public void changeToBuildHoverEventListener(Wall wall) {
		wallBuildHoverEvent = new WallBuildHoverEvent(wall, constrHoverDrawer);
		canvas.removeEventHandler(MouseEvent.MOUSE_MOVED, wallHoverEvent);
		canvas.addEventHandler(MouseEvent.MOUSE_MOVED, wallBuildHoverEvent);
	}

	/**
	 * Remove all event listeners for building a wall and add survivor selector
	 * event listener.
	 */
	public void removeWallBuildingEventListeners() {
		try {
			canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, wallBuildEvent);
			canvas.removeEventHandler(MouseEvent.MOUSE_MOVED, wallBuildHoverEvent);
			canvas.removeEventHandler(MouseEvent.MOUSE_MOVED, wallHoverEvent);
			wallHoverEvent = null;
			addSurvivorSelectorEventListener();
		} catch (NullPointerException e) {
			// ignore
		}
	}

	/**
	 * Adds event listeners to display a shadow of a trap before building it and
	 * listener that will handle the trap building once the building location is
	 * clicked.
	 *
	 * @param trap trap element containing information about the trap type
	 */
	public void addTrapHoverEventListener(Trap trap) {
		trapHoverEvent = new TrapHoverEvent(trap, constrHoverDrawer);
		trapBuildEvent = new TrapBuildEvent(items, this, trap);
		canvas.addEventHandler(MouseEvent.MOUSE_MOVED, trapHoverEvent);
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, trapBuildEvent);
	}

	public void removeTrapBuildingEventListeners() {
		try {
			canvas.removeEventHandler(MouseEvent.MOUSE_MOVED, trapHoverEvent);
			canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, trapBuildEvent);
			trapHoverEvent = null;
			addSurvivorSelectorEventListener();
		} catch (NullPointerException e) {
			// ignore
		}
	}

	private void resetCanvasBase() {
		gc.drawImage(background, 0, 0);
	}

}
