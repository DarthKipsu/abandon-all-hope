
package abandonallhope.domain;

import abandonallhope.domain.constructions.Cost;
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
	
	@Test
	public void returnsTrueIfEnoughResourcesToBuild() {
		inventory.addMetal(2);
		inventory.addWood(4);
		assertTrue(inventory.enoughResources(new Cost(4, 2)));
	}
	
	@Test
	public void returnsFalseIfNotEnoughResourcesToBuild() {
		inventory.addMetal(2);
		inventory.addWood(4);
		assertFalse(inventory.enoughResources(new Cost(5, 2)));
	}
	
	@Test
	public void returnsFalseIfNotEnoughResourcesToBuild2() {
		inventory.addMetal(2);
		inventory.addWood(4);
		assertFalse(inventory.enoughResources(new Cost(4, 3)));
	}
	
	@Test
	public void payingSubstractsResources() {
		inventory.payResources(new Cost(1, 1));
		assertEquals(-1, inventory.getWood());
		assertEquals(-1, inventory.getMetal());
	}
	
	@Test
	public void noBulletsAfterReset() {
		inventory.addPistolBullets(50);
		inventory.reset();
		assertFalse(inventory.getPistolBullets().notEmpty());
	}
	
	@Test
	public void noGunsAfterReset() {
		inventory.addFireamrs(new Pistol(inventory));
		inventory.reset();
		assertTrue(inventory.getGuns().isEmpty());
	}
	
	@Test
	public void noWeaponsAfterReset() {
		inventory.addWeapons(new Axe());
		inventory.reset();
		assertTrue(inventory.getWeapons().isEmpty());
	}
	
	@Test
	public void noMaterialsAfterReset() {
		inventory.addWood(50);
		inventory.addMetal(50);
		inventory.reset();
		assertEquals(0, inventory.getWood());
		assertEquals(0, inventory.getMetal());
	}
	
}
