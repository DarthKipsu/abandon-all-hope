
package abandonallhope;

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
		addFigures(10, 2, game);
		UserInterface ui = new UserInterface(game);
		primaryStage.setTitle(":: Abandon All Hope ::");
		Group root = new Group();
		root.getChildren().add(ui.getBorder());
		ui.runGame();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	/**
	 * Will create game figures for testing purposes during production
	 * @param zombie
	 * @param survivor
	 * @param game 
	 */
	public void addFigures(int zombie, int survivor, Game game) {
		Random random = new Random();
		String[] names = new String[]{"Uolevi", "Maija"};
		for (int i = 0; i < zombie; i++) {
			game.add(new Zombie(new Point(random.nextInt(500), random.nextInt(500)), game.getMap()));
		}
		for (int i = 0; i < survivor; i++) {
			game.add(new Survivor(new Point(random.nextInt(500), random.nextInt(500)), game.getMap(), names[i], i+1));
			List<Survivor> survivors = game.getSurvivors();
			survivors.get(survivors.size() - 1).setWeapon(new Axe());
			if (i == 0) {
				Magazine magazine = new Magazine();
				magazine.add(2);
				survivors.get(survivors.size() - 1).setGun(new Pistol(magazine));
			}
		}
	}
	
}
