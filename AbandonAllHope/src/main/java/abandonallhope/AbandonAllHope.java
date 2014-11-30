
package abandonallhope;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.Point;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Magazine;
import abandonallhope.domain.weapons.Pistol;
import abandonallhope.logic.Game;
import abandonallhope.ui.UserInterface;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for abandon all hope td game
 * @author kipsu
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
		Game game = new Game(500);
		UserInterface ui = new UserInterface(game);
		primaryStage.setTitle(":: Abandon All Hope ::");
		Group root = new Group();
		root.getChildren().add(ui.getBorder());
		ui.runGame();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
}
