
package abandonallhope.ui;

import abandonallhope.logic.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * UserInterface class will display the game events to the user.
 * @author kipsu
 */
public class UserInterface implements EventHandler {
	private BorderPane border;
	private GameCanvas canvas;
	private BuildPanel build;
	private Game game;
	private Duration frameDuration;

	/**
	 * Creates a new user interface for a game, that handles game time line and
	 * contains all parts the user interface consist of.
	 * @param game game the user interface will use
	 */
	public UserInterface(Game game) {
		canvas = new GameCanvas(game);
		build = new BuildPanel(game, canvas);
		border = new BorderPane();
		border.setCenter(canvas.getCanvas());
		border.setRight(build.getVbox());
		this.game = game;
		frameDuration = Duration.millis(1000 / 60);
	}

	/**
	 * Get the border pane element containing objects visible to user.
	 * @return 
	 */
	public BorderPane getBorder() {
		return border;
	}
	
	/**
	 * Create and start time lines to control the game and user interface speed 
	 * and create events for each frame.
	 */
	public void runGame() {
		Timeline gameTimeline = createTimeline(game);
		Timeline uiTimeline = createTimeline(this);
		
		SurvivorEvent selection = new SurvivorEvent(game);
		canvas.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, selection);
		
		gameTimeline.play();
		uiTimeline.play();
	}

	@Override
	public void handle(Event t) {
		canvas.handle(t);
	}

	private Timeline createTimeline(EventHandler eventHandler) {
		Timeline gameTimeline = new Timeline(new KeyFrame(frameDuration, eventHandler));
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		return gameTimeline;
	}
}
