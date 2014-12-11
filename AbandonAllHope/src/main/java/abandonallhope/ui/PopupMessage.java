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

	public static void setStage(Stage stage) {
		PopupMessage.stage = stage;
	}

	public static void showGameOverMessage(final Game game) {
		Label label = createGameOverLabel("All survivors are lost! You managed to" +
				"survive for " + (DayChanger.day - 1) + " days!\n" + 
				"                               Game over!\n\n" +
				"        Start a new game by clicking anywhere on stage!");
		final Popup popup = createPopup(label);
		popup.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
				popup.setY(stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2);
			}
		});
		popup.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				game.startANewGame();
			}
		});
		popup.show(stage);
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

	private static Label createGameOverLabel(String message) {
		Label label = new Label(message);
		label.getStylesheets().add("/uiStyle.css");
		label.getStyleClass().add("popup");
		return label;
	}

}
