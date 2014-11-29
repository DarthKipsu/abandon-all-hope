
package abandonallhope.domain.constructions;

import javafx.scene.paint.Color;

/**
 * Contains the details of each trap type
 * @author kipsu
 */
public enum TrapType {
	
	BEARIRON(5, 1, Color.SANDYBROWN);
	
	private final int size;
	private final int capacity;
	private final Color color;

	private TrapType(int size, int capacity, Color color) {
		this.size = size;
		this.capacity = capacity;
		this.color = color;
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
	
}
