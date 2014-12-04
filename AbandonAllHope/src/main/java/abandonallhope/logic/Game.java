package abandonallhope.logic;

import abandonallhope.domain.*;
import abandonallhope.domain.constructions.*;
import abandonallhope.domain.weapons.*;
import abandonallhope.events.handlers.ResourceEventHandler;
import abandonallhope.logic.loot.LootDistributor;
import abandonallhope.ui.MessagePanel;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * The events taking place in single gameTimeline frame (from UserInterface)
 *
 * @author kipsu
 */
public class Game implements EventHandler {

	private Map map;
	private Inventory inventory;
	private LootDistributor lootDistributor;
	private MessagePanel messages;
	private Timeline gameTimeline;
	private ResourceEvents resourceEvents;
	private int day;
	private int sleep;

	private List<Zombie> zombies;
	private List<Survivor> survivors;
	private List<Bullet> bullets;
	private List<Wall> walls;
	private List<Trap> traps;

	/**
	 * Create new game event handler
	 *
	 * @param mapSize
	 */
	public Game(int mapSize) {
		inventory = new Inventory();
		lootDistributor = new LootDistributor(inventory);
		resourceEvents = new ResourceEvents();
		zombies = new ArrayList<>();
		survivors = new ArrayList<>();
		bullets = new ArrayList<>();
		walls = new ArrayList<>();
		traps = new ArrayList<>();
		map = new Map(mapSize, survivors, walls, traps);
		day = 1;
		sleep = 0;
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

	public List<Wall> getWalls() {
		return walls;
	}

	public List<Trap> getTraps() {
		return traps;
	}

	public Map getMap() {
		return map;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public int getDay() {
		return day;
	}

	/**
	 * Add one or more survivors to the game
	 *
	 * @param survivors
	 */
	public void add(Survivor... survivors) {
		for (Survivor survivor : survivors) {
			this.survivors.add(survivor);
			resourceEvents.triggerNewSurvivorEvent(survivor);
		}
	}

	/**
	 * add one or more zombies to the game
	 *
	 * @param zombies
	 */
	public void add(Zombie... zombies) {
		for (Zombie zombie : zombies) {
			this.zombies.add(zombie);
		}
	}

	/**
	 * Add one or more walls to the game.
	 *
	 * @param walls
	 */
	public void add(Wall... walls) {
		for (Wall wall : walls) {
			this.walls.add(wall);
		}
	}

	/**
	 * Add one or more traps to the game.
	 *
	 * @param traps
	 */
	public void add(Trap... traps) {
		for (Trap trap : traps) {
			this.traps.add(trap);
		}
	}

	public void setMessages(MessagePanel messages) {
		this.messages = messages;
	}

	public void setGameTimeline(Timeline gameTimeline) {
		this.gameTimeline = gameTimeline;
	}
	
	/**
	 * Adds a new resource event handler in game.
	 * @param event event handler to be added.
	 * @param type type of the event: newSurvivor, deleteSurvivor
	 */
	public void addNewResourceEventHandler(ResourceEventHandler event, String type) {
		resourceEvents.addNewResourceEventHandler(event, type);
	}

	/**
	 * Handle game event: move survivors and zombies, fight zombies and infect
	 * survivors.
	 *
	 * @param t
	 */
	@Override
	public void handle(Event t) {
		if (sleep > 0) {
			sleepUntilTheNextDay();
		} else if (survivors.isEmpty()) {
			gameOver();
		} else if (zombiesCleared()) {
			endTheCurrentDay();
		} else {
			playATurn();
		}
	}

	private void sleepUntilTheNextDay() {
		if (sleep == 1) {
			DayChanger.nextDay();
			messages.addMessage("Begin day " + day + ": " + zombies.size() + " new zombies.");
		}
		sleep--;
	}

	private void gameOver() {
		gameTimeline.stop();
		messages.addMessage("All survivors are lost! Game over!");
	}

	private void endTheCurrentDay() {
		for (Zombie zombie : zombies) {
			lootDistributor.getLoot();
		}
		zombies.clear();
		day++;
		messages.addMessage("All zombies cleared and trapped loot collected. You managed to survive another day!");
		messages.addMessage("Prepare for day " + day);
		sleep = 180;
	}

	private boolean zombiesCleared() {
		for (Zombie zombie : zombies) {
			if (!zombie.isTrapped()) {
				return false;
			}
		}
		return true;
	}

	private void playATurn() {
		handleBullets();
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

	protected void handleBullets() {
		List<Bullet> bulletsToRemove = new ArrayList<>();
		for (Bullet bullet : bullets) {
			checkHitsAndMoveBullets(bullet, bulletsToRemove);
		}
		bullets.removeAll(bulletsToRemove);
	}

	private void checkHitsAndMoveBullets(Bullet bullet, List<Bullet> bulletsToRemove) {
		Zombie zombie = (Zombie) Collision.hitTest(bullet, zombies);
		if (zombie != null) {
			removeZombieAndBullet(zombie, bulletsToRemove, bullet);
		} else if (bullet.hasReachedMaxDistance()) {
			bulletsToRemove.add(bullet);
		} else {
			bullet.move();
		}
	}

	private void removeZombieAndBullet(Zombie zombie, List<Bullet> bulletsToRemove, Bullet bullet) {
		Point targetLocation = zombie.getLocation();
		killAZombie(zombie);
		bulletsToRemove.add(bullet);
	}

	protected void fightZombies() {
		for (Survivor survivor : survivors) {
			fightNearestZombieIfPossible(survivor);
		}
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
		if (target == null) {
			return;
		}
		fireGunIfCloseEnoughToTarget(gun, survivor, target.getLocation());
	}

	private void useBasicWeapon(Survivor survivor) {
		Weapon weapon = survivor.getWeapon();
		MovingObject target = Collision.nearestPerson(survivor, zombies);
		if (target == null) {
			return;
		}
		useWeapon(weapon, survivor, target);
	}

	private void decreaseGunRoundsToUse(Survivor survivor) {
		if (survivor.getGun() != null) {
			survivor.getGun().decreaseRoundsToUse();
		}
	}

	protected void infectSurvivors() {
		for (Zombie zombie : zombies) {
			Survivor survivor = (Survivor) Collision.hitTest(zombie, map.getSurvivors());
			if (survivor != null) {
				Point survivorLocation = survivor.getLocation();
				messages.addMessage(survivor.getName() + " was bit and turned into a zombie!");
				resourceEvents.triggerDeleteSurvivorEvent(survivor);
				survivors.remove(survivor);
				add(new Zombie(survivorLocation, map));
				return;
			}
		}
	}

	private void fireGunIfCloseEnoughToTarget(Firearm gun, Survivor survivor, Point destination) {
		if (Collision.distanceBetween(survivor.getLocation(), destination) <= gun.getRange() * 1.2) {
			gun.use();
			Bullet newBullet = new Bullet(new Point(survivor.getLocation().x, survivor.getLocation().y),
					map, destination, (int) gun.getRange());
			bullets.add(newBullet);
		}
	}

	private void useWeapon(Weapon weapon, Survivor survivor, MovingObject target) {
		if (weaponCanBeUsed(weapon, survivor.getLocation(), target.getLocation())) {
			weapon.use();
			killAZombie(target);
		} else {
			weapon.decreaseRoundsToUse();
		}
	}

	private void killAZombie(MovingObject target) {
		zombies.remove(target);
		messages.addMessage("Zombie dropped " + lootDistributor.getLoot());
	}

	private static boolean weaponCanBeUsed(Weapon weapon, Point survivor, Point target) {
		return weapon.canBeUsed()
				&& Collision.distanceBetween(survivor, target)
				<= weapon.getRange();
	}

}
