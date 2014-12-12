
package abandonallhope.logic;

import abandonallhope.domain.*;
import abandonallhope.domain.constructions.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemsTest {
	
	private Items items;
	
	@Before
	public void setUp() {
		Game game = new Game(500);
		items = game.getItems();
	}
	
	@Test
	public void emptiesTraps() {
		Trap trap = new Trap(null, TrapType.BEARIRON);
		trap.addZombie();
		items.add(trap);
		items.emptyTraps();
		assertTrue(trap.hasCapacityLeft());
	}
	
	@Test
	public void clearsZombies() {
		items.add(new Zombie(null, null, null));
		items.reset();
		assertTrue(items.getZombies().isEmpty());
	}
	
	@Test
	public void clearsWalls() {
		items.add(new Wall(WallType.WOODEN, Wall.Orientation.VERTICAL, null));
		items.reset();
		assertTrue(items.getWalls().isEmpty());
	}
	
	@Test
	public void clearsTraps() {
		items.add(new Trap(null, TrapType.PIT));
		items.reset();
		assertTrue(items.getTraps().isEmpty());
	}
	
	@Test
	public void emptiesInventory() {
		items.getInventory().addWood(50);
		items.reset();
		assertEquals(0, items.getInventory().getWood());
	}
	
}
