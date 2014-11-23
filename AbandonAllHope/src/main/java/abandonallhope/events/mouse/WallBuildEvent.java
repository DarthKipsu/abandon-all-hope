
package abandonallhope.events.mouse;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.domain.constructions.WoodenWall;
import abandonallhope.events.action.WallEvent;
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
	private WallEvent.WallType type;

	/**
	 * Creates a new AllBuildEvent that will build the wall the player is hovering
	 * over game field on click. Will also remove event listeners related to building
	 * walls.
	 * @param game game where the wall will be built
	 * @param canvas canvas with event listeners to build the wall and it's shadow
	 * @param type Wall type
	 */
	public WallBuildEvent(Game game, GameCanvas canvas, WallEvent.WallType type) {
		this.game = game;
		this.canvas = canvas;
		this.type = type;
	}

	@Override
	public void handle(MouseEvent t) {
		canvas.removeWallHoverEventListener();
		if (type == WallEvent.WallType.WOODEN) {
			game.add(new WoodenWall(Wall.Orientation.HORIZONAL,
					new Point(t.getSceneX(), t.getSceneY())));
		}
	}
	
}
