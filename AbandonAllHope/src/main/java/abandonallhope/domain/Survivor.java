
package abandonallhope.domain;

import java.awt.Point;

public class Survivor extends Person {
	
	private boolean selected;
	private Point destination;

	public Survivor(Point startingLocation, Map map) {
		super(startingLocation, map);
		this.speed = 2;
		selected = false;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public void select() {
		selected = true;
	}
	
	public void unselect() {
		selected = false;
	}
	
	public void moveTowards(Point destination) {
		this.destination = destination;
	}

	@Override
	public void move() {
		if (destination != null) {
			move(destination.x - location.x, destination.y - location.y);
			if (hasReachedLocation()) {
				destination = null;
			}
		}
	}
	
	protected boolean hasReachedLocation() {
		return Math.abs(location.x - destination.x) < 2
			   && Math.abs(location.y - destination.y) < 2;
	}
	
}
