
package abandonallhope.logic;

import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.weapons.Bullet;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.events.handlers.ResourceEventHandler;
import abandonallhope.logic.loot.LootDistributor;
import abandonallhope.ui.MessagePanel;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains logic for what happens during a single game turn
 */
public class Turn {
	
	private Items items;
	private LootDistributor lootDistributor;
	private ResourceEvents resourceEvents;

	/**
	 * Creates a new turn object
	 * @param items 
	 */
	public Turn(Items items) {
		this.items = items;
		resourceEvents = new ResourceEvents();
		lootDistributor = new LootDistributor(resourceEvents);
	}

	public LootDistributor getLootDistributor() {
		return lootDistributor;
	}

	public ResourceEvents getResourceEvents() {
		return resourceEvents;
	}
	
	/**
	 * Play game for one turn
	 */
	public void play() {
		lootDistributor.setInventory(items.getInventory());
		handleBullets();
		moveSurvivors();
		if (!items.getZombies().isEmpty()) {
			moveZombies();
			fightZombies();
			infectSurvivors();
		}
	}

	/**
	 * Adds a new resource event handler in game.
	 * @param event event handler to be added.
	 * @param type type of the event: newSurvivor, deleteSurvivor
	 */
	public void addNewResourceEventHandler(ResourceEventHandler event, String type) {
		resourceEvents.addNewResourceEventHandler(event, type);
	}

	protected void handleBullets() {
		List<Bullet> bulletsToRemove = new ArrayList<>();
		for (Bullet bullet : items.getBullets()) {
			checkHitsAndMoveBullets(bullet, bulletsToRemove);
		}
		items.getBullets().removeAll(bulletsToRemove);
	}

	private void checkHitsAndMoveBullets(Bullet bullet, List<Bullet> bulletsToRemove) {
		Zombie zombie = (Zombie) Collision.hitTest(bullet, items.getZombies());
		if (zombie != null) {
			removeZombieAndBullet(zombie, bulletsToRemove, bullet);
		} else if (bullet.hasReachedMaxDistance()) {
			bulletsToRemove.add(bullet);
		} else {
			bullet.move();
		}
	}

	private void removeZombieAndBullet(Zombie zombie, List<Bullet> bulletsToRemove, Bullet bullet) {
		killAZombie(zombie);
		bulletsToRemove.add(bullet);
	}

	protected void killAZombie(Zombie target) {
		items.getZombies().remove(target);
		MessagePanel.addMessage("Zombie dropped " + lootDistributor.getLoot());
	}

	protected void moveSurvivors() {
		for (Survivor survivor : items.getSurvivors()) {
			survivor.move();
		}
	}

	protected void moveZombies() {
		for (Zombie zombie : items.getZombies()) {
			zombie.move();
		}
	}

	protected void fightZombies() {
		for (Survivor survivor : items.getSurvivors()) {
			fightNearestZombieIfPossible(survivor);
		}
	}

	private void fightNearestZombieIfPossible(Survivor survivor) {
		if (survivor.getGun() != null && survivor.getGun().canBeUsed()) {
			useFirearmWeapon(survivor);
		} else if (survivor.getWeapon() != null) {
			useMeleeWeapon(survivor);
		}
		decreaseGunRoundsToUse(survivor);
	}

	private void useFirearmWeapon(Survivor survivor) {
		Firearm gun = survivor.getGun();
		MovingObject target = Collision.nearestPerson(survivor, items.getZombies());
		if (target == null) {
			return;
		}
		fireGunIfCloseEnoughToTarget(gun, survivor, target.getLocation());
	}

	private void fireGunIfCloseEnoughToTarget(Firearm gun, Survivor survivor, Point destination) {
		if (Collision.distanceBetween(survivor.getLocation(), destination) <= gun.getRange() * 1.2) {
			gun.use();
			Bullet newBullet = new Bullet(new Point(survivor.getLocation().x, survivor.getLocation().y),
					items.getMap(), destination, (int) gun.getRange());
			items.getBullets().add(newBullet);
		}
	}

	private void useMeleeWeapon(Survivor survivor) {
		Weapon weapon = survivor.getWeapon();
		MovingObject target = Collision.nearestPerson(survivor, items.getZombies());
		if (target == null) {
			return;
		}
		useWeapon(weapon, survivor, target);
	}

	private void useWeapon(Weapon weapon, Survivor survivor, MovingObject target) {
		if (weaponCanBeUsed(weapon, survivor.getLocation(), target.getLocation())) {
			weapon.use();
			killAZombie((Zombie) target);
		} else {
			weapon.decreaseRoundsToUse();
		}
	}

	private static boolean weaponCanBeUsed(Weapon weapon, Point survivor, Point target) {
		return weapon.canBeUsed() &&
				Collision.distanceBetween(survivor, target) <=
				weapon.getRange();
	}

	private void decreaseGunRoundsToUse(Survivor survivor) {
		if (survivor.getGun() != null) {
			survivor.getGun().decreaseRoundsToUse();
		}
	}

	protected void infectSurvivors() {
		for (Zombie zombie : items.getZombies()) {
			Survivor survivor = (Survivor) Collision.hitTest(zombie, items.getSurvivors());
			if (survivor != null) {
				Point survivorLocation = survivor.getLocation();
				MessagePanel.addMessage(survivor.getName() + " was bit and turned into a zombie!");
				resourceEvents.triggerDeleteSurvivorEvent(survivor);
				items.getSurvivors().remove(survivor);
				items.add(new Zombie(survivorLocation, items.getMap(), items.getZombies()));
				return;
			}
		}
	}
	
}
