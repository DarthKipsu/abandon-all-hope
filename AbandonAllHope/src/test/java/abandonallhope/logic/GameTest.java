
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.ui.SurvivorEvent;
import java.awt.Point;
import javafx.event.Event;
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
		assertEquals("30 x 30", game.getMap().toString());
	}
	
	@Test
	public void movesZombies() {
		Zombie zombie = new Zombie(new Point(20, 20), game.getMap());
		game.add(new Survivor(new Point(10, 10), game.getMap()));
		game.add(zombie);
		game.moveZombies();
		assertEquals(new Point(19, 19), zombie.getLocation());
	}
	
	@Test
	public void movesSurvivors() {
		Survivor survivor = new Survivor(new Point(10, 10), game.getMap());
		survivor.moveTowards(new Point(20, 20));
		game.add(survivor);
		game.moveSurvivors();
		assertEquals(new Point(12, 12), survivor.getLocation());
	}
	
}
