
package abandonallhope.domain;

import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Magazine;
import abandonallhope.domain.weapons.Weapon;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains inventory for the player resources.
 * @author kipsu
 */
public class Inventory {
	
	private Magazine pistolBullets;
	private List<Weapon> weapons;
	private List<Firearm> guns;

	/**
	 * Create empty inventory.
	 */
	public Inventory() {
		pistolBullets = new Magazine();
		weapons = new ArrayList<>();
		guns = new ArrayList<>();
	}

	public Magazine getPistolBullets() {
		return pistolBullets;
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	public List<Firearm> getGuns() {
		return guns;
	}
	
	public void addPistolBullets(int amount) {
		pistolBullets.add(amount);
	}
	
	public void addWeapons(Weapon... weapons) {
		for (Weapon weapon : weapons) {
			this.weapons.add(weapon);
		}
	}
	
	public void addFireamrs(Firearm... firearms) {
		for (Firearm firearm : firearms) {
			this.guns.add(firearm);
		}
	}
}
