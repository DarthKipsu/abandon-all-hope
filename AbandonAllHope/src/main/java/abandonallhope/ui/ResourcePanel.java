
package abandonallhope.ui;

import abandonallhope.domain.Survivor;
import abandonallhope.logic.Game;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Left ui panel containing info about survivors and resources.
 * @author kipsu
 */
public class ResourcePanel {
	
	private VBox vbox;
	private VBox survivors;
	private Game game;

	/**
	 * Creates a new resources panel,containing information about player
	 * resources.
	 * @param game game containing the resources
	 */
	public ResourcePanel(Game game) {
		this.game = game;
		vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setPrefWidth(200);
		survivors = new VBox();
		createVBoxContents();
	}

	public VBox getVbox() {
		return vbox;
	}
	
	public void updateSurvivors() {
		survivors.getChildren().clear();
		for (Survivor survivor : game.getSurvivors()) {
			printSurvivorInfo(survivor);
		}
	}

	private void printSurvivorInfo(Survivor survivor) {
		Text name = new Text(survivor.getId() + ". " + survivor.getName());
		name.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		this.survivors.getChildren().add(name);
		this.survivors.getChildren().add(new Text(printWeapon(survivor)));
		this.survivors.getChildren().add(new Text(printGun(survivor)));
	}

	private static String printWeapon(Survivor survivor) {
		return "  weapon: " + (survivor.getWeapon() != null ? survivor.getWeapon() : "-");
	}

	private static String printGun(Survivor survivor) {
		return "  gun: " + (survivor.getGun() != null ? survivor.getGun() : "-");
	}
	
	private void createVBoxContents() {
		addTitle("Survivors:");
		vbox.getChildren().add(survivors);
	}

	private void addTitle(String text) {
		Label title = new Label(text);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);
	}
	
}
