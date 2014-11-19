
package abandonallhope.domain.weapons;

/**
 * Subclass of weapon and a superclass for all firearm type of weapons
 * @author kipsu
 */
public class Firearm extends Weapon {
	
	private Magazine bullets;

	/**
	 * Constructor for firearm weapon
	 * @param range weapon shooting range
	 * @param reloadTime weapon reload time
	 * @param bullets Bullet magazine combined with this weapon type
	 */
	public Firearm(double range, int reloadTime, Magazine bullets) {
		super(range, reloadTime);
		this.bullets = bullets;
	}

	@Override
	public void use() {
		if (canBeUsed()) {
			roundsToUse = reloadTime;
			bullets.use();
		}
	}

	@Override
	public boolean canBeUsed() {
		return super.canBeUsed() && bullets.notEmpty();
	}
	
}
