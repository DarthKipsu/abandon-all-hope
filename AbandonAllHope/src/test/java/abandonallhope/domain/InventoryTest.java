
package abandonallhope.domain;

import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Magazine;
import abandonallhope.domain.weapons.Pistol;
import abandonallhope.domain.weapons.Weapon;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTest {
	
	private Inventory inventory;
	
	@Before
	public void setUp() {
		inventory = new Inventory();
	}
	
	@Test
	public void testPistolBullets() {
		inventory.addPistolBullets(5);
		assertEquals(5, inventory.getPistolBullets().getBullets());
	}
	
	@Test
	public void testWeaponsAdding(){
		Axe axe = new Axe();
		inventory.addWeapons(axe);
		assertEquals(axe, inventory.getWeapons().get(0));
	}
	
	@Test
	public void testWeaponsAdding2(){
		inventory.addWeapons(new Axe(), new Axe());
		assertEquals(2, inventory.getWeapons().size());
	}
	
	@Test
	public void testFirearmsAdding() {
		Firearm firearm = new Pistol(inventory);
		inventory.addFireamrs(firearm);
		assertEquals(firearm, inventory.getGuns().get(0));
	}
	
	@Test
	public void testFirearmsAdding2() {
		inventory.addFireamrs(new Pistol(inventory), new Pistol(inventory));
		assertEquals(2, inventory.getGuns().size());
	}
	
}
