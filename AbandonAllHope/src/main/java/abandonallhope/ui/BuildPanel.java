package abandonallhope.ui;

import abandonallhope.domain.constructions.TrapType;
import abandonallhope.domain.constructions.WallType;
import abandonallhope.events.action.DeselectEvent;
import abandonallhope.events.action.TrapEvent;
import abandonallhope.events.action.WallEvent;
import abandonallhope.logic.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
		vbox.getStyleClass().add("right");
		vbox.setPrefWidth(252);
		this.game = game;
		this.canvas = canvas;
		createVBoxContent();
	}

	public VBox getVbox() {
		return vbox;
	}

	private void createVBoxContent() {
		addDeselectionButton();
		
		addTitle("Build a wall:");
		addWallButton(WallType.WOODEN, "wall-wooden");
		
		addTitle("Build a trap:");
		addTrapButton(TrapType.BEARIRON, "trap-beariron");
		addTrapButton(TrapType.PIT, "trap-pit");
	}
	
	private void addDeselectionButton() {
		Button deselect = new Button("Uselect all");
		deselect.setPrefSize(100, 20);
		deselect.setOnAction(new DeselectEvent(canvas, game));
		vbox.getChildren().add(deselect);
	}

	private void addTitle(String text) {
		Label title = new Label(text);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		title.setTextFill(Color.ANTIQUEWHITE);
		vbox.getChildren().add(title);
	}

	private void addWallButton(WallType wallType, String wallCss) {
		Button wall = new Button();
		wall.setId(wallCss);
		wall.setPrefSize(80, 30);
		wall.setOnAction(new WallEvent(canvas, game, wallType));
		vbox.getChildren().add(wall);
	}

	private void addTrapButton(TrapType trapType, String trapCss) {
		Button trap = new Button();
		trap.setId(trapCss);
		trap.setPrefSize(80, 30);
		trap.setOnAction(new TrapEvent(canvas, game, trapType));
		vbox.getChildren().add(trap);
	}
}
