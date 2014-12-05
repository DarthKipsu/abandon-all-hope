
package abandonallhope.logic;

import abandonallhope.domain.*;
import abandonallhope.domain.constructions.*;
import abandonallhope.domain.weapons.*;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import abandonallhope.ui.MessagePanel;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
	
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game(30);
		game.setMessages(new MessagePanel(game));
	}
	
	@Test
	public void addsCorrectAmountOfSurvivorsOneByOne() {
		for (int i = 0; i < 5; i++) {
			game.add(new Survivor(new Point(10,10), game.getMap(), "name", 1));
		}
		assertEquals(5, game.getSurvivors().size());
	}
	
	@Test
	public void addsCorrectAmountOfSurvivorsAtOnce() {
		game.add(new Survivor(new Point(10,10), game.getMap(), "name", 1),
				new Survivor(new Point(10,10), game.getMap(), "name", 1),
				new Survivor(new Point(10,10), game.getMap(), "name", 1));
		assertEquals(3, game.getSurvivors().size());
	}

	@Test
	public void NoSurvivorsAtTheBeginningOfANewGame() {
		assertEquals(0, game.getSurvivors().size());
	}
	
	@Test
	public void addsCorrectAmountOfZombiesOneByOne() {
		for (int i = 0; i < 5; i++) {
			game.add(new Zombie(new Point(10,10), game.getMap()));
		}
		assertEquals(5, game.getZombies().size());
	}
	
	@Test
	public void addsCorrectAmountOfZombiesAtOnce() {
		game.add(new Zombie(new Point(10,10), game.getMap()),
				new Zombie(new Point(10,10), game.getMap()),
				new Zombie(new Point(10,10), game.getMap()));
		assertEquals(3, game.getZombies().size());
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
	public void addsCorrectAmountOfWallsAtOnce() {
		game.add(new Wall(WallType.WOODEN, Wall.Orientation.HORIZONAL, new Point(5,10)),
				new Wall(WallType.WOODEN, Wall.Orientation.HORIZONAL, new Point(10,15)),
				new Wall(WallType.WOODEN, Wall.Orientation.HORIZONAL, new Point(15,20)));
		assertEquals(3, game.getWalls().size());
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
	public void addsCorrectAmountOfTrapsAtOnce() {
		game.add(new Trap(new Point(10, 10), TrapType.BEARIRON),
				new Trap(new Point(20, 20), TrapType.BEARIRON),
				new Trap(new Point(0, 0), TrapType.BEARIRON));
		assertEquals(3, game.getTraps().size());
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
	public void movesZombies() {
		Zombie zombie = new Zombie(new Point(20, 20), game.getMap());
		game.add(new Survivor(new Point(10, 10), game.getMap(), "name", 1));
		game.add(zombie);
		game.moveZombies();
		assertEquals(new Point(20 - zombie.getSpeed(), 20 - zombie.getSpeed()), zombie.getLocation());
	}
	
	@Test
	public void movesSurvivors() {
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap(), "name", 1);
		survivor.moveTowards(new Point(20, 20));
		game.add(survivor);
		game.moveSurvivors();
		assertEquals(new Point(10 + survivor.getSpeed(), 10 + survivor.getSpeed()), survivor.getLocation());
	}
	
	@Test
	public void infectSurvivorInASamePointWithZombie() {
		addSurvivor(10, 10);
		addZombie(10, 10);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void infectSSurvivorsTooCloseToZombies() {
		addSurvivor(10, 10);
		addZombie(7.1, 7.1);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void infectSSurvivorsTooCloseToZombies2() {
		addSurvivor(10, 10);
		addZombie(12.9, 12.9);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void dontInfectSurvivorsFarEnoughFromZombies() {
		addSurvivor(10, 10);
		addZombie(7, 7);
		game.infectSurvivors();
		assertEquals(1, game.getSurvivors().size());
	}
	
	@Test
	public void createANewZombieAfterInfection() {
		addSurvivor(10, 10);
		addZombie(10, 10);
		game.infectSurvivors();
		assertEquals(2, game.getZombies().size());
	}
	
	@Test
	public void killZombieCloseToArmedSurvivor() {
		Survivor survivor = addSurvivor(10, 10);
		addZombie(8.5, 8.5);
		Weapon axe = new Axe();
		survivor.setWeapon(axe);
		game.fightZombies();
		assertTrue(game.getZombies().isEmpty());
		assertFalse(axe.canBeUsed());
	}
	
	@Test
	public void dontKillZombieFurtherFromArmedSurvivor() {
		addSurvivor(10, 10);
		addZombie(8.4, 8.4);
		game.fightZombies();
		assertFalse(game.getZombies().isEmpty());
	}
	
	@Test
	public void dontKillZombieCloseToArmedSurvivorIfWeaponIsNotUsable() {
		Survivor survivor = addSurvivor(10, 10);
		addZombie(8.5, 8.5);
		survivor.setWeapon(new Axe());
		survivor.getWeapon().use();
		game.fightZombies();
		assertFalse(game.getZombies().isEmpty());
	}

	@Test
	public void NoBulletsAtTheBeginningOfANewGame() {
		assertEquals(0, game.getBullets().size());
	}
	
	@Test
	public void doesNotUseOtherWeaponIfSurvivorHasAGun() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(10, 10);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		game.fightZombies();
		assertTrue(survivor.getWeapon().canBeUsed());
	}
	
	@Test
	public void shootsABulletIfCloseEnoughToZombieAndHasBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(65, 5); // shoots when zombie 1.2 times weapon range, because zombie moves towards player
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		game.fightZombies();
		assertEquals(1, game.getBullets().size());
	}
	
	@Test
	public void doesNotShootABulletIfNotCloseEnoughToZombieAndHasBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(66, 5);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		game.fightZombies();
		assertEquals(0, game.getBullets().size());
	}
	
	@Test
	public void doesNotShootABulletIfCloseEnoughToZombieButHasNoBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(45, 40);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(0));
		game.fightZombies();
		assertEquals(0, game.getBullets().size());
	}
	
	@Test
	public void doesNotWasteBulletsOnTrappedZombies() {
		Survivor survivor = addSurvivor(10, 5);
		Zombie zombie = new Zombie(new Point(5, 5), game.getMap());
		game.add(zombie);
		game.add(new Trap(new Point(5, 5), TrapType.BEARIRON));
		zombie.move();
		survivor.setGun(createGun(1));
		game.fightZombies();
		assertTrue(survivor.getGun().canBeUsed());
	}
	
	@Test
	public void usesAxeWhenCloseEnoughToZombieAndHasGunButNoBullets() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(0));
		game.fightZombies();
		assertFalse(survivor.getWeapon().canBeUsed());
	}
	
	@Test
	public void usesAxeWhenCloseEnoughToZombieAndWeaponIsNotYetReloaded() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(0));
		survivor.getGun().use();
		game.fightZombies();
		assertFalse(survivor.getWeapon().canBeUsed());
	}
	
	@Test
	public void doesNotUseAnyWeaponIfHasNoWeapons() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		game.fightZombies();
		assertEquals(1, game.getZombies().size());
	}
	
	@Test
	public void bulletsDecreaseWhenFightingZombiesAndGunIsUsed() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		game.getInventory().addPistolBullets(1);
		survivor.setGun(new Pistol(game.getInventory()));
		game.fightZombies();
		assertEquals(0, game.getInventory().getPistolBullets().getBullets());
	}
	
	@Test
	public void gunReloadsEachTurn() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setGun(createGun(2));
		survivor.getGun().use();
		fightZombiesForSeveralRounds(300);
		assertTrue(survivor.getGun().canBeUsed());
	}
	
	@Test
	public void bulletMovesOnceInOneFrame() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(20, 5);
		survivor.setGun(createGun(1));
		game.fightZombies();
		game.handleBullets();
		assertEquals(new Point(6, 5), game.getBullets().get(0).getLocation());
	}
	
	@Test
	public void dontRemoveBulletsWhenTheyHaveNotYetMovedTheirMaxDistance() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setGun(createGun(1));
		fightZombiesForSeveralRounds(34);
		assertEquals(1, game.getBullets().size());
	}
	
	@Test
	public void zombieIsRemovedWhenBulletHitsIt() {
		addSurvivorWithPistol(10, 10, 1);
		addZombie(11, 10);
		game.fightZombies();
		game.handleBullets();
		assertEquals(0, game.getZombies().size());
	}
	
	@Test
	public void zombieIsNotRemovedWhenBulletDoesNotHitIt() {
		addSurvivorWithPistol(10, 10, 1);
		addZombie(13, 10);
		game.fightZombies();
		game.handleBullets();
		assertEquals(1, game.getBullets().size());
	}
	
	@Test
	public void newSurvivorEventTriggersWhenNewSurvivorIsAdded() {
		NewSurvivorEventHandlerMock res = (NewSurvivorEventHandlerMock) newSurvivorEvent();
		game.addNewResourceEventHandler(res, "newSurvivor");
		addSurvivor(10, 10);
		assertEquals(1, res.handleEvents);
	}
	
	@Test
	public void deleteSurvivorEventTriggersWhenSurvivorIsRemovedFromGame() {
		DeleteSurvivorEventHandlerMock res = (DeleteSurvivorEventHandlerMock) deleteSurvivorEvent();
		game.addNewResourceEventHandler(res, "deleteSurvivor");
		addZombie(10, 10);
		addSurvivor(10, 10);
		game.infectSurvivors();
		assertEquals(1, res.handleEvents);
	}
	
	@Test
	public void displayMessageWhenSurvivorIsRemovedFromGame() {
		addZombie(10, 10);
		addSurvivor(10, 10);
		game.infectSurvivors();
		Text text = (Text) game.messages.getVbox().getChildren().get(0);
		assertEquals("name was bit and turned into a zombie!", text.getText());
	}
	
	@Test
	public void displayMessageWhenZombieIsRemovedFromGame() {
		Zombie zombie = addZombie(10, 10);
		game.killAZombie(zombie);
		Text text = (Text) game.messages.getVbox().getChildren().get(0);
		assertEquals("Zombie dropped ", text.getText().subSequence(0, 15));
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
		assertEquals("Begin day 1: 0 new zombies.", text.getText());
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
		Timeline timeline = new Timeline();
		timeline.play();
		game.setGameTimeline(timeline);
		game.gameOver();
		assertEquals(Status.STOPPED, timeline.statusProperty().getValue());
	}
	
	@Test
	public void gameOverCreatesAMessageToPlayer() {
		game.setGameTimeline(new Timeline());
		game.gameOver();
		Text text = (Text) game.messages.getVbox().getChildren().get(0);
		assertEquals("All survivors are lost! You survived 1 days. Game over!", text.getText());
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
		game.endTheCurrentDay();
		assertEquals(2, game.getDay());
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
		assertEquals("All zombies cleared and trapped loot collected. You managed to survive another day!", text.getText());
		assertEquals("Prepare for day 2", text2.getText());
	}
	
	@Test
	public void dayChangeConditionReturnsTrueWhenNoZombiesAreLeft() {
		assertTrue(game.zombiesCleared());
	}
	
	@Test
	public void dayChangeConditionReturnsTrueWhenOnlyTrappedZombiesAreLeft() {
		addSurvivor(20, 20);
		addZombie(10, 10);
		game.add(new Trap(new Point(10.2, 10.2), TrapType.BEARIRON));
		game.moveZombies();
		assertTrue(game.zombiesCleared());
	}
	
	@Test
	public void dayChangeConditionReturnsFalseIfThereAreZombies() {
		addZombie(10, 10);
		assertFalse(game.zombiesCleared());
	}
	
	@Test
	public void turnHandlesBullets() {
		game.getBullets().add(new Bullet(new Point(0, 10), game.getMap(), new Point(0, 0), 10));
		game.playATurn();
		assertEquals(new Point(0, 9), game.getBullets().get(0).getLocation());
	}
	
	@Test
	public void turnMovesSurvivors() {
		Survivor survivor = addSurvivor(0, 10);
		survivor.moveTowards(new Point(10, 10));
		game.playATurn();
		assertEquals(new Point(0.5, 10), survivor.getLocation());
	}
	
	@Test
	public void turnMovesZombiesIfSurvivorsIsNotEmpty() {
		addSurvivor(0, 10);
		Zombie zombie = addZombie(10, 10);
		game.playATurn();
		assertEquals(new Point(9.7, 10), zombie.getLocation());
	}
	
	@Test
	public void turnDoesNotMoveZombiesIfSurvivorsIsEmpty() {
		Zombie zombie = addZombie(10, 10);
		game.playATurn();
		assertEquals(new Point(10, 10), zombie.getLocation());
	}
	
	@Test
	public void turnFightsZombies() {
		addZombie(10, 10);
		Survivor survivor = addSurvivor(10, 10);
		survivor.setWeapon(new Axe());
		game.playATurn();
		assertTrue(game.getZombies().isEmpty());
	}
	
	@Test
	public void turnInfectsSurvivors() {
		addZombie(10, 10);
		addSurvivor(10, 10);
		game.playATurn();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	private Survivor addSurvivorWithPistol(double x, double y, int bullets) {
		Survivor survivor = addSurvivor(x, y);
		survivor.setGun(createGun(bullets));
		return survivor;
	}
	
	private Survivor addSurvivor(double x, double y) {
		Survivor survivor = new Survivor(new Point(x, y), game.getMap(), "name", 1);
		game.add(survivor);
		return survivor;
	}
	
	private Zombie addZombie(double x, double y) {
		Zombie zombie = new Zombie(new Point(x, y), game.getMap());
		game.add(zombie);
		return zombie;
	}
	
	private Pistol createGun(int bullets) {
		game.getInventory().addPistolBullets(bullets);
		return new Pistol(game.getInventory());
	}
	
	private void fightZombiesForSeveralRounds(int rounds) {
		for (int i = 0; i < rounds; i++) {
			game.fightZombies();
		}
	}

	private ResourceEventHandler newSurvivorEvent() {
		return new NewSurvivorEventHandlerMock();
	}

	private ResourceEventHandler deleteSurvivorEvent() {
		return new DeleteSurvivorEventHandlerMock();
	}

	private class NewSurvivorEventHandlerMock implements NewSurvivorEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewSurvivorEvent e) {
			handleEvents++;
		}
	}

	private class DeleteSurvivorEventHandlerMock implements DeleteSurvivorEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(DeleteSurvivorEvent e) {
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
