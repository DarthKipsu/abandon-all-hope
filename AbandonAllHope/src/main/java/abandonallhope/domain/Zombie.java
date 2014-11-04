
package abandonallhope.domain;

import java.awt.Point;

public class Zombie extends Person {

	public Zombie(Point startingLocation) {
		super(startingLocation);
		this.speed = 5;
	}
	
}
