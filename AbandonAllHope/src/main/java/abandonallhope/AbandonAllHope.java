
package abandonallhope;

import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.logic.Game;
import abandonallhope.ui.UserInterface;
import java.awt.Point;
import java.util.Random;
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
		addFigures(5, 2, game);
		UserInterface ui = new UserInterface(game);
		primaryStage.setTitle(":: Abandon All Hope ::");
		Group root = new Group();
		root.getChildren().add(ui.getCanvas());
		ui.runGame();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	// Temporarily here for testing...
	public void addFigures(int zombie, int survivor, Game game) {
		Random random = new Random();
		for (int i = 0; i < zombie; i++) {
			game.add(new Zombie(new Point(random.nextInt(500), random.nextInt(500)), game.getMap()));
		}
		for (int i = 0; i < survivor; i++) {
			game.add(new Survivor(new Point(random.nextInt(500), random.nextInt(500)), game.getMap()));
		}
	}
	
}
