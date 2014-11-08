
package abandonallhope.ui;

import abandonallhope.domain.Person;
import abandonallhope.logic.Game;
import java.awt.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class UserInterface implements EventHandler {
	private Canvas canvas;
	private Game game;
	private Duration frameDuration;

	public UserInterface(Game game) {
		canvas = new Canvas(500, 500);
		this.game = game;
		frameDuration = Duration.millis(1000/12);
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
	public void runGame() {
		Timeline gameTimeline = new Timeline(new KeyFrame(frameDuration, game));
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		
		Timeline uiTimeline = new Timeline(new KeyFrame(frameDuration, this));
		uiTimeline.setCycleCount(Timeline.INDEFINITE);
		
		gameTimeline.play();
		uiTimeline.play();
	}

	@Override
	public void handle(Event t) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		drawSurvivors(gc);
		drawZombies(gc);
	}
	
	private void drawSurvivors(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		for (Person survivor : game.getSurvivors()) {
			Point location = survivor.getLocation();
			gc.fillRect((double)location.x, (double)location.y, 3.0, 3.0);
		}
	}
	
	private void drawZombies(GraphicsContext gc) {
		gc.setFill(Color.GREEN);
		for (Person zombie : game.getZombies()) {
			Point location = zombie.getLocation();
			gc.fillRect((double)location.x, (double)location.y, 2.0, 2.0);
		}
	}
}
