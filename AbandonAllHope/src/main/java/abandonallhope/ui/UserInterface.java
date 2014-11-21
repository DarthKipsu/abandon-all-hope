
package abandonallhope.ui;

import abandonallhope.logic.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * UserInterface class will display the game events to the user.
 * @author kipsu
 */
public class UserInterface implements EventHandler {
	private Canvas canvas;
	private GraphicsContext gc;
	private PersonDrawer personDrawer;
	private BulletDrawer bulletDrawer;
	private Game game;
	private Duration frameDuration;

	/**
	 * Creates a new user interface for a game
	 * @param game game the user interface will use
	 */
	public UserInterface(Game game) {
		canvas = new Canvas(500, 500);
		gc = canvas.getGraphicsContext2D();
		this.game = game;
		personDrawer = new PersonDrawer(game, gc);
		bulletDrawer = new BulletDrawer(game, gc);
		frameDuration = Duration.millis(1000 / 60);
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
	/**
	 * Create and start time lines to control the game and user interface speed 
	 * and create events for each frame.
	 */
	public void runGame() {
		Timeline gameTimeline = createTimeline(game);
		Timeline uiTimeline = createTimeline(this);
		
		SurvivorEvent selection = new SurvivorEvent(game);
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, selection);
		
		gameTimeline.play();
		uiTimeline.play();
	}

	private Timeline createTimeline(EventHandler eventHandler) {
		Timeline gameTimeline = new Timeline(new KeyFrame(frameDuration, eventHandler));
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		return gameTimeline;
	}

	@Override
	public void handle(Event t) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		personDrawer.drawSurvivors();
		personDrawer.drawZombies();
		bulletDrawer.drawBullets();
	}
}
