
package abandonallhope.domain.weapons;

/**
 * Pistol firearm weapon
 * Short range and long reload time
 * @author kipsu
 */
public class Pistol extends Firearm {

	/**
	 * Constructor for Pistol type firearm
	 * @param bullets Bullet magazine connected with this type of weapon
	 */
	public Pistol(Magazine bullets) {
		super(35, 600, bullets);
	}
	
}
