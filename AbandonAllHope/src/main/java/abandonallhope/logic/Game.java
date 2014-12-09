package abandonallhope.logic;

import abandonallhope.domain.*;
import abandonallhope.domain.Map;
import abandonallhope.domain.constructions.*;
import abandonallhope.domain.weapons.*;
import abandonallhope.events.handlers.ResourceEventHandler;
import abandonallhope.ui.MessagePanel;
import java.util.*;
import javafx.animation.*;
import javafx.event.*;

/**
 * Contains game objects and handles game timeline
 */
public class Game implements EventHandler {

	private Map map;
	private Inventory inventory;
	protected MessagePanel messages;
	protected Timeline gameTimeline;
	private ResourceEvents resourceEvents;
	private Turn turn;
	protected int sleep;

	private List<Zombie> zombies;
	private List<Survivor> survivors;
	private List<Bullet> bullets;
	private List<Wall> walls;
	private List<Trap> traps;

	/**
	 * Create new game event handler
	 * @param mapSize
	 */
	public Game(int mapSize) {
		inventory = new Inventory();
		resourceEvents = new ResourceEvents();
		turn = new Turn(this);
		zombies = new ArrayList<>();
		survivors = new ArrayList<>();
		bullets = new ArrayList<>();
		walls = new ArrayList<>();
		traps = new ArrayList<>();
		map = new Map(mapSize, this);
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

	public Turn getTurn() {
		return turn;
	}

	public MessagePanel getMessages() {
		return messages;
	}

	public ResourceEvents getResourceEvents() {
		return resourceEvents;
	}

	/**
	 * Add one survivor to the game
	 * @param survivor
	 */
	public void add(Survivor survivor) {
		this.survivors.add(survivor);
		resourceEvents.triggerNewSurvivorEvent(survivor);
	}

	/**
	 * add one zombie to the game
	 * @param zombie
	 */
	public void add(Zombie zombie) {
		this.zombies.add(zombie);
	}

	/**
	 * Add a wall to the game.
	 * @param wall
	 */
	public void add(Wall wall) {
		walls.add(wall);
	}

	/**
	 * Add a trap to the game.
	 * @param trap
	 */
	public void add(Trap trap) {
		traps.add(trap);
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
	 * Handle game event: move survivors and zombies, fight zombies and infect survivors.
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
			turn.play();
		}
	}

	public void pause() {
		if (gameTimeline.getStatus() == Animation.Status.PAUSED) {
			gameTimeline.play();
		} else {
			gameTimeline.pause();
		}
	}

	protected void sleepUntilTheNextDay() {
		if (sleep == 1) {
			DayChanger.nextDay();
			messages.addMessage("Begin day " + DayChanger.day + ": " + zombies.size() + " new zombies.");
		}
		sleep--;
	}

	protected void gameOver() {
		gameTimeline.stop();
		messages.addMessage("All survivors are lost! You survived " + DayChanger.day + " days. Game over!");
	}

	protected void endTheCurrentDay() {
		turn.getLootDistributor().collectLootFrom(zombies.size());
		emptyTraps();
		zombies.clear();
		messages.addMessage("All zombies cleared and trapped loot collected. You managed to survive another day!");
		messages.addMessage("Prepare for day " + (DayChanger.day + 1));
		sleep = 180;
	}

	private void emptyTraps() {
		for (Trap trap : traps) {
			trap.empty();
		}
	}

	protected boolean zombiesCleared() {
		for (Zombie zombie : zombies) {
			if (!zombie.isTrapped()) {
				return false;
			}
		}
		return true;
	}
}