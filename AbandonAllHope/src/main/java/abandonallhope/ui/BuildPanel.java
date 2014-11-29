package abandonallhope.ui;

import abandonallhope.domain.constructions.TrapType;
import abandonallhope.domain.constructions.WallType;
import abandonallhope.events.action.TrapEvent;
import abandonallhope.events.action.WallEvent;
import abandonallhope.logic.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Right ui panel containing objects about building and weapons.
 *
 * @author kipsu
 */
public class BuildPanel {

	private VBox vbox;
	private Game game;
	private GameCanvas canvas;

	/**
	 * Creates a new building and weapons panel, displaying building types and
	 * available weapons.
	 */
	public BuildPanel(Game game, GameCanvas canvas) {
		vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		this.game = game;
		this.canvas = canvas;
		createVBoxContent();
	}

	public VBox getVbox() {
		return vbox;
	}

	private void createVBoxContent() {
		addTitle("Build a wall:");
		addWallButton("Wooden", WallType.WOODEN);
		
		addTitle("Build a trap:");
		addTrapButton("Bear Iron", TrapType.BEARIRON);
	}

	private void addTitle(String text) {
		Label title = new Label(text);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);
	}

	private void addWallButton(String name, WallType wallType) {
		Button wall = new Button(name);
		wall.setPrefSize(100, 20);
		wall.setOnAction(new WallEvent(canvas, game, wallType));
		vbox.getChildren().add(wall);
	}

	private void addTrapButton(String name, TrapType trapType) {
		Button trap = new Button(name);
		trap.setPrefSize(100, 20);
		trap.setOnAction(new TrapEvent(canvas, game, trapType));
		vbox.getChildren().add(trap);
	}
}
