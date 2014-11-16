
package abandonallhope.domain.items;

public class Firearm extends Weapon {
	
	private Magazine bullets;

	public Firearm(double range, int reloadTime, Magazine bullets) {
		super(range, reloadTime);
		this.bullets = bullets;
	}

	@Override
	public void use() {
		if (canBeUsed()) {
			roundsToUse = reloadTime;
			bullets.use();
		}
	}

	@Override
	public boolean canBeUsed() {
		return super.canBeUsed() && bullets.notEmpty();
	}
	
}
