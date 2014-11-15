
package abandonallhope.logic;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.items.Axe;
import abandonallhope.domain.items.Weapon;
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
		addSurvivor(10, 10);
		addZombie(8.5, 8.5);
		game.getSurvivors().get(0).setWeapon(new Axe());
		game.fightZombies();
		assertTrue(game.getZombies().isEmpty());
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
		addSurvivor(10, 10);
		addZombie(8.5, 8.5);
		game.getSurvivors().get(0).setWeapon(new Axe());
		game.getSurvivors().get(0).getWeapon().use();
		game.fightZombies();
		assertFalse(game.getZombies().isEmpty());
	}
	
	private void addSurvivor(double x, double y) {
		Survivor survivor = new Survivor(new Point(x, y), game.getMap());
		game.add(survivor);
	}
	
	private void addZombie(double x, double y) {
		Zombie zombie = new Zombie(new Point(x, y), game.getMap());
		game.add(zombie);
	}
	
}
