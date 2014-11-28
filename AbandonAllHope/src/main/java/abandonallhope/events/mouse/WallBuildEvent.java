
package abandonallhope.events.mouse;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.logic.Game;
import abandonallhope.ui.GameCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Event handler to build walls when user clicks their build location.
 * @author kipsu
 */
public class WallBuildEvent implements EventHandler<MouseEvent> {
	
	private Game game;
	private GameCanvas canvas;
	private Wall wall;
	private boolean buildingIsFinal;

	/**
	 * Creates a new AllBuildEvent that will build the wall the player is hovering
	 * over game field on click. Will also remove event listeners related to building
	 * walls.
	 * @param game game where the wall will be built
	 * @param canvas canvas with event listeners to build the wall and it's shadow
	 * @param wall Wall type
	 */
	public WallBuildEvent(Game game, GameCanvas canvas, Wall wall) {
		this.game = game;
		this.canvas = canvas;
		this.wall = wall;
		buildingIsFinal = false;
	}

	@Override
	public void handle(MouseEvent t) {
		if (buildingIsFinal) {
			canvas.removeWallBuildingEventListeners();
			game.add(wall);
		} else {
			buildingIsFinal = true;
			wall.setLocation(new Point(t.getSceneX(), t.getSceneY()));
			canvas.add(wall);
			canvas.changeToBuildHoverEventListener(wall);
		}
	}
	
}
