
package abandonallhope.domain.constructions;

/**
 * contains object cost.
 * @author kipsu
 */
public class Cost {
	
	private int wood;
	private int metal;

	/**
	 * Creates a new cost for an object
	 * @param wood cost in wood
	 * @param metal cost in metal
	 */
	public Cost(int wood, int metal) {
		this.wood = wood;
		this.metal = metal;
	}

	public int getWood() {
		return wood;
	}

	public int getMetal() {
		return metal;
	}
	
}
