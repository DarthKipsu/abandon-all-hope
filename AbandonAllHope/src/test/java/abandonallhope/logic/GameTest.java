package abandonallhope.logic;

import abandonallhope.domain.*;
import abandonallhope.domain.constructions.*;
import abandonallhope.ui.MessagePanel;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

	private Items items;
	private Game game;

	@Before
	public void setUp() {
		game = new Game(30);
		items = game.getItems();
		MessagePanel messages = new MessagePanel(game);
		Timeline gtl = new Timeline(new KeyFrame(Duration.millis(1000 / 60), game));
		gtl.play();
		game.setGameTimeline(gtl);
		DayChanger.day = 1;
	}

	@Test
	public void addsCorrectAmountOfSurvivorsOneByOne() {
		for (int i = 0; i < 5; i++) {
			items.add(new Survivor(new Point(10, 10), items.getMap(), "name", 1));
		}
		assertEquals(5, items.getSurvivors().size());
	}

	@Test
	public void NoSurvivorsAtTheBeginningOfANewGame() {
		assertEquals(0, items.getSurvivors().size());
	}

	@Test
	public void addsCorrectAmountOfZombiesOneByOne() {
		for (int i = 0; i < 5; i++) {
			items.add(new Zombie(new Point(10, 10), items.getMap(), items.getZombies()));
		}
		assertEquals(5, items.getZombies().size());
	}

	@Test
	public void NoZombiesAtTheBeginningOfTheGame() {
		assertEquals(0, items.getZombies().size());
	}

	@Test
	public void addsCorrectAmountOfWallsOneByOne() {
		for (int i = 0; i < 50; i += 10) {
			items.add(new Wall(WallType.WOODEN, Wall.Orientation.HORIZONAL, new Point(i, i)));
		}
		assertEquals(5, items.getWalls().size());
	}

	@Test
	public void NoWallsAtTheBeginningOfTheGame() {
		assertEquals(0, items.getWalls().size());
	}

	@Test
	public void addsCorrectAmountOfTrapsOneByOne() {
		for (int i = 0; i < 50; i += 10) {
			items.add(new Trap(new Point(10 + i, 10 + i), TrapType.BEARIRON));
		}
		assertEquals(5, items.getTraps().size());
	}

	@Test
	public void NoTrapsAtTheBeginningOfTheGame() {
		assertEquals(0, items.getTraps().size());
	}

	@Test
	public void returnsMapWithGetMap() {
		assertEquals("30.0 x 30.0", items.getMap().toString());
	}

	@Test
	public void NoBulletsAtTheBeginningOfANewGame() {
		assertEquals(0, items.getBullets().size());
	}

	@Test
	public void canSetGameTimeLine() {
		Timeline tl = new Timeline();
		game.setGameTimeline(tl);
		assertEquals(tl, game.gameTimeline);
	}

	@Test
	public void decreaseSleepByOneEachTurnSleepIsCalled() {
		game.sleep = 100;
		for (int i = 0; i < 10; i++) {
			game.sleepUntilTheNextDay();
		}
		assertEquals(90, game.sleep);
	}

	@Test
	public void dontShowMessageIfSleepIsNotOver() {
		game.sleep = 2;
		game.sleepUntilTheNextDay();
		assertTrue(MessagePanel.getMessages().isEmpty());
	}

	@Test
	public void displayMessageWhenSleepIsOver() {
		game.sleep = 1;
		game.sleepUntilTheNextDay();
		Text text = (Text) MessagePanel.getMessages().get(0);
		assertEquals("     Begin day 2: 0 new zombies.", text.getText());
	}

	@Test
	public void addNewZombiesOnGameChange() {
		DayChanger.setGame(items, game.getTurn());
		game.sleep = 1;
		game.sleepUntilTheNextDay();
		assertFalse(items.getZombies().isEmpty());
	}

	@Test
	public void noLootIsDistributedAtTheEndOfTheDayIfNoZombiesAreTrapped() {
		game.endTheCurrentDay();
		assertTrue(items.getInventory().getGuns().isEmpty());
		assertTrue(items.getInventory().getWeapons().isEmpty());
		assertFalse(items.getInventory().getPistolBullets().notEmpty());
	}

	@Test
	public void someLootIsDistributedAtTheEndOfTheDayIfZombiesAreTrapped() {
		addZombie(10, 10);
		game.endTheCurrentDay();
		int loot = collectAllLoot();
		assertTrue(loot > 0);
	}

	@Test
	public void lootIsDitributedFromAllTheZombiesIfMoreZombieasAreTrapped() {
		for (int i = 0; i < 10; i++) {
			addZombie(10, 10);
		}
		game.endTheCurrentDay();
		int loot = collectAllLoot();
		assertTrue(loot >= 10);
	}

	@Test
	public void allZombiesFromPreviousDayAreDeleted() {
		addZombie(10, 10);
		game.endTheCurrentDay();
		assertTrue(items.getZombies().isEmpty());
	}

	@Test
	public void increaseDayCounterAfterTheEndOfDay() {
		DayChanger.nextDay();
		assertEquals(2, DayChanger.day);
	}

	@Test
	public void add3SecondsSleepCounterAfterEndOfDay() {
		game.endTheCurrentDay();
		assertEquals(180, game.sleep);
	}

	@Test
	public void showMessagesDisplayingDayChangeAfterDayChange() {
		game.endTheCurrentDay();
		Text text = (Text) MessagePanel.getMessages().get(1);
		Text text2 = (Text) MessagePanel.getMessages().get(0);
		assertEquals("     All zombies cleared and trapped loot collected. You managed to survive another day!", text.getText());
		assertEquals("     Prepare for day 2", text2.getText());
	}

	@Test
	public void dayChangeConditionReturnsTrueWhenNoZombiesAreLeft() {
		assertTrue(items.zombiesCleared());
	}

	@Test
	public void dayChangeConditionReturnsFalseIfThereAreZombies() {
		addZombie(10, 10);
		assertFalse(items.zombiesCleared());
	}

	@Test
	public void getResourcesPAnel() {
		assertNotNull(game.getTurn().getResourceEvents());
	}

	@Test
	public void setsGameOnPauseWhenGameIsRunning() {
		game.pause();
		assertEquals(Status.PAUSED, game.gameTimeline.getStatus());
	}
	
	@Test
	public void startingNewGameAddsNEwGameMessage() {
		game.startANewGame();
		Text text = (Text) MessagePanel.getMessages().get(0);
		assertEquals("     It's the first day of zombie apocalypse! You " +
				"managed to survive out of the city with your group...", text.getText());
	}
	
	@Test
	public void startingNewGameClearsOldMessages() {
		game.endTheCurrentDay();
		game.startANewGame();
		assertEquals(1, MessagePanel.getMessages().size());
	}
	
	@Test
	public void startingANewGameStartsTheGameTimelineAgain() {
		game.gameTimeline.stop();
		game.startANewGame();
		assertEquals(Status.RUNNING, game.gameTimeline.getStatus());
	}
	
	@Test
	public void itemsGetResetAfterStartingANewGame() {
		items.add(new Trap(null, TrapType.PIT));
		game.startANewGame();
		assertTrue(items.getTraps().isEmpty());
	}
	
	@Test
	public void gameDayResetsTo1AfterStartingANewGame() {
		DayChanger.day = 20;
		game.startANewGame();
		assertEquals(1, DayChanger.day);
	}
	
	@Test
	public void fiveNewSurvivorsIsInGameAfterStartingANewGame() {
		DayChanger.setGame(items, game.getTurn());
		game.startANewGame();
		assertEquals(5, items.getSurvivors().size());
	}
	
	@Test
	public void emptiesTrapWhenStartingANewDay() {
		Trap trap = new Trap(null, TrapType.BEARIRON);
		trap.addZombie();
		items.add(trap);
		game.endTheCurrentDay();
		assertTrue(trap.hasCapacityLeft());
	}

	private Zombie addZombie(double x, double y) {
		Zombie zombie = new Zombie(new Point(x, y), items.getMap(), items.getZombies());
		items.add(zombie);
		return zombie;
	}

	private int collectAllLoot() {
		int loot = 0;
		loot += items.getInventory().getGuns().size();
		loot += items.getInventory().getWeapons().size();
		loot += items.getInventory().getPistolBullets().getBullets();
		loot += items.getInventory().getMetal();
		loot += items.getInventory().getWood();
		return loot;
	}

}
