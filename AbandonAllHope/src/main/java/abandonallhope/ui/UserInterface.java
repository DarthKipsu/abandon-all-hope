package abandonallhope.ui;

import abandonallhope.events.key.KeySelectEvent;
import abandonallhope.logic.DayChanger;
import abandonallhope.logic.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * UserInterface class will display the game events to the user.
 * @author kipsu
 */
public class UserInterface implements EventHandler {
	private BorderPane border;
	private GameCanvas canvas;
	private Game game;
	private Duration frameDuration;
	private ResourcePanel resources;

	/**
	 * Creates a new user interface for a game, that handles game time line and
	 * contains all parts the user interface consist of.
	 * @param game game the user interface will use
	 */
	public UserInterface(Game game) {
		canvas = new GameCanvas(game.getItems());
		this.game = game;
		resources = new ResourcePanel(game);
		createBorder();
		frameDuration = Duration.millis(1000 / 60);
		border.addEventHandler(KeyEvent.KEY_PRESSED, new KeySelectEvent(canvas, game.getItems(), game));
	}

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

		gameTimeline.play();
		uiTimeline.play();

		setupDayOne(gameTimeline);
	}

	@Override
	public void handle(Event t) {
		resources.updateResources();
		canvas.handle(t);
	}

	private void setupDayOne(Timeline gameTimeline) {
		game.setGameTimeline(gameTimeline);
		DayChanger.setGame(game.getItems(), game.getTurn());
		game.startANewGame();
	}

	private void createBorder() {
		MessagePanel messages = new MessagePanel();
		BuildPanel build = new BuildPanel(game.getItems(), canvas);
		border = new BorderPane();
		border.getStylesheets().add("/uiStyle.css");
		border.setTop(createTopPanel());
		border.setCenter(canvas.getCanvas());
		border.setRight(build.getVbox());
		border.setLeft(resources.getVbox());
		border.setBottom(messages.getVbox());
	}

	private VBox createTopPanel() {
		VBox top = new VBox();
		top.getStyleClass().add("top");
		top.setPrefHeight(56);
		return top;
	}

	private Timeline createTimeline(EventHandler eventHandler) {
		Timeline gameTimeline = new Timeline(new KeyFrame(frameDuration, eventHandler));
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		return gameTimeline;
	}
}
