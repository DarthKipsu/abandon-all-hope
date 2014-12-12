package abandonallhope.ui;

import abandonallhope.logic.DayChanger;
import abandonallhope.logic.Game;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Creates a popup window for game over scenario
 *
 * @author kipsu
 */
public class PopupMessage {
	
	private static Stage stage;
	private static Popup pause = createPopup(createLabel(
			"                        PAUSED\nPress pause again to continue playing"));

	public static void setStage(Stage stage) {
		PopupMessage.stage = stage;
	}

	/**
	 * Display a popup message showing game over message and creating events for
	 * starting a new game.
	 * @param game 
	 */
	public static void showGameOverMessage(final Game game) {
		Label label = addGameOverMessage();
		final Popup popup = createPopup(label);
		centerPopupOnStage(popup);
		addNewGameEventHandler(popup, game);
		popup.show(stage);
	}
	
	/**
	 * Displays a popup message showing game is paused.
	 */
	public static void showPauseMessage() {
		pause.setAutoHide(false);
		pause.show(stage);
	}
	
	/**
	 * Removes popup message showing game is paused.
	 */
	public static void removePauseMessage() {
		pause.hide();
	}

	private static Label addGameOverMessage() {
		Label label = createLabel("All survivors are lost! You managed to" +
				"survive for " + DayChanger.day + " days!\n" +
				"                               Game over!\n\n" +
				"        Start a new game by clicking anywhere on stage!");
		return label;
	}

	private static Popup createPopup(Label label) {
		final Popup popup = new Popup();
		cratePopupSettings(popup);
		popup.getContent().add(label);
		return popup;
	}

	private static void cratePopupSettings(final Popup popup) {
		popup.setAutoFix(true);
		popup.setAutoHide(true);
		popup.setHideOnEscape(true);
	}

	private static Label createLabel(String message) {
		Label label = new Label(message);
		label.getStylesheets().add("/uiStyle.css");
		label.getStyleClass().add("popup");
		return label;
	}

	private static void centerPopupOnStage(final Popup popup) {
		popup.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
				popup.setY(stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2);
			}
		});
	}

	private static void addNewGameEventHandler(final Popup popup, final Game game) {
		popup.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				game.startANewGame();
			}
		});
	}

}
