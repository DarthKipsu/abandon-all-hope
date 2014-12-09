
package abandonallhope.logic;

import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.weapons.Bullet;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Weapon;
import abandonallhope.logic.loot.LootDistributor;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains logic for what happens during a single game turn
 */
public class Turn {
	
	private Game game;
	private LootDistributor lootDistributor;
	private ResourceEvents resourceEvents;

	/**
	 * Creates a new turn object
	 * @param game 
	 */
	public Turn(Game game) {
		this.game = game;
		lootDistributor = new LootDistributor(game.getInventory(), game.getResourceEvents());
		resourceEvents = game.getResourceEvents();
	}

	public LootDistributor getLootDistributor() {
		return lootDistributor;
	}
	
	/**
	 * Play game for one turn
	 */
	public void play() {
		handleBullets();
		moveSurvivors();
		if (!game.getZombies().isEmpty()) {
			moveZombies();
			fightZombies();
			infectSurvivors();
		}
	}

	protected void handleBullets() {
		List<Bullet> bulletsToRemove = new ArrayList<>();
		for (Bullet bullet : game.getBullets()) {
			checkHitsAndMoveBullets(bullet, bulletsToRemove);
		}
		game.getBullets().removeAll(bulletsToRemove);
	}

	private void checkHitsAndMoveBullets(Bullet bullet, List<Bullet> bulletsToRemove) {
		Zombie zombie = (Zombie) Collision.hitTest(bullet, game.getZombies());
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
		game.getZombies().remove(target);
		game.getMessages().addMessage("Zombie dropped " + lootDistributor.getLoot());
	}

	protected void moveSurvivors() {
		for (Survivor survivor : game.getSurvivors()) {
			survivor.move();
		}
	}

	protected void moveZombies() {
		for (Zombie zombie : game.getZombies()) {
			zombie.move();
		}
	}

	protected void fightZombies() {
		for (Survivor survivor : game.getSurvivors()) {
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
		MovingObject target = Collision.nearestPerson(survivor, game.getZombies());
		if (target == null) {
			return;
		}
		fireGunIfCloseEnoughToTarget(gun, survivor, target.getLocation());
	}

	private void fireGunIfCloseEnoughToTarget(Firearm gun, Survivor survivor, Point destination) {
		if (Collision.distanceBetween(survivor.getLocation(), destination) <= gun.getRange() * 1.2) {
			gun.use();
			Bullet newBullet = new Bullet(new Point(survivor.getLocation().x, survivor.getLocation().y),
					game.getMap(), destination, (int) gun.getRange());
			game.getBullets().add(newBullet);
		}
	}

	private void useMeleeWeapon(Survivor survivor) {
		Weapon weapon = survivor.getWeapon();
		MovingObject target = Collision.nearestPerson(survivor, game.getZombies());
		if (target == null) {
			return;
		}
		useWeapon(weapon, survivor, target);
	}

	private void useWeapon(Weapon weapon, Survivor survivor, MovingObject target) {
		if (weaponCanBeUsed(weapon, survivor.getLocation(), target.getLocation())) {
			weapon.use();
			killAZombie((Zombie)target);
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
		for (Zombie zombie : game.getZombies()) {
			Survivor survivor = (Survivor) Collision.hitTest(zombie, game.getSurvivors());
			if (survivor != null) {
				Point survivorLocation = survivor.getLocation();
				game.getMessages().addMessage(survivor.getName() + " was bit and turned into a zombie!");
				resourceEvents.triggerDeleteSurvivorEvent(survivor);
				game.getSurvivors().remove(survivor);
				game.add(new Zombie(survivorLocation, game.getMap(), game.getZombies()));
				return;
			}
		}
	}
	
}
