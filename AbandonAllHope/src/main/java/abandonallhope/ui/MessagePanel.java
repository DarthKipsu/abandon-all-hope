
package abandonallhope.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Panel showing the player messages containing info about what is happening in the game.
 * @author kipsu
 */
public class MessagePanel {
	
	private static VBox vbox;

	/**
	 * Creates a new message panel displaying messages to player.
	 */
	public MessagePanel() {
		vbox = new VBox();
		vbox.getStyleClass().add("bottom");
		vbox.setPrefHeight(152);
	}

	public VBox getVbox() {
		return vbox;
	}
	
	/**
	 * Shows the player a new message and removes old ones from sight if message count
	 * is greater than 5.
	 * @param message message to be displayed
	 */
	public static void addMessage(String message) {
		vbox.getChildren().add(0, new Text("     " + message));
		if (vbox.getChildren().size() > 5) {
			vbox.getChildren().remove(5);
		}
	}
	
	/**
	 * Removes all messages from message panel.
	 */
	public static void clearMessages() {
		vbox.getChildren().clear();
	}
	
	/**
	 * Get messages panel contents. Used for testing.
	 * @return list containing the messages.
	 */
	public static ObservableList<Node> getMessages() {
		return vbox.getChildren();
	}
	
}
