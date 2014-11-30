
package abandonallhope.domain.weapons;

/**
 * Axe hand held weapon
 * Short reach & medium reload time
 * @author kipsu
 */
public class Axe extends Weapon {

	/**
	 * Constructor for Axe weapon
	 */
	public Axe() {
		super(4, 120);
	}

	@Override
	public String toString() {
		return "Axe";
	}
	
}