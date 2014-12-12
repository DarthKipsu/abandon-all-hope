
package abandonallhope.domain.weapons;

/**
 * Bullet container for a firearm.
 * @author kipsu
 */
public class Magazine {
	
	private int bullets;

	/**
	 * Create a new container for bullets of one weapon type
	 */
	public Magazine() {
		bullets = 0;
	}
	
	/**
	 * Add more bullets to magazine
	 * @param amount amount of bullets to add
	 */
	public void add(int amount) {
		bullets += amount;
	}
	
	/**
	 * Decrese bullets by one
	 */
	public void use() {
		bullets--;
	}
	
	/**
	 * Returns true if there are bullets left in this magazine
	 * @return true if bullets left
	 */
	public boolean notEmpty() {
		return bullets > 0;
	}

	public int getBullets() {
		return bullets;
	}
	
}
