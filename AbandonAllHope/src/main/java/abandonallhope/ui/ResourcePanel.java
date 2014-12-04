package abandonallhope.ui;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.action.NewWeaponEvent;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.events.handlers.NewWeaponEventHandler;
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
public class ResourcePanel implements NewSurvivorEventHandler, DeleteSurvivorEventHandler,
		NewWeaponEventHandler {

	private VBox vbox;
	private VBox survivors;
	private VBox bullets;
	private Inventory inventory;
	

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
		game.addNewResourceEventHandler(this, "newSurvivor");
		game.addNewResourceEventHandler(this, "deleteSurvivor");
		game.addNewResourceEventHandler(this, "newWeapon");
		inventory = game.getInventory();
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
				+ inventory.getPistolBullets().getBullets()));
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
		for (Node node : survivors.getChildren()) {
			if (node.getId().equals(e.getSurvivor().getName())) {
				nodesToRemove.add(node);
			}
		}
		survivors.getChildren().removeAll(nodesToRemove);
	}

	@Override
	public void handle(NewWeaponEvent e) {
		for (ComboBox combo : survivorWeapons) {
			String weaponNow = combo.getItems().get(0).toString().substring(8);
			if (!weaponNow.equals(e.getWeapon().toString())) {
				combo.getItems().add(e.getWeapon().toString());
			}
		}
	}

	private void addMeleeWeaponComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		comboBox.getItems().addAll(printWeapons(e.getSurvivor()));
		comboBox.setValue(printWeapon(e.getSurvivor()));
		comboBox.setPrefWidth(150);
		comboBox.setId(e.getSurvivor().getName());
		survivorWeapons.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

	private void addFirearmComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		comboBox.getItems().addAll(printGuns(e.getSurvivor()));
		comboBox.setValue(printGun(e.getSurvivor()));
		comboBox.setPrefWidth(150);
		comboBox.setId(e.getSurvivor().getName());
		survivorGuns.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

	private String printWeapons(Survivor survivor) {
		String weapons = printWeapon(survivor);
		if (!inventory.getWeapons().isEmpty()) {
			weapons += ", " + inventory.getWeapons().get(0);   
		}
		return weapons;
	}

	private String printWeapon(Survivor survivor) {
		return "weapon: " + (survivor.getWeapon() != null ? survivor.getWeapon() : "-");
	}

	private String printGuns(Survivor survivor) {
		String guns = printGun(survivor);
		if (!inventory.getGuns().isEmpty()) {
			guns += ", " + inventory.getGuns().get(0);   
		}
		return guns;
	}

	private String printGun(Survivor survivor) {
		return "gun: " + (survivor.getGun() != null ? survivor.getGun() : "-");
	}

}
