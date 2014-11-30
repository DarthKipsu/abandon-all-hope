package abandonallhope.ui;

import abandonallhope.domain.Survivor;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.logic.Game;
import java.util.ArrayList;
import javafx.geometry.Insets;
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
public class ResourcePanel implements NewSurvivorEventHandler {

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
		game.addNewSurvivorEventHandler(this);
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
		survivorNames.add(name);
		survivors.getChildren().add(name);
		addMeleeWeaponComboBox(e);
		addFirearmComboBox(e);
	}

	private void addMeleeWeaponComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		comboBox.getItems().addAll(
				printWeapon(e.getSurvivor())
		);
		comboBox.setValue(printWeapon(e.getSurvivor()));
		comboBox.setPrefWidth(150);
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
		survivorWeapons.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

}
