
package abandonallhope.logic;

import abandonallhope.domain.*;
import abandonallhope.domain.constructions.Trap;
import abandonallhope.domain.constructions.TrapType;
import abandonallhope.domain.weapons.*;
import abandonallhope.events.action.DeleteSurvivorEvent;
import abandonallhope.events.handlers.DeleteSurvivorEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import abandonallhope.ui.MessagePanel;
import javafx.scene.text.Text;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TurnTest {
	
	private Items items;
	private Turn turn;
	
	@Before
	public void setUp() {
		Game game = new Game(30);
		MessagePanel messages = new MessagePanel();
		items = game.getItems();
		turn = game.getTurn();
	}
	
	@Test
	public void movesZombies() {
		Zombie zombie = addZombie(20, 20);
		items.add(new Survivor(new Point(10, 10), items.getMap(), "name", 1));
		turn.moveZombies();
		assertEquals(new Point(20 - zombie.getSpeed(), 20 - zombie.getSpeed()), zombie.getLocation());
	}
	
	@Test
	public void movesSurvivors() {
		Survivor survivor = new Survivor(new Point(10, 10), items.getMap(), "name", 1);
		survivor.moveTowards(new Point(20, 20));
		items.add(survivor);
		turn.moveSurvivors();
		assertEquals(new Point(10 + survivor.getSpeed(), 10 + survivor.getSpeed()), survivor.getLocation());
	}
	
	@Test
	public void infectSurvivorInASamePointWithZombie() {
		addSurvivor(10, 10);
		addZombie(10, 10);
		turn.infectSurvivors();
		assertTrue(items.getSurvivors().isEmpty());
	}
	
	@Test
	public void infectSSurvivorsTooCloseToZombies() {
		addSurvivor(10, 10);
		addZombie(7.1, 7.1);
		turn.infectSurvivors();
		assertTrue(items.getSurvivors().isEmpty());
	}
	
	@Test
	public void infectSSurvivorsTooCloseToZombies2() {
		addSurvivor(10, 10);
		addZombie(12.9, 12.9);
		turn.infectSurvivors();
		assertTrue(items.getSurvivors().isEmpty());
	}
	
	@Test
	public void dontInfectSurvivorsFarEnoughFromZombies() {
		addSurvivor(10, 10);
		addZombie(7, 7);
		turn.infectSurvivors();
		assertEquals(1, items.getSurvivors().size());
	}
	
	@Test
	public void createANewZombieAfterInfection() {
		addSurvivor(10, 10);
		addZombie(10, 10);
		turn.infectSurvivors();
		assertEquals(2, items.getZombies().size());
	}
	
	@Test
	public void killZombieCloseToArmedSurvivor() {
		Survivor survivor = addSurvivor(10, 10);
		addZombie(8.5, 8.5);
		Weapon axe = new Axe();
		survivor.setWeapon(axe);
		turn.fightZombies();
		assertTrue(items.getZombies().isEmpty());
		assertFalse(axe.canBeUsed());
	}
	
	@Test
	public void dontKillZombieFurtherFromArmedSurvivor() {
		addSurvivor(10, 10);
		addZombie(8.4, 8.4);
		turn.fightZombies();
		assertFalse(items.getZombies().isEmpty());
	}
	
	@Test
	public void dontKillZombieCloseToArmedSurvivorIfWeaponIsNotUsable() {
		Survivor survivor = addSurvivor(10, 10);
		addZombie(8.5, 8.5);
		survivor.setWeapon(new Axe());
		survivor.getWeapon().use();
		turn.fightZombies();
		assertFalse(items.getZombies().isEmpty());
	}
	
	@Test
	public void doesNotUseOtherWeaponIfSurvivorHasAGun() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(10, 10);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		turn.fightZombies();
		assertTrue(survivor.getWeapon().canBeUsed());
	}
	
	@Test
	public void shootsABulletIfCloseEnoughToZombieAndHasBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(65, 5); // shoots when zombie 1.2 times weapon range, because zombie moves towards player
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		turn.fightZombies();
		assertEquals(1, items.getBullets().size());
	}
	
	@Test
	public void doesNotShootABulletIfNotCloseEnoughToZombieAndHasBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(66, 5);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		turn.fightZombies();
		assertEquals(0, items.getBullets().size());
	}
	
	@Test
	public void doesNotShootABulletIfCloseEnoughToZombieButHasNoBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(45, 40);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(0));
		turn.fightZombies();
		assertEquals(0, items.getBullets().size());
	}
	
	@Test
	public void doesNotWasteBulletsOnTrappedZombies() {
		Survivor survivor = addSurvivor(10, 5);
		Zombie zombie = addZombie(5, 5);
		items.add(zombie);
		items.add(new Trap(new Point(4.6, 4.6), TrapType.PIT));
		zombie.move();
		survivor.setGun(createGun(1));
		turn.fightZombies();
		assertTrue(survivor.getGun().canBeUsed());
	}
	
	@Test
	public void usesAxeWhenCloseEnoughToZombieAndHasGunButNoBullets() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(0));
		turn.fightZombies();
		assertFalse(survivor.getWeapon().canBeUsed());
	}
	
	@Test
	public void usesAxeWhenCloseEnoughToZombieAndWeaponIsNotYetReloaded() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(0));
		survivor.getGun().use();
		turn.fightZombies();
		assertFalse(survivor.getWeapon().canBeUsed());
	}
	
	@Test
	public void doesNotUseAnyWeaponIfHasNoWeapons() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		turn.fightZombies();
		assertEquals(1, items.getZombies().size());
	}
	
	@Test
	public void bulletsDecreaseWhenFightingZombiesAndGunIsUsed() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		items.getInventory().addPistolBullets(1);
		survivor.setGun(new Pistol(items.getInventory()));
		turn.fightZombies();
		assertEquals(0, items.getInventory().getPistolBullets().getBullets());
	}
	
	@Test
	public void bulletMovesOnceInOneFrame() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(20, 5);
		survivor.setGun(createGun(1));
		turn.fightZombies();
		turn.handleBullets();
		assertEquals(new Point(6, 5), items.getBullets().get(0).getLocation());
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
	public void dontRemoveBulletsWhenTheyHaveNotYetMovedTheirMaxDistance() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setGun(createGun(1));
		fightZombiesForSeveralRounds(34);
		assertEquals(1, items.getBullets().size());
	}
	
	@Test
	public void zombieIsRemovedWhenBulletHitsIt() {
		addSurvivorWithPistol(10, 10, 1);
		addZombie(11, 10);
		turn.fightZombies();
		turn.handleBullets();
		assertEquals(0, items.getZombies().size());
	}
	
	@Test
	public void zombieIsNotRemovedWhenBulletDoesNotHitIt() {
		addSurvivorWithPistol(10, 10, 1);
		addZombie(13, 10);
		turn.fightZombies();
		turn.handleBullets();
		assertEquals(1, items.getBullets().size());
	}
	
	@Test
	public void deleteSurvivorEventTriggersWhenSurvivorIsRemovedFromGame() {
		DeleteSurvivorEventHandlerMock res = (DeleteSurvivorEventHandlerMock) deleteSurvivorEvent();
		turn.addNewResourceEventHandler(res, "deleteSurvivor");
		addZombie(10, 10);
		addSurvivor(10, 10);
		turn.infectSurvivors();
		assertEquals(1, res.handleEvents);
	}
	
	@Test
	public void displayMessageWhenSurvivorIsRemovedFromGame() {
		addZombie(10, 10);
		addSurvivor(10, 10);
		turn.infectSurvivors();
		Text text = (Text) MessagePanel.getMessages().get(0);
		assertEquals("     name was bit and turned into a zombie!", text.getText());
	}
	
	@Test
	public void displayMessageWhenZombieIsRemovedFromGame() {
		Zombie zombie = addZombie(10, 10);
		turn.killAZombie(zombie);
		Text text = (Text) MessagePanel.getMessages().get(0);
		assertEquals("     Zombie dropped ", text.getText().subSequence(0, 20));
	}
	
	@Test
	public void dayChangeConditionReturnsTrueWhenOnlyTrappedZombiesAreLeft() {
		addSurvivor(20, 20);
		addZombie(10, 10);
		items.add(new Trap(new Point(10.2, 10.2), TrapType.BEARIRON));
		turn.moveZombies();
		assertTrue(items.zombiesCleared());
	}
	
	@Test
	public void turnHandlesBullets() {
		items.getBullets().add(new Bullet(new Point(0, 10), items.getMap(), new Point(0, 0), 10));
		turn.play();
		assertEquals(new Point(0, 9), items.getBullets().get(0).getLocation());
	}
	
	@Test
	public void turnMovesSurvivors() {
		Survivor survivor = addSurvivor(0, 10);
		survivor.moveTowards(new Point(10, 10));
		turn.play();
		assertEquals(new Point(0.5, 10), survivor.getLocation());
	}
	
	@Test
	public void turnMovesZombiesIfSurvivorsIsNotEmpty() {
		addSurvivor(0, 10);
		Zombie zombie = addZombie(10, 10);
		turn.play();
		assertFalse(new Point(10, 10).equals(zombie.getLocation()));
	}
	
	@Test
	public void turnDoesNotMoveZombiesIfSurvivorsIsEmpty() {
		Zombie zombie = addZombie(10, 10);
		turn.play();
		assertEquals(new Point(10, 10), zombie.getLocation());
	}
	
	@Test
	public void turnFightsZombies() {
		addZombie(10, 10);
		Survivor survivor = addSurvivor(10, 10);
		survivor.setWeapon(new Axe());
		turn.play();
		assertTrue(items.getZombies().isEmpty());
	}
	
	@Test
	public void turnInfectsSurvivors() {
		addZombie(10, 10);
		addSurvivor(10, 10);
		turn.play();
		assertTrue(items.getSurvivors().isEmpty());
	}
	
	private Survivor addSurvivorWithPistol(double x, double y, int bullets) {
		Survivor survivor = addSurvivor(x, y);
		survivor.setGun(createGun(bullets));
		return survivor;
	}
	
	private Survivor addSurvivor(double x, double y) {
		Survivor survivor = new Survivor(new Point(x, y), items.getMap(), "name", 1);
		items.add(survivor);
		return survivor;
	}
	
	private Zombie addZombie(double x, double y) {
		Zombie zombie = new Zombie(new Point(x, y), items.getMap(), items.getZombies());
		items.add(zombie);
		return zombie;
	}
	
	private Pistol createGun(int bullets) {
		items.getInventory().addPistolBullets(bullets);
		return new Pistol(items.getInventory());
	}
	
	private void fightZombiesForSeveralRounds(int rounds) {
		for (int i = 0; i < rounds; i++) {
			turn.fightZombies();
		}
	}

	private ResourceEventHandler deleteSurvivorEvent() {
		return new DeleteSurvivorEventHandlerMock();
	}

	private class DeleteSurvivorEventHandlerMock implements DeleteSurvivorEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(DeleteSurvivorEvent e) {
			handleEvents++;
		}
	}
}
