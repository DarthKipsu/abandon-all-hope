
package abandonallhope.domain.constructions;

import javafx.scene.paint.Color;

/**
 * Contains the details of each trap type
 * @author kipsu
 */
public enum TrapType {
	
	BEARIRON(3, 1, Color.SANDYBROWN, new Cost(0, 1)),
	PIT(10, 4, Color.SIENNA, new Cost(8, 4));
	
	private final int size;
	private final int capacity;
	private final Color color;
	private final Cost cost;

	private TrapType(int size, int capacity, Color color, Cost cost) {
		this.size = size;
		this.capacity = capacity;
		this.color = color;
		this.cost = cost;
	}

	public int getCapacity() {
		return capacity;
	}

	public Color getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

	public Cost getCost() {
		return cost;
	}
	
}
