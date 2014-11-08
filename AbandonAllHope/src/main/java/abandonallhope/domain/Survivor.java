
package abandonallhope.domain;

import java.awt.Point;

public class Survivor extends Person {

	public Survivor(Point startingLocation, Map map) {
		super(startingLocation, map);
		this.speed = 2;
	}
	
}
