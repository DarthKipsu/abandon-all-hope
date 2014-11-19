
package abandonallhope.domain.weapons;

/**
 * Superclass for weapons
 * Contains methods shared between all usable weapons
 * @author kipsu
 */
public class Weapon {
	private double range;
	protected int reloadTime;
	protected int roundsToUse;

	/**
	 * Constructor for weapon class.
	 * @param range Reach of the weapon
	 * @param reloadTime Reload time of weapon
	 */
	public Weapon(double range, int reloadTime) {
		this.range = range;
		this.reloadTime = reloadTime;
		roundsToUse = 0;
	}

	public double getRange() {
		return range;
	}
	
	/**
	 * Resets the weapon rounds to use
	 * If rounds to use are not 0, does nothing
	 */
	public void use() {
		if (canBeUsed()) {
			roundsToUse = reloadTime;
		}
	}
	
	/**
	 * Returns the value of rounds that need to be waited before weapon can be used
	 * If 0, the weapon can be used again
	 * @return rounds until the weapon can be used again
	 */
	public boolean canBeUsed() {
		return roundsToUse == 0;
	}
	
	/**
	 * If weapon has more than zero rounds to use, decrease them by one.
	 */
	public void decreaseRoundsToUse() {
		if (roundsToUse > 0) {
			roundsToUse--;
		}
	}
	
}