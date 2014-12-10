
package abandonallhope.domain.constructions;

import abandonallhope.domain.*;
import abandonallhope.logic.Game;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TrapTest {
	
	private Trap trap;
	
	@Before
	public void setUp() {
		Game game = new Game(30);
		game.getSurvivors().add(new Survivor(new Point(20,20), game.getMap(), "name", 1));
		new Zombie(new Point(9.8, 9.8), game.getMap(), new ArrayList<Zombie>());
	}
	
	@Test
	public void returnsCorrectColor() {
		createTrap(TrapType.BEARIRON);
		assertEquals(Color.SANDYBROWN, trap.getColor());
	}
	
	@Test
	public void returnsCorrectLocation() {
		createTrap(TrapType.BEARIRON);
		assertEquals(new Point(10, 10), trap.getLocation());
	}
	
	@Test
	public void returnsCorrectOccupatedArea() {
		createTrap(TrapType.BEARIRON);
		assertEquals(new Rectangle2D(10, 10, 4, 4), trap.occupiedArea());
	}
	
	@Test
	public void returnsCorrectCost() {
		createTrap(TrapType.BEARIRON);
		assertEquals(0, trap.getCost().getWood());
		assertEquals(1, trap.getCost().getMetal());
	}
	
	@Test
	public void hasCapacityInTheBeginning() {
		createTrap(TrapType.BEARIRON);
		assertTrue(trap.hasCapacityLeft());
	}
	
	@Test
	public void doesNotHAveCapacityAfterFilledWithZombies() {
		createTrap(TrapType.BEARIRON);
		trap.addZombie();
		assertFalse(trap.hasCapacityLeft());
	}
	
	@Test
	public void locationCanBeChanged() {
		createTrap(TrapType.BEARIRON);
		trap.setLocation(new Point(20, 20));
		assertEquals(new Point(20, 20), trap.getLocation());
	}
	
	@Test
	public void trapCanBeEmptied() {
		createTrap(TrapType.BEARIRON);
		trap.addZombie();
		trap.empty();
		assertTrue(trap.hasCapacityLeft());
	}
	
	private void createTrap(TrapType type) {
		trap = new Trap(new Point(10, 10), TrapType.BEARIRON);
	}
	
}
