
package abandonallhope.ui;

import abandonallhope.logic.Game;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Panel showing the player messages containing info about what is happening in the game.
 * @author kipsu
 */
public class MessagePanel {
	
	private VBox vbox;

	/**
	 * Creates a new message panel displaying messages to player.
	 */
	public MessagePanel(Game game) {
		vbox = new VBox();
		vbox.getStyleClass().add("bottom");
		vbox.setPrefHeight(152);
		game.setMessages(this);
	}

	public VBox getVbox() {
		return vbox;
	}
	
	/**
	 * Shows the player a new message and removes old ones from sight if message count
	 * is greater than 5.
	 * @param message message to be displayed
	 */
	public void addMessage(String message) {
		vbox.getChildren().add(0, new Text("     " + message));
		if (vbox.getChildren().size() > 5) {
			vbox.getChildren().remove(5);
		}
	}
	
	/**
	 * Removes all messages from message panel.
	 */
	public void clearMessages() {
		vbox.getChildren().clear();
	}
	
}
