
package abandonallhope;

import abandonallhope.logic.Game;
import abandonallhope.ui.UserInterface;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AbandonAllHope extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Game game = new Game(500);
		UserInterface ui = new UserInterface(game);
		primaryStage.setTitle(":: Abandon All Hope ::");
		Group root = new Group();
		root.getChildren().add(ui.getCanvas());
		ui.runGame();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
}
