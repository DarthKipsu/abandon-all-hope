
package abandonallhope.domain;

public class Zombie extends Person {

	public Zombie(Point startingLocation, Map map) {
		super(startingLocation, map);
		this.speed = 0.3;
	}
	
	@Override
	public void move() {
		if (!map.getSurvivors().isEmpty()) {
			Point nearestSurvivor = nearestSurvivor();
			move(nearestSurvivor.x - location.x, nearestSurvivor.y - location.y);
		}
	}
	
	private Point nearestSurvivor() {
		Point nearest = map.getSurvivors().get(0).getLocation();
		double nearesDifference = Double.MAX_VALUE;
		for (Person survivor : map.getSurvivors()) {
			double difference = Math.abs(survivor.getLocation().x - location.x)
					+ Math.abs(survivor.getLocation().y - location.y);
			if (difference < nearesDifference) {
				nearest = survivor.getLocation();
				nearesDifference = difference;
			}
		}
		return nearest;
	}
	
}
