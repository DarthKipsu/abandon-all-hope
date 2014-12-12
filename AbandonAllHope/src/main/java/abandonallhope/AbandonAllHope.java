
package abandonallhope;

import abandonallhope.logic.Game;
import abandonallhope.ui.PopupMessage;
import abandonallhope.ui.UserInterface;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for abandon all hope td game
 */
public class AbandonAllHope extends Application {
	
	/**
	 * Will launch the application
	 * @param args none needed
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		PopupMessage.setStage(primaryStage);
		Group root = newGame();
		primaryStage.setTitle(":: Abandon All Hope ::");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	private Group newGame() {
		Game game = new Game(500);
		UserInterface ui = new UserInterface(game);
		Group root = new Group();
		root.getChildren().add(ui.getBorder());
		ui.runGame();
		return root;
	}
	
}
