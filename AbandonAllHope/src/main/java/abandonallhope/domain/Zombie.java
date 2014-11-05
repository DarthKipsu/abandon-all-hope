
package abandonallhope.domain;

import java.awt.Point;

public class Zombie extends Person {

	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map);
		this.speed = 5;
	}
	
}
