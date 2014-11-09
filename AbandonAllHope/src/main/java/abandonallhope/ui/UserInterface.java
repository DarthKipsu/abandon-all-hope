
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

public class UserInterface implements EventHandler {
	private Canvas canvas;
	private GraphicsContext gc;
	private PersonDrawer personDrawer;
	private Game game;
	private Duration frameDuration;

	public UserInterface(Game game) {
		canvas = new Canvas(500, 500);
		gc = canvas.getGraphicsContext2D();
		this.game = game;
		personDrawer = new PersonDrawer(game, gc);
		frameDuration = Duration.millis(1000/12);
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
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
	}
}
