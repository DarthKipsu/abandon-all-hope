
package abandonallhope.domain;

import abandonallhope.domain.weapons.*;
import org.junit.Before;
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
	
	@Test
	public void containsPistolWhenPistolIsInInventory() {
		inventory.addFireamrs(new Pistol(inventory));
		assertTrue(inventory.containsFirearm(new Pistol(inventory)));
	}
	
	@Test
	public void doesNotContainPistolWhenPistolIsNotInInventory() {
		assertFalse(inventory.containsFirearm(new Pistol(inventory)));
	}
	
	@Test
	public void doesNotContainPistolWhenSomeOtherWeaponIsInInventory() {
		inventory.addFireamrs(new Firearm(1, 1, null));
		assertFalse(inventory.containsFirearm(new Pistol(inventory)));
	}
	
	@Test
	public void containsAxeWhenAxeIsInInventory() {
		inventory.addWeapons(new Axe());
		assertTrue(inventory.containsWeapon(new Axe()));
	}
	
	@Test
	public void doesNotContainAxeWhenInventoryIsEmpty() {
		assertFalse(inventory.containsWeapon(new Axe()));
	}
	
	@Test
	public void doesNotContainAxeWhenOtherWeaponIsInInventory() {
		inventory.addWeapons(new Weapon(1, 1));
		assertFalse(inventory.containsWeapon(new Axe()));
	}
	
}
