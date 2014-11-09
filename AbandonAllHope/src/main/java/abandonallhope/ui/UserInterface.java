
package abandonallhope.ui;

import abandonallhope.domain.Person;
import abandonallhope.domain.Survivor;
import abandonallhope.logic.Game;
import java.awt.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
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
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		drawSurvivors(gc);
		drawZombies(gc);
	}
	
	private void drawSurvivors(GraphicsContext gc) {
		setGraphicsContextAttributes(gc, Color.BLACK, Color.RED, 2);
		for (Survivor survivor : game.getSurvivors()) {
			drawASurvivor(survivor, gc);
		}
	}

	private void setGraphicsContextAttributes(GraphicsContext gc, Color fill,
			Color stroke, int lineWidth) {
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);
	}

	private void drawASurvivor(Survivor survivor, GraphicsContext gc) {
		Point location = survivor.getLocation();
		gc.fillRect((double)location.x, (double)location.y, 3.0, 3.0);
		if (survivor.isSelected()) {
			drawSelectionMarkerAroundSurvivor(gc, location);
		}
	}

	private void drawSelectionMarkerAroundSurvivor(GraphicsContext gc, Point location) {
		gc.strokeRect((double)location.x - 2, (double)location.y - 2, 7.0, 7.0);
	}
	
	private void drawZombies(GraphicsContext gc) {
		setGraphicsContextAttributes(gc, Color.GREEN, Color.RED, 2);
		for (Person zombie : game.getZombies()) {
			drawAZombie(zombie, gc);
		}
	}

	private void drawAZombie(Person zombie, GraphicsContext gc) {
		Point location = zombie.getLocation();
		gc.fillRect((double)location.x, (double)location.y, 2.0, 2.0);
	}
}
