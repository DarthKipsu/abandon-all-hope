
package abandonallhope.ui;

import abandonallhope.logic.Game;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameCanvas implements EventHandler{
	
	private Game game;
	private Canvas canvas;
	private GraphicsContext gc;
	private PersonDrawer personDrawer;
	private BulletDrawer bulletDrawer;

	public GameCanvas(Game game) {
		this.game = game;
		canvas = new Canvas(500, 500);
		gc = canvas.getGraphicsContext2D();
		personDrawer = new PersonDrawer(game, gc);
		bulletDrawer = new BulletDrawer(game, gc);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	@Override
	public void handle(Event t) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		personDrawer.drawSurvivors();
		personDrawer.drawZombies();
		bulletDrawer.drawBullets();
	}
	
}
