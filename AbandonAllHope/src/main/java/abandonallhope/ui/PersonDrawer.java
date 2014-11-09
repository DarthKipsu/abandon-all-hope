
package abandonallhope.ui;

import abandonallhope.domain.Person;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.logic.Game;
import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PersonDrawer {
	
	private Game game;
	private GraphicsContext gc;

	public PersonDrawer(Game game, GraphicsContext gc) {
		this.game = game;
		this.gc = gc;
	}
		
	public void drawSurvivors() {
		setGraphicsContextAttributes(Color.BLACK, Color.RED, 2);
		for (Survivor survivor : game.getSurvivors()) {
			drawASurvivor(survivor);
		}
	}
	
	public void drawZombies() {
		setGraphicsContextAttributes(Color.GREEN, Color.RED, 2);
		for (Zombie zombie : game.getZombies()) {
			drawAZombie(zombie);
		}
	}

	private void setGraphicsContextAttributes(Color fill, Color stroke, int lineWidth) {
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);
	}

	private void drawASurvivor(Survivor survivor) {
		Point location = survivor.getLocation();
		gc.fillRect((double)location.x, (double)location.y, 3.0, 3.0);
		if (survivor.isSelected()) {
			drawSelectionMarkerAroundSurvivor(location);
		}
	}

	private void drawSelectionMarkerAroundSurvivor(Point location) {
		gc.strokeRect((double)location.x - 2, (double)location.y - 2, 7.0, 7.0);
	}

	private void drawAZombie(Person zombie) {
		Point location = zombie.getLocation();
		gc.fillRect((double)location.x, (double)location.y, 2.0, 2.0);
	}
}
