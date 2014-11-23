package abandonallhope.ui;

import abandonallhope.domain.Point;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.domain.constructions.WoodenWall;
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
		Label title = new Label("Build a wall:");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		Button woodenWall = new Button("Wooden");
		woodenWall.setPrefSize(100, 20);
		woodenWall.setOnAction(new WallEvent(canvas, game,
				WallEvent.WallType.WOODEN));

		vbox.getChildren().addAll(title, woodenWall);
	}
}
