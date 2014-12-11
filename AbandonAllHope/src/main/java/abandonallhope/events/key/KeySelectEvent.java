package abandonallhope.events.key;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Katana;
import abandonallhope.logic.Game;
import abandonallhope.logic.Items;
import abandonallhope.logic.SurvivorSelector;
import abandonallhope.ui.GameCanvas;
import abandonallhope.ui.MessagePanel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Event to track keystrokes to select or deselect survivors. Also handles
 * creating easter egg Michonne
 *
 * @author kipsu
 */
public class KeySelectEvent implements EventHandler<KeyEvent> {

	private GameCanvas canvas;
	private Items items;
	private Game game;

	private String michonneEgg = "michonne";
	private int eggIndex = 0;
	private boolean michonneNotYetPlaced = true;

	/**
	 * Creates a new key selection event to track player keystrokes.
	 *
	 * @param canvas canvas containing other events
	 * @param items game containing selectable objects
	 */
	public KeySelectEvent(GameCanvas canvas, Items items, Game game) {
		this.canvas = canvas;
		this.game = game;
		this.items = items;
	}

	@Override
	public void handle(KeyEvent keyPressed) {
		KeyCode key = keyPressed.getCode();
		if (typingMichonneEgg(key, keyPressed)) {
			checkIfMichonneCanBeAdded();
		} else {
			handleOtherKeys(key, keyPressed);
		}
	}

	private boolean typingMichonneEgg(KeyCode key, KeyEvent keyPressed) {
		return michonneNotYetPlaced && key.isLetterKey() && keyPressed.getText().charAt(0) == michonneEgg.charAt(eggIndex);
	}

	private void checkIfMichonneCanBeAdded() {
		if (eggIndex == 7) {
			addMichonne();
			MessagePanel.addMessage("Michonne has joined your team!");
		} else {
			eggIndex++;
		}
	}

	private void addMichonne() {
		michonneNotYetPlaced = false;
		Survivor michonne = new Survivor(new Point(250, 250), items.getMap(), "Michonne", 0);
		michonne.setWeapon(new Katana());
		items.add(michonne);
	}

	private void handleOtherKeys(KeyCode key, KeyEvent keyPressed) throws NumberFormatException {
		if (key == KeyCode.ESCAPE) {
			deselectEverything();
		} else if (key == KeyCode.PAUSE) {
			game.pause();
		} else if (key.isDigitKey()) {
			selectSurvivorWithKeyID(keyPressed);
		}
	}

	private void deselectEverything() {
		new SurvivorSelector(items.getSurvivors()).unselectAll();
		canvas.removeWallBuildingEventListeners();
		canvas.removeTrapBuildingEventListeners();
	}

	private void selectSurvivorWithKeyID(KeyEvent keyPressed) throws NumberFormatException {
		deselectEverything();
		for (Survivor survivor : items.getSurvivors()) {
			if (survivor.getId() == Integer.parseInt(keyPressed.getText())) {
				survivor.select();
			}
		}
	}

}
