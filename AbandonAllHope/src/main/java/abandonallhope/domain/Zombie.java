
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
	
	public Point spreadInfection() {
		for (Survivor survivor : map.getSurvivors()) {
			if (distanceBetweenZombieAnd(survivor) < 2) {
				Point survivorLocation = survivor.getLocation();
				map.getSurvivors().remove(survivor);
				return survivorLocation;
			}
		}
		return null;
	}
	
	private Point nearestSurvivor() {
		Point nearest = map.getSurvivors().get(0).getLocation();
		Double nearesDifference = Double.MAX_VALUE;
		for (Survivor survivor : map.getSurvivors()) {
			double difference = distanceBetweenZombieAnd(survivor);
			if (difference < nearesDifference) {
				nearest = survivor.getLocation();
				nearesDifference = difference;
			}
		}
		return nearest;
	}

	private double distanceBetweenZombieAnd(Survivor survivor) {
		return Math.abs(survivor.getLocation().x - location.x) 
				+ Math.abs(survivor.getLocation().y - location.y);
	}
	
}
