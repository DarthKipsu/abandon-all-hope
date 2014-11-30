package abandonallhope.events.key;

import abandonallhope.domain.Survivor;
import abandonallhope.logic.Game;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Event to track keystrokes to select or deselect survivors.
 *
 * @author kipsu
 */
public class KeySelectEvent implements EventHandler<KeyEvent> {

	private GameCanvas canvas;
	private Game game;

	/**
	 * Creates a new key selection event to track player keystrokes.
	 * @param canvas canvas containing other events
	 * @param game game containing selectable objects
	 */
	public KeySelectEvent(GameCanvas canvas, Game game) {
		this.canvas = canvas;
		this.game = game;
	}

	@Override
	public void handle(KeyEvent keyPressed) {
		if (keyPressed.getCode() == KeyCode.ESCAPE) {
			deselectEverything();
		} else if (keyPressed.getCode().isDigitKey()) {
			selectSurvivorWithKeyID(keyPressed);
		}
	}

	private void deselectEverything() {
		new SurvivorSelector(game.getSurvivors()).unselectAll();
		canvas.removeWallBuildingEventListeners();
		canvas.removeTrapBuildingEventListeners();
	}

	private void selectSurvivorWithKeyID(KeyEvent keyPressed) throws NumberFormatException {
		deselectEverything();
		for (Survivor survivor : game.getSurvivors()) {
			if (survivor.getId() == Integer.parseInt(keyPressed.getText())) {
				survivor.select();
			}
		}
	}

}
