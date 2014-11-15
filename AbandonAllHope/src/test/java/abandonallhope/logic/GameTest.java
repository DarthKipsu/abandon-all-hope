
package abandonallhope.logic;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
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
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap());
		Zombie zombie = new Zombie(new Point(10, 10), game.getMap());
		game.add(survivor);
		game.add(zombie);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void infectSSurvivorsTooCloseToZombies() {
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap());
		Zombie zombie = new Zombie(new Point(8.1, 8.1), game.getMap());
		game.add(survivor);
		game.add(zombie);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void infectSSurvivorsTooCloseToZombies2() {
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap());
		Zombie zombie = new Zombie(new Point(11.9, 11.9), game.getMap());
		game.add(survivor);
		game.add(zombie);
		game.infectSurvivors();
		assertTrue(game.getSurvivors().isEmpty());
	}
	
	@Test
	public void dontInfectSurvivorsFarEnoughFromZombies() {
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap());
		Zombie zombie = new Zombie(new Point(7.9, 7.9), game.getMap());
		game.add(survivor);
		game.add(zombie);
		game.infectSurvivors();
		assertEquals(1, game.getSurvivors().size());
	}
	
	@Test
	public void createANewZombieAfterInfection() {
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap());
		Zombie zombie = new Zombie(new Point(10, 10), game.getMap());
		game.add(survivor);
		game.add(zombie);
		game.infectSurvivors();
		assertEquals(2, game.getZombies().size());
	}
	
}
