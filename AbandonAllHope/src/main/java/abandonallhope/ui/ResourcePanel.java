package abandonallhope.ui;

import abandonallhope.domain.*;
import abandonallhope.events.action.*;
import abandonallhope.events.handlers.*;
import abandonallhope.logic.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

/**
 * Left ui panel containing info about survivors and resources.
 */
public class ResourcePanel implements NewSurvivorEventHandler, DeleteSurvivorEventHandler,
		NewWeaponEventHandler, DeleteWeaponEventHandler, NewFirearmEventHandler, DeleteFirearmEventHandler {

	private VBox vbox;
	private VBox survivors;
	private VBox resources;
	private Items items;
	private ResourceEvents resEvents;
	private ArrayList<ComboBox> survivorWeapons;
	private ArrayList<ComboBox> survivorGuns;

	/**
	 * Creates a new resources panel,containing information about player resources.
	 * @param game game containing the resources
	 */
	public ResourcePanel(Game game) {
		items = game.getItems();
		resEvents = game.getTurn().getResourceEvents();
		addEventHandlers(game.getTurn());
		vBoxSetup();
		survivors = new VBox();
		survivors.setPrefHeight(360);
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
		resources.getChildren().add(new Text("      Bullets: " + items.getInventory().getPistolBullets().getBullets()));
		resources.getChildren().add(new Text("      Wood: " + items.getInventory().getWood()));
		resources.getChildren().add(new Text("      Metal: " + items.getInventory().getMetal()));
	}

	@Override
	public void handle(NewSurvivorEvent e) {
		Text name = new Text(e.getSurvivor().getId() + ". " + e.getSurvivor().getName());
		name.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		name.setFill(Color.ANTIQUEWHITE);
		name.setId(e.getSurvivor().getName());
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
		changeLadel(survivorWeapons, "add", e.getWeapon().toString());
	}

	@Override
	public void handle(DeleteWeaponEvent e) {
		changeLadel(survivorWeapons, "delete", e.getWeapon().toString());
	}

	@Override
	public void handle(NewFirearmEvent e) {
		changeLadel(survivorGuns, "add", e.getFirearm().toString());
	}

	@Override
	public void handle(DeleteFirearmEvent e) {
		changeLadel(survivorGuns, "delete", e.getFirearm().toString());
	}

	private void changeLadel(List<ComboBox> cBoxes, String type, String weapon) {
		for (ComboBox combo : cBoxes) {
			String weaponNow = combo.getValue().toString();
			if (!weaponNow.equals(weapon)) {
				if (type.equals("delete")) {
					removeLabels(combo, weapon);
				} else {
					combo.getItems().add(weapon);
				}
			}
		}
	}

	private void addEventHandlers(Turn turn) {
		String[] events = new String[]{"newSurvivor", "deleteSurvivor", "newWeapon", "deleteWeapon",
			"newFirearm", "deleteFirearm"};
		for (int i = 0; i < events.length; i++) {
			turn.addNewResourceEventHandler(this, events[i]);
		}
	}

	private void vBoxSetup() {
		vbox = new VBox();
		vbox.getStyleClass().add("left");
		vbox.setPrefWidth(247);
	}

	private void createVBoxContents() {
		addTitle("Survivors:");
		vbox.getChildren().add(survivors);
		addBulletInventory();
	}

	private void addTitle(String text) {
		Label title = new Label(text);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		title.setTextFill(Color.ANTIQUEWHITE);
		vbox.getChildren().add(title);
	}

	private void addBulletInventory() {
		addTitle("     Resources:");
		vbox.getChildren().add(resources);
		updateResources();
	}

	private void removeLabels(ComboBox combo, String weapon) {
		while (combo.getItems().contains(weapon)) {
			combo.getItems().remove(weapon);
		}
	}

	private void addMeleeWeaponComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		addComboItems(comboBox, printSurvivorWeapon(e.getSurvivor()));
		setCBProperties(comboBox, e, printSurvivorWeapon(e.getSurvivor()));
		comboBox.valueProperty().addListener(new WeaponEvent(items.getInventory(),
				e.getSurvivor(), resEvents));
		survivorWeapons.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

	private void addFirearmComboBox(NewSurvivorEvent e) {
		ComboBox<String> comboBox = new ComboBox();
		addComboItems(comboBox, printSurvivorGun(e.getSurvivor()));
		setCBProperties(comboBox, e, printSurvivorGun(e.getSurvivor()));
		comboBox.valueProperty().addListener(new FirearmEvent(items.getInventory(),
				e.getSurvivor(), resEvents));
		survivorGuns.add(comboBox);
		survivors.getChildren().add(comboBox);
	}

	private void setCBProperties(ComboBox<String> comboBox, NewSurvivorEvent e, String value) {
		comboBox.setValue(value);
		comboBox.setPrefWidth(150);
		comboBox.setId(e.getSurvivor().getName());
	}

	private void addComboItems(ComboBox<String> comboBox, String item) {
		comboBox.getItems().add(item);
		if (!item.equals("none")) {
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