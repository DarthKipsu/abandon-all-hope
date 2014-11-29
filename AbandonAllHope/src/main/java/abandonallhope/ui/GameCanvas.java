
package abandonallhope.ui;

import abandonallhope.domain.constructions.Wall;
import abandonallhope.ui.drawing.ObjectsDrawer;
import abandonallhope.ui.drawing.ConstructionHoverDrawer;
import abandonallhope.events.mouse.SurvivorEvent;
import abandonallhope.events.mouse.WallBuildEvent;
import abandonallhope.events.mouse.WallBuildHoverEvent;
import abandonallhope.events.mouse.WallHoverEvent;
import abandonallhope.logic.Game;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Contains game field presentation and object drawing.
 * @author kipsu
 */
public class GameCanvas implements EventHandler{
	
	private Game game;
	private Canvas canvas;
	private GraphicsContext gc;
	
	private ObjectsDrawer objectsDrawer;
	private ConstructionHoverDrawer constrHoverDrawer;
	
	private SurvivorEvent selectionEvent;
	private WallHoverEvent wallHoverEvent;
	private WallBuildEvent wallBuildEvent;
	private WallBuildHoverEvent wallBuildHoverEvent;

	/**
	 * Creates a new game canvas and drawer classes to draw objects on game field.
	 * @param game 
	 */
	public GameCanvas(Game game) {
		this.game = game;
		canvas = new Canvas(500, 500);
		gc = canvas.getGraphicsContext2D();
		objectsDrawer = new ObjectsDrawer(game, gc);
		constrHoverDrawer = new ConstructionHoverDrawer(game, gc);
		selectionEvent = new SurvivorEvent(game);
		addSurvivorSelectorEventListener();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	/**
	 * Adds a selection event to game canvas for selecting survivors.
	 */
	public void addSurvivorSelectorEventListener() {
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, selectionEvent);
	}

	/**
	 * Removes a survivor selection event from game canvas.
	 */
	public void removeSurvivorSelectorEventListener() {
		canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, selectionEvent);
	}

	/**
	 * Adds event listeners to display a shadow of a wall before building it and
	 * listener that will handle the wall building once the building location is clicked.
	 * @param wall wall element containing information about the wall type
	 */
	public void addWallHoverEventListener(Wall wall) {
		wallHoverEvent = new WallHoverEvent(wall, constrHoverDrawer);
		wallBuildEvent = new WallBuildEvent(game, this, wall);
		canvas.addEventHandler(MouseEvent.MOUSE_MOVED, wallHoverEvent);
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, wallBuildEvent);
	}

	/**
	 * Removes wall hover event from game canvas.
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
		canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, wallBuildEvent);
		canvas.removeEventHandler(MouseEvent.MOUSE_MOVED, wallBuildHoverEvent);
		wallHoverEvent = null;
		addSurvivorSelectorEventListener();
	}

	@Override
	public void handle(Event t) {
		gc.setFill(Color.KHAKI);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		objectsDrawer.drawObjects(game.getWalls());
		objectsDrawer.drawSurvivors();
		objectsDrawer.drawObjects(game.getZombies());
		objectsDrawer.drawObjects(game.getBullets());
		if (wallHoverEvent != null) {
			constrHoverDrawer.drawConstructionShadows();
		}
	}
	
}
