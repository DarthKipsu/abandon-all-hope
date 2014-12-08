package abandonallhope.ui;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Survivor;
import abandonallhope.events.action.*;
import abandonallhope.events.handlers.*;
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
		NewWeaponEventHandler, DeleteWeaponEventHandler, NewFirearmEventHandler,
		DeleteFirearmEventHandler {

	private VBox vbox;
	private VBox survivors;
	private VBox resources;
	private Inventory inventory;
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
		game.addNewResourceEventHandler(this, "newSurvivor");
		game.addNewResourceEventHandler(this, "deleteSurvivor");
		game.addNewResourceEventHandler(this, "newWeapon");
		game.addNewResourceEventHandler(this, "deleteWeapon");
		game.addNewResourceEventHandler(this, "newFirearm");
		game.addNewResourceEventHandler(this, "deleteFirearm");
		this.game = game;
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
		resources = new VBox();
		createVBoxContents();
	}

	public VBox getVbox() {
		return vbox;
	}

	/**
	 * Update left panel bullet and material counts
	 */
	public void updateResources() {
		resources.getChildren().clear();
		resources.getChildren().add(new Text("  Bullets: "
				+ inventory.getPistolBullets().getBullets()));
		resources.getChildren().add(new Text("  Wood: "
				+ inventory.getWood()));
		resources.getChildren().add(new Text("  Metal: "
				+ inventory.getMetal()));
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
		addTitle("Resources:");
		vbox.getChildren().add(resources);
		updateResources();
	}

	@Override
	public void handle(NewSurvivorEvent e) {
		Text name = new Text(e.getSurvivor().getId() + ". " + e.getSurvivor().getName());
		name.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		name.setId(e.getSurvivor().getName());
		survivorNames.add(name);
		survivors.getChildren().add(name);
		if (e.getSurvivor().getId() != 0) {
			addMeleeWeaponComboBox(e);
			addFirearmComboBox(e);
		}
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
			String weaponNow = combo.getValue().toString();
			if (!weaponNow.equals(e.getWeapon().toString())) {
				combo.getItems().add(e.getWeapon().toString());
			}
		}
	}

	@Override
	public void handle(DeleteWeaponEvent e) {
		for (ComboBox combo : survivorWeapons) {
			String weaponNow = combo.getValue().toString();
			if (!weaponNow.equals(e.getWeapon().toString())) {
				removeWeaponLabels(combo, e);
			}
		}
	}

	@Override
	public void handle(NewFirearmEvent e) {
		for (ComboBox combo : survivorGuns) {
			String gunNow = combo.getValue().toString();
			if (!gunNow.equals(e.getFirearm().toString())) {
				combo.getItems().add(e.getFirearm().toString());
			}
		}
	}

	@Override
	public void handle(DeleteFirearmEvent e) {
		for (ComboBox combo : survivorGuns) {
			String gunNow = combo.getValue().toString();
			if (!gunNow.equals(e.getFirearm().toString())) {
				removeFirearmLabels(combo, e);
			}
		}
	}

	private void removeWeaponLabels(ComboBox combo, DeleteWeaponEvent e) {
		while (combo.getItems().contains(e.getWeapon().toString())) {
			combo.getItems().remove(e.getWeapon().toString());
		}
	}

	private void removeFirearmLabels(ComboBox combo, DeleteFirearmEvent e) {
		while (combo.getItems().contains(e.getFirearm().toString())) {
			combo.getItems().remove(e.getFirearm().toString());
		}
	}

	private void addMeleeWeaponComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		String survivorWeapon = printSurvivorWeapon(e.getSurvivor());
		addCBWeapons(comboBox, survivorWeapon);
		comboBox.setValue(printSurvivorWeapon(e.getSurvivor()));
		setCBProperties(comboBox, e);
		comboBox.valueProperty().addListener(new WeaponEvent(inventory,
				e.getSurvivor(), game.getResourceEvents()));
		survivorWeapons.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

	private void addFirearmComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		String survivorGun = printSurvivorGun(e.getSurvivor());
		addCBGuns(comboBox, survivorGun);
		comboBox.setValue(printSurvivorGun(e.getSurvivor()));
		setCBProperties(comboBox, e);
		comboBox.valueProperty().addListener(new FirearmEvent(inventory,
				e.getSurvivor(), game.getResourceEvents()));
		survivorGuns.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

	private void setCBProperties(ComboBox<String> comboBox, NewSurvivorEvent e) {
		comboBox.setPrefWidth(150);
		comboBox.setId(e.getSurvivor().getName());
	}

	private void addCBWeapons(ComboBox<String> comboBox, String survivorWeapon) {
		comboBox.getItems().add(survivorWeapon);
		if (!survivorWeapon.equals("none")) {
			comboBox.getItems().add("none");
		}
	}

	private void addCBGuns(ComboBox<String> comboBox, String SurvivorGun) {
		comboBox.getItems().add(SurvivorGun);
		if (!SurvivorGun.equals("none")) {
			comboBox.getItems().add("none");
		}
	}

	private String printSurvivorWeapon(Survivor survivor) {
		return survivor.getWeapon() != null ? survivor.getWeapon().toString() : "none";
	}

	private String printSurvivorGun(Survivor survivor) {
		return survivor.getGun() != null ? survivor.getGun().toString() : "none";
	}

}
