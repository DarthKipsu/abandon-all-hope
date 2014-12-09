package abandonallhope.domain;

import abandonallhope.domain.constructions.Cost;
import abandonallhope.domain.weapons.Firearm;
import abandonallhope.domain.weapons.Magazine;
import abandonallhope.domain.weapons.Weapon;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains inventory for the player resources.
 *
 * @author kipsu
 */
public class Inventory {

	private Magazine pistolBullets;
	private List<Weapon> weapons;
	private List<Firearm> guns;
	private int wood;
	private int metal;

	/**
	 * Create an empty inventory.
	 */
	public Inventory() {
		pistolBullets = new Magazine();
		weapons = new ArrayList<>();
		guns = new ArrayList<>();
		wood = 0;
		metal = 0;
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

	public int getMetal() {
		return metal;
	}

	public int getWood() {
		return wood;
	}

	/**
	 * Adds bullets for pistol firearm.
	 * @param amount 
	 */
	public void addPistolBullets(int amount) {
		pistolBullets.add(amount);
	}

	/**
	 * Adds one or more new melee weapons.
	 * @param weapons 
	 */
	public void addWeapons(Weapon... weapons) {
		for (Weapon weapon : weapons) {
			this.weapons.add(weapon);
		}
	}

	/**
	 * Adds one or more firearms
	 * @param firearms 
	 */
	public void addFireamrs(Firearm... firearms) {
		for (Firearm firearm : firearms) {
			this.guns.add(firearm);
		}
	}
	
	/**
	 * Adds new wood to player resources
	 * @param amount 
	 */
	public void addWood(int amount) {
		wood += amount;
	}
	
	/**
	 * Adds new metal to player resources
	 * @param amount 
	 */
	public void addMetal(int amount) {
		metal += amount;
	}
	
	/**
	 * Check if player has enough resources for this purchase.
	 * @param cost cost of the purchase
	 * @return true if enough resources
	 */
	public boolean enoughResources(Cost cost) {
		return wood - cost.getWood() >= 0 && metal - cost.getMetal() >= 0;
	}
	
	/**
	 * Check if inventory already has this type of weapon.
	 * @param weapon weapon whose type is checked
	 * @return true if similar weapon already exists
	 */
	public boolean containsWeapon(Weapon weapon) {
		for (Weapon comparator : weapons) {
			if (weapon.getClass() == comparator.getClass()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if inventory already has this type of firearm.
	 * @param firearm firearm whose type is checked
	 * @return true if similar firearm already exists
	 */
	public boolean containsFirearm(Firearm firearm) {
		for (Firearm comparator : guns) {
			if (firearm.getClass() == comparator.getClass()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes resources used to pay a building
	 * @param cost cost of the building
	 */
	public void payResources(Cost cost) {
		metal -= cost.getMetal();
		wood -= cost.getWood();
	}
}
