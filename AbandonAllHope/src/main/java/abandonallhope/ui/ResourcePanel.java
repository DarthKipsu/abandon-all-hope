package abandonallhope.ui;

import abandonallhope.domain.Survivor;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.logic.Game;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Left ui panel containing info about survivors and resources.
 *
 * @author kipsu
 */
public class ResourcePanel implements NewSurvivorEventHandler, DeleteSurvivorEventHandler {

	private VBox vbox;
	private VBox survivors;
	private VBox bullets;
	private Game game;

	private ArrayList<Text> survivorNames;
	private ArrayList<ComboBox> survivorWeapons;
	private ArrayList<ComboBox> survivorGuns;

	/**
	 * Creates a new resources panel,containing information about player
	 * resources.
	 *
	 * @param game game containing the resources
	 */
	public ResourcePanel(Game game) {
		this.game = game;
		game.addNewResourceEventHandler(this, "newSurvivor");
		game.addNewResourceEventHandler(this, "deleteSurvivor");
		vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setPrefWidth(200);
		survivors = new VBox();
		survivors.setPrefHeight(350);
		survivorNames = new ArrayList<>();
		survivorWeapons = new ArrayList<>();
		survivorGuns = new ArrayList<>();
		bullets = new VBox();
		createVBoxContents();
	}

	public VBox getVbox() {
		return vbox;
	}

	/**
	 * Update left panel bullet count
	 */
	public void updateBullets() {
		bullets.getChildren().clear();
		bullets.getChildren().add(new Text("  Pistol: "
				+ game.getInventory().getPistolBullets().getBullets()));
	}

	private static String printWeapon(Survivor survivor) {
		return "weapon: " + (survivor.getWeapon() != null ? survivor.getWeapon() : "-");
	}

	private static String printGun(Survivor survivor) {
		return "gun: " + (survivor.getGun() != null ? survivor.getGun() : "-");
	}

	private void createVBoxContents() {
		addTitle("Survivors:");
		vbox.getChildren().add(survivors);
		addBulletInventory();
	}

	private void addTitle(String text) {
		Label title = new Label(text);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);
	}

	private void addBulletInventory() {
		addTitle("Bullets:");
		vbox.getChildren().add(bullets);
		updateBullets();
	}

	@Override
	public void handle(NewSurvivorEvent e) {
		Text name = new Text(e.getSurvivor().getId() + ". " + e.getSurvivor().getName());
		name.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		name.setId(e.getSurvivor().getName());
		survivorNames.add(name);
		survivors.getChildren().add(name);
		addMeleeWeaponComboBox(e);
		addFirearmComboBox(e);
	}

	@Override
	public void handle(DeleteSurvivorEvent e) {
		List<Node> nodesToRemove = new ArrayList<>();
		for (int i = 0; i < survivors.getChildren().size(); i++) {
			Node node = survivors.getChildren().get(i);
			if (node.getId().equals(e.getSurvivor().getName())) {
				nodesToRemove.add(node);
			}
		}
		survivors.getChildren().removeAll(nodesToRemove);
	}

	private void addMeleeWeaponComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		comboBox.getItems().addAll(
				printWeapon(e.getSurvivor())
		);
		comboBox.setValue(printWeapon(e.getSurvivor()));
		comboBox.setPrefWidth(150);
		comboBox.setId(e.getSurvivor().getName());
		survivorWeapons.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

	private void addFirearmComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		comboBox.getItems().addAll(
				printGun(e.getSurvivor())
		);
		comboBox.setValue(printGun(e.getSurvivor()));
		comboBox.setPrefWidth(150);
		comboBox.setId(e.getSurvivor().getName());
		survivorWeapons.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

}
