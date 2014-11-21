package abandonallhope.logic;

import abandonallhope.domain.Map;
import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.weapons.Bullet;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Weapon;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * The events taking place in single gameTimeline frame (from UserInterface)
 * @author kipsu
 */
public class Game implements EventHandler {

	private Map map;
	private List<Zombie> zombies;
	private List<Survivor> survivors;
	private List<Bullet> bullets;

	/**
	 * Create new game event handler
	 * @param mapSize 
	 */
	public Game(int mapSize) {
		zombies = new ArrayList<>();
		survivors = new ArrayList<>();
		bullets = new ArrayList<>();
		map = new Map(mapSize, mapSize, survivors);
	}

	public List<Survivor> getSurvivors() {
		return survivors;
	}

	public List<Zombie> getZombies() {
		return zombies;
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

	public Map getMap() {
		return map;
	}

	/**
	 * Add one or more survivors to the game
	 * @param survivors 
	 */
	public void add(Survivor... survivors) {
		for (Survivor survivor : survivors) {
			this.survivors.add(survivor);
		}
	}

	/**
	 * add one or more zombies to the game
	 * @param zombies 
	 */
	public void add(Zombie... zombies) {
		for (Zombie zombie : zombies) {
			this.zombies.add(zombie);
		}
	}

	/**
	 * Handle game event: move survivors and zombies, fight zombies and 
	 * infect survivors.
	 * @param t 
	 */
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
			fightNearestZombieIfPossible(survivor);
		}
		handleBullets();
	}

	private void fightNearestZombieIfPossible(Survivor survivor) {
		if (survivor.getGun() != null && survivor.getGun().canBeUsed()) {
			useFirearmWeapon(survivor);
		} else if (survivor.getWeapon() != null) {
			useBasicWeapon(survivor);
		}
		decreaseGunRoundsToUse(survivor);
	}

	private void useFirearmWeapon(Survivor survivor) {
		Firearm gun = survivor.getGun();
		MovingObject target = Collision.nearestPerson(survivor, zombies);
		fireGunIfCloseEnoughToTarget(gun, survivor, target.getLocation());
	}

	private void useBasicWeapon(Survivor survivor) {
		Weapon weapon = survivor.getWeapon();
		MovingObject target = Collision.nearestPerson(survivor, zombies);
		useWeapon(weapon, survivor, target);
	}

	private void decreaseGunRoundsToUse(Survivor survivor) {
		if (survivor.getGun() != null) {
			survivor.getGun().decreaseRoundsToUse();
		}
	}

	private void handleBullets() {
		List<Bullet> bulletsToRemove = new ArrayList<>();
		for (Bullet bullet : bullets) {
			if (bullet.hasReachedMaxDistance()) {
				bulletsToRemove.add(bullet);
			} else {
				bullet.move();
			}
		}
		bullets.removeAll(bulletsToRemove);
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
	
	private void fireGunIfCloseEnoughToTarget(Firearm gun, Survivor survivor, Point destination) {
		if (Collision.distanceBetween(survivor.getLocation(), destination) <= gun.getRange()*1.2) {
			gun.use();
			Bullet newBullet = new Bullet(new Point(survivor.getLocation().x, survivor.getLocation().y), map, destination, (int)gun.getRange());
			bullets.add(newBullet);
		}
	}

	private void useWeapon(Weapon weapon, Survivor survivor, MovingObject target) {
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
