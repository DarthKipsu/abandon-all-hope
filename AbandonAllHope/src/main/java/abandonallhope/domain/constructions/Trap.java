package abandonallhope.domain.constructions;

import abandonallhope.domain.DrawableObject;
import abandonallhope.domain.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

/**
 * Traps are used to kill zombies without survivor help.
 *
 * @author kipsu
 */
public class Trap implements DrawableObject {

	protected Point location;
	private TrapType type;
	private int size;
	private int capacity;
	private int contents;
	private Color color;
	private Cost cost;

	/**
	 * Creates a new trap at given location.
	 *
	 * @param location location of the trap
	 * @param type type of trap
	 */
	public Trap(Point location, TrapType type) {
		this.location = location;
		this.type = type;
		size = type.getSize();
		color = type.getColor();
		capacity = type.getCapacity();
		cost = type.getCost();
		contents = 0;
	}

	@Override
	public Color getColor() {
		return color;
	}

	public Point getLocation() {
		return location;
	}

	public Cost getCost() {
		return cost;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * Returns true if capacity is not full
	 * @return false if capacity is full
	 */
	public boolean hasCapacityLeft() {
		return capacity - contents > 0;
	}

	/**
	 * Decrease capacity by one zombie
	 */
	public void addZombie() {
		contents++;
	}

	@Override
	public Rectangle2D occupiedArea() {
		return new Rectangle2D(location.x, location.y, size, size);
	}

}
