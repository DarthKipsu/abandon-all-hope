
package abandonallhope.domain.weapons;

/**
 * Katana is the weapon used by easter egg character Michonne
 * Short range can hit each turn
 * @author kipsu
 */
public class Katana extends Weapon {

	/**
	 * Create a new Katana weapon for Michonne.
	 */
	public Katana() {
		super(5, 1);
	}

	@Override
	public String toString() {
		return "katana";
	}
	
}
