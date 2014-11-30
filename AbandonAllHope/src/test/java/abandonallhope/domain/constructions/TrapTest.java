
package abandonallhope.domain.constructions;

import abandonallhope.domain.Map;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TrapTest {
	
	private Trap trap;
	private Map map;
	private List<Trap> traps;
	
	@Before
	public void setUp() {
		List<Survivor> survivors = new ArrayList<>();
		survivors.add(new Survivor(new Point(20,20), map, "name", 1));
		traps = new ArrayList<>();
		map = new Map(30, survivors, new ArrayList<Wall>(), traps);
		new Zombie(new Point(9.8, 9.8), map);
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
		assertEquals(new Rectangle2D(10, 10, 5, 5), trap.occupiedArea());
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
	
	private void createTrap(TrapType type) {
		trap = new Trap(new Point(10, 10), TrapType.BEARIRON);
	}
	
}
