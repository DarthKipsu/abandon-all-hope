
package abandonallhope.logic;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Magazine;
import abandonallhope.domain.weapons.Pistol;
import abandonallhope.domain.weapons.Weapon;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
	
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game(30);
	}
	
	@Test
	public void addsCorrectAmountOfSurvivorsOneByOne() {
		for (int i = 0; i < 5; i++) {
			game.add(new Survivor(new Point(10,10), game.getMap()));
		}
		assertEquals(5, game.getSurvivors().size());
	}
	
	@Test
	public void addsCorrectAmountOfSurvivorsAtOnce() {
		game.add(new Survivor(new Point(10,10), game.getMap()),
				new Survivor(new Point(10,10), game.getMap()),
				new Survivor(new Point(10,10), game.getMap()));
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
	public void returnsMapWithGetMap() {
		assertEquals("30.0 x 30.0", game.getMap().toString());
	}
	
	@Test
	public void movesZombies() {
		Zombie zombie = new Zombie(new Point(20, 20), game.getMap());
		game.add(new Survivor(new Point(10, 10), game.getMap()));
		game.add(zombie);
		game.moveZombies();
		assertEquals(new Point(20 - zombie.getSpeed(), 20 - zombie.getSpeed()), zombie.getLocation());
	}
	
	@Test
	public void movesSurvivors() {
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap());
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
		addZombie(8.1, 8.1);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void infectSSurvivorsTooCloseToZombies2() {
		addSurvivor(10, 10);
		addZombie(11.9, 11.9);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void dontInfectSurvivorsFarEnoughFromZombies() {
		addSurvivor(10, 10);
		addZombie(7.9, 7.9);
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
		addZombie(40, 40);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		game.fightZombies();
		assertEquals(1, game.getBullets().size());
	}
	
	@Test
	public void doesNotShootABulletIfNotCloseEnoughToZombieAndHasBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(41, 41);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(1));
		game.fightZombies();
		assertEquals(0, game.getBullets().size());
	}
	
	@Test
	public void doesNotShootABulletIfCloseEnoughToZombieButHasNoBulletsLeft() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(40, 40);
		survivor.setWeapon(new Axe());
		survivor.setGun(createGun(0));
		game.fightZombies();
		assertEquals(0, game.getBullets().size());
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
		Magazine magazine = new Magazine();
		magazine.add(1);
		survivor.setGun(new Pistol(magazine));
		game.fightZombies();
		assertEquals(0, magazine.getBullets());
	}
	
	@Test
	public void gunReloadsEachTurn() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setGun(createGun(2));
		survivor.getGun().use();
		fightZombiesForSeveralRounds(600);
		assertTrue(survivor.getGun().canBeUsed());
	}
	
	@Test
	public void dontRemoveBulletsWhenTheyHaveNotYetMovedTheirMaxDistance() {
		Survivor survivor = addSurvivor(5, 5);
		addZombie(5, 5);
		survivor.setGun(createGun(1));
		fightZombiesForSeveralRounds(34);
		assertEquals(1, game.getBullets().size());
	}
	
	private Survivor addSurvivor(double x, double y) {
		Survivor survivor = new Survivor(new Point(x, y), game.getMap());
		game.add(survivor);
		return survivor;
	}
	
	private void addZombie(double x, double y) {
		Zombie zombie = new Zombie(new Point(x, y), game.getMap());
		game.add(zombie);
	}
	
	private Pistol createGun(int bullets) {
		Magazine magazine = new Magazine();
		magazine.add(bullets);
		return new Pistol(magazine);
	}
	
	private void fightZombiesForSeveralRounds(int rounds) {
		for (int i = 0; i < rounds; i++) {
			game.fightZombies();
		}
	}
	
}
