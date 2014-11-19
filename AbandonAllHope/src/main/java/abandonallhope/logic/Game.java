package abandonallhope.logic;

import abandonallhope.domain.Map;
import abandonallhope.domain.Person;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.weapons.Weapon;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Game implements EventHandler {

	private Map map;
	private List<Zombie> zombies;
	private List<Survivor> survivors;

	public Game(int mapSize) {
		zombies = new ArrayList<>();
		survivors = new ArrayList<>();
		map = new Map(mapSize, mapSize, survivors);
	}

	public List<Survivor> getSurvivors() {
		return survivors;
	}

	public List<Zombie> getZombies() {
		return zombies;
	}

	public Map getMap() {
		return map;
	}

	public void add(Survivor... survivors) {
		for (Survivor survivor : survivors) {
			this.survivors.add(survivor);
		}
	}

	public void add(Zombie... zombies) {
		for (Zombie zombie : zombies) {
			this.zombies.add(zombie);
		}
	}

	@Override
	public void handle(Event t) {
		moveSurvivors();
		if (!zombies.isEmpty()) {
			moveZombies();
			fightZombies();
			infectSurvivors();
		}
	}

	protected void moveSurvivors() {
		for (Survivor survivor : survivors) {
			survivor.move();
		}
	}

	protected void moveZombies() {
		for (Zombie zombie : zombies) {
			zombie.move();
		}
	}

	protected void fightZombies() {
		for (Survivor survivor : survivors) {
			if (survivor.getWeapon() != null) {
				Weapon weapon = survivor.getWeapon();
				Person target = Collision.nearestPerson(survivor, zombies);
				useWeapon(weapon, survivor, target);
			}
		}
	}

	protected void infectSurvivors() {
		for (Zombie zombie : zombies) {
			Survivor survivor = Collision.survivor(zombie, map.getSurvivors());
			if (survivor != null) {
				Point survivorLocation = survivor.getLocation();
				survivors.remove(survivor);
				add(new Zombie(survivorLocation, map));
				return;
			}
		}
	}

	private void useWeapon(Weapon weapon, Survivor survivor, Person target) {
		if (weaponCanBeUsed(weapon, survivor.getLocation(), target.getLocation())) {
			weapon.use();
			zombies.remove(target);
		} else {
			weapon.decreaseRoundsToUse();
		}
	}

	private static boolean weaponCanBeUsed(Weapon weapon, Point survivor, Point target) {
		return weapon.canBeUsed() &&
				Collision.distanceBetween(survivor, target) <=
				weapon.getRange();
	}

}
