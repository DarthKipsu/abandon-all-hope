package abandonallhope.logic;

import abandonallhope.ui.MessagePanel;
import abandonallhope.ui.PopupMessage;
import javafx.animation.*;
import javafx.event.*;

/**
 * Handles game scenarios
 */
public class Game implements EventHandler {

	protected Timeline gameTimeline;
	private Items items;
	private Turn turn;
	protected int sleep;

	/**
	 * Create new game event handler
	 * @param mapSize size of the game map to be created
	 */
	public Game(int mapSize) {
		items = new Items(mapSize);
		turn = new Turn(items);
		sleep = 0;
	}

	public Turn getTurn() {
		return turn;
	}

	public Items getItems() {
		return items;
	}

	public void setGameTimeline(Timeline gameTimeline) {
		this.gameTimeline = gameTimeline;
	}

	/**
	 * Handle game event: move survivors and zombies, fight zombies and infect survivors.
	 * @param t Turn event
	 */
	@Override
	public void handle(Event t) {
		if (sleep > 0) {
			sleepUntilTheNextDay();
		} else if (items.getSurvivors().isEmpty()) {
			gameOver();
		} else if (items.zombiesCleared()) {
			endTheCurrentDay();
		} else {
			turn.play();
		}
	}

	/**
	 * Set game on pause or continue once game is already paused.
	 */
	public void pause() {
		if (gameTimeline.getStatus() == Animation.Status.PAUSED) {
			gameTimeline.play();
			PopupMessage.removePauseMessage();
		} else {
			gameTimeline.pause();
			try {
				PopupMessage.showPauseMessage();
			} catch (ExceptionInInitializerError e) {
				// Needed for tests to run without initializing popup
			}
		}
	}
	
	/**
	 * Creates a new game and deletes objects from the previous game.
	 */
	public void startANewGame() {
		MessagePanel.clearMessages();
		items.reset();
		DayChanger.day = 1;
		DayChanger.setupDayOne();
		MessagePanel.addMessage("It's the first day of zombie apocalypse! You " +
				"managed to survive out of the city with your group...");
		gameTimeline.play();
	}

	protected void sleepUntilTheNextDay() {
		if (sleep == 1) {
			DayChanger.nextDay();
			MessagePanel.addMessage("Begin day " + DayChanger.day + ": " + items.getZombies().size() + " new zombies.");
		}
		sleep--;
	}

	protected void gameOver() {
		gameTimeline.pause();
		PopupMessage.showGameOverMessage(this);
	}

	protected void endTheCurrentDay() {
		turn.getLootDistributor().collectLootFrom(items.getZombies().size());
		items.emptyTraps();
		items.getZombies().clear();
		MessagePanel.addMessage("All zombies cleared and trapped loot collected. You managed to survive another day!");
		MessagePanel.addMessage("Prepare for day " + (DayChanger.day + 1));
		sleep = 180;
	}
}