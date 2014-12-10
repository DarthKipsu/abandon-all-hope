
package abandonallhope.logic;

import abandonallhope.domain.*;
import abandonallhope.domain.constructions.*;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
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
	
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game(30);
		game.setMessages(new MessagePanel(game));
		DayChanger.day = 1;
	}
	
	@Test
	public void addsCorrectAmountOfSurvivorsOneByOne() {
		for (int i = 0; i < 5; i++) {
			game.add(new Survivor(new Point(10,10), game.getMap(), "name", 1));
		}
		assertEquals(5, game.getSurvivors().size());
	}

	@Test
	public void NoSurvivorsAtTheBeginningOfANewGame() {
		assertEquals(0, game.getSurvivors().size());
	}
	
	@Test
	public void addsCorrectAmountOfZombiesOneByOne() {
		for (int i = 0; i < 5; i++) {
			game.add(new Zombie(new Point(10,10), game.getMap(), game.getZombies()));
		}
		assertEquals(5, game.getZombies().size());
	}

	@Test
	public void NoZombiesAtTheBeginningOfTheGame() {
		assertEquals(0, game.getZombies().size());
	}
	
	@Test
	public void addsCorrectAmountOfWallsOneByOne() {
		for (int i = 0; i < 50; i+=10) {
			game.add(new Wall(WallType.WOODEN, Wall.Orientation.HORIZONAL, new Point(i,i)));
		}
		assertEquals(5, game.getWalls().size());
	}
	
	@Test
	public void NoWallsAtTheBeginningOfTheGame() {
		assertEquals(0, game.getWalls().size());
	}
	
	@Test
	public void addsCorrectAmountOfTrapsOneByOne() {
		for (int i = 0; i < 50; i+=10) {
			game.add(new Trap(new Point(10 + i, 10 + i), TrapType.BEARIRON));
		}
		assertEquals(5, game.getTraps().size());
	}

	@Test
	public void NoTrapsAtTheBeginningOfTheGame() {
		assertEquals(0, game.getTraps().size());
	}

	@Test
	public void returnsMapWithGetMap() {
		assertEquals("30.0 x 30.0", game.getMap().toString());
	}

	@Test
	public void NoBulletsAtTheBeginningOfANewGame() {
		assertEquals(0, game.getBullets().size());
	}
	
	@Test
	public void newSurvivorEventTriggersWhenNewSurvivorIsAdded() {
		NewSurvivorEventHandlerMock res = (NewSurvivorEventHandlerMock) newSurvivorEvent();
		game.addNewResourceEventHandler(res, "newSurvivor");
		addSurvivor(10, 10);
		assertEquals(1, res.handleEvents);
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
		assertTrue(game.messages.getVbox().getChildren().isEmpty());
	}
	
	@Test
	public void displayMessageWhenSleepIsOver() {
		game.sleep = 1;
		game.sleepUntilTheNextDay();
		Text text = (Text) game.messages.getVbox().getChildren().get(0);
		assertEquals("     Begin day 2: 0 new zombies.", text.getText());
	}
	
	@Test
	public void addNewZombiesOnGameChange() {
		DayChanger.setGame(game);
		game.sleep = 1;
		game.sleepUntilTheNextDay();
		assertFalse(game.getZombies().isEmpty());
	}
	
	@Test
	public void gameOverStopsTimeline() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), game));
		timeline.play();
		game.setGameTimeline(timeline);
		game.gameOver();
		assertEquals(Status.STOPPED, timeline.getStatus());
	}

	@Test
	public void pauseStopsGameTimeline() {
		Timeline gtl = new Timeline(new KeyFrame(Duration.millis(1000 / 60), game));
		gtl.play();
		game.setGameTimeline(gtl);
		game.pause();
		assertEquals(Status.PAUSED, gtl.getStatus());
	}

	@Test
	public void pauseAgainContinuesGameTimeline() {
		Timeline gtl = new Timeline(new KeyFrame(Duration.millis(1000 / 60), game));
		gtl.play();
		game.setGameTimeline(gtl);
		game.pause();
		game.pause();
		assertEquals(Status.RUNNING, gtl.getStatus());
	}
	
	@Test
	public void gameOverCreatesAMessageToPlayer() {
		game.setGameTimeline(new Timeline());
		game.gameOver();
		Text text = (Text) game.messages.getVbox().getChildren().get(0);
		assertEquals("     All survivors are lost! You survived 1 days. Game over!", text.getText());
	}
	
	@Test
	public void noLootIsDistributedAtTheEndOfTheDayIfNoZombiesAreTrapped() {
		game.endTheCurrentDay();
		assertTrue(game.getInventory().getGuns().isEmpty());
		assertTrue(game.getInventory().getWeapons().isEmpty());
		assertFalse(game.getInventory().getPistolBullets().notEmpty());
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
		assertTrue(game.getZombies().isEmpty());
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
		Text text = (Text) game.messages.getVbox().getChildren().get(1);
		Text text2 = (Text) game.messages.getVbox().getChildren().get(0);
		assertEquals("     All zombies cleared and trapped loot collected. You managed to survive another day!", text.getText());
		assertEquals("     Prepare for day 2", text2.getText());
	}
	
	@Test
	public void dayChangeConditionReturnsTrueWhenNoZombiesAreLeft() {
		assertTrue(game.zombiesCleared());
	}
	
	@Test
	public void dayChangeConditionReturnsFalseIfThereAreZombies() {
		addZombie(10, 10);
		assertFalse(game.zombiesCleared());
	}
	
	@Test
	public void getGameMessages() {
		MessagePanel mp = new MessagePanel(game);
		game.setMessages(mp);
		assertEquals(mp, game.getMessages());
	}
	
	@Test
	public void getResourcesPAnel() {
		assertNotNull(game.getResourceEvents());
	}
	
	private Survivor addSurvivor(double x, double y) {
		Survivor survivor = new Survivor(new Point(x, y), game.getMap(), "name", 1);
		game.add(survivor);
		return survivor;
	}
	
	private Zombie addZombie(double x, double y) {
		Zombie zombie = new Zombie(new Point(x, y), game.getMap(), game.getZombies());
		game.add(zombie);
		return zombie;
	}

	private ResourceEventHandler newSurvivorEvent() {
		return new NewSurvivorEventHandlerMock();
	}

	private class NewSurvivorEventHandlerMock implements NewSurvivorEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewSurvivorEvent e) {
			handleEvents++;
		}
	}

	private int collectAllLoot() {
		int loot = 0;
		loot += game.getInventory().getGuns().size();
		loot += game.getInventory().getWeapons().size();
		loot += game.getInventory().getPistolBullets().getBullets();
		loot += game.getInventory().getMetal();
		loot += game.getInventory().getWood();
		return loot;
	}
	
}
