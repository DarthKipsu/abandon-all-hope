
package abandonallhope.ui;

import abandonallhope.domain.constructions.Wall;
import abandonallhope.events.action.WallEvent;
import abandonallhope.events.mouse.SurvivorEvent;
import abandonallhope.events.mouse.WallBuildEvent;
import abandonallhope.events.mouse.WallHoverEvent;
import abandonallhope.logic.Game;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * Contains game field presentation and object drawing.
 * @author kipsu
 */
public class GameCanvas implements EventHandler{
	
	private Game game;
	private Canvas canvas;
	private GraphicsContext gc;
	
	private PersonDrawer personDrawer;
	private BulletDrawer bulletDrawer;
	private ConstructionHoverDrawer constrHoverDrawer;
	private WallDrawer wallDrawer;
	
	private SurvivorEvent selectionEvent;
	private WallHoverEvent wallHoverEvent;
	private WallBuildEvent wallBuildEvent;

	/**
	 * Creates a new game canvas and drawer classes to draw objects on game field.
	 * @param game 
	 */
	public GameCanvas(Game game) {
		this.game = game;
		canvas = new Canvas(500, 500);
		gc = canvas.getGraphicsContext2D();
		personDrawer = new PersonDrawer(game, gc);
		bulletDrawer = new BulletDrawer(game, gc);
		constrHoverDrawer = new ConstructionHoverDrawer(game, gc);
		wallDrawer = new WallDrawer(game, gc);
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
	 */
	public void addWallHoverEventListener(WallEvent.WallType type) {
		wallHoverEvent = new WallHoverEvent(type, constrHoverDrawer);
		wallBuildEvent = new WallBuildEvent(game, this, type);
		canvas.addEventHandler(MouseEvent.MOUSE_MOVED, wallHoverEvent);
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, wallBuildEvent);
	}

	/**
	 * Removes wall hover event from game canvas.
	 */
	public void removeWallHoverEventListener() {
		canvas.removeEventHandler(MouseEvent.MOUSE_MOVED, wallHoverEvent);
		canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, wallBuildEvent);
		wallHoverEvent = null;
		addSurvivorSelectorEventListener();
	}

	@Override
	public void handle(Event t) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		wallDrawer.drawWalls();
		personDrawer.drawSurvivors();
		personDrawer.drawZombies();
		bulletDrawer.drawBullets();
		if (wallHoverEvent != null) {
			constrHoverDrawer.drawConstructionShadows();
		}
	}
	
}
