
package abandonallhope.domain;

import java.awt.Point;

public class Survivor extends Person {
	
	private boolean selected;

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
	
}
