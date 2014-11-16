
package abandonallhope.domain.items;

public class Weapon {
	private double range;
	protected int reloadTime;
	protected int roundsToUse;

	public Weapon(double range, int reloadTime) {
		this.range = range;
		this.reloadTime = reloadTime;
		roundsToUse = 0;
	}

	public double getRange() {
		return range;
	}
	
	public void use() {
		if (canBeUsed()) {
			roundsToUse = reloadTime;
		}
	}
	
	public boolean canBeUsed() {
		return roundsToUse == 0;
	}
	
	public void decreaseRoundsToUse() {
		if (roundsToUse > 0) {
			roundsToUse--;
		}
	}
	
}