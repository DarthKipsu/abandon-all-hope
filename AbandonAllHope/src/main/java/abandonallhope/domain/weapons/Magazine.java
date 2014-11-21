
package abandonallhope.domain.weapons;

/**
 * Bullet container for a firearm.
 * @author kipsu
 */
public class Magazine {
	
	private int bullets;

	public Magazine() {
		bullets = 0;
	}
	
	public void add(int amount) {
		bullets += amount;
	}
	
	public void use() {
		bullets--;
	}
	
	public boolean notEmpty() {
		return bullets > 0;
	}

	public int getBullets() {
		return bullets;
	}
	
}
