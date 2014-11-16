

package abandonallhope.domain;

import abandonallhope.domain.items.*;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SurvivorTest {
	
	private Survivor survivor;
	private double speed;
	private Map map;
	private List<Survivor> survivors;
	
	@Before
	public void setUp() {
		survivors = new ArrayList<>();
		map = new Map(30, survivors);
		survivor = new Survivor(new Point(10, 10), map);
		speed = survivor.getSpeed();
	}
	
	@Test
	public void isInitiallyNotSelected() {
		assertFalse(survivor.isSelected());
	}
	
	@Test
	public void canBeSelected() {
		survivor.select();
		assertTrue(survivor.isSelected());
	}
	
	@Test
	public void canBeUnselected() {
		survivor.select();
		survivor.unselect();
		assertFalse(survivor.isSelected());
	}
	
	@Test
	public void doesNotHaveAWeaponInitially() {
		assertEquals(null, survivor.getWeapon());
	}
	
	@Test
	public void canBeGivenAWeapon() {
		Weapon weapon = new Axe();
		survivor.setWeapon(weapon);
		assertEquals(weapon, survivor.getWeapon());
	}
	
	@Test
	public void doesNotHaveAFireArmInitially() {
		assertEquals(null, survivor.getGun());
	}
	
	@Test
	public void canBeGivenAFirearm() {
		Firearm weapon = new Pistol(new Magazine());
		survivor.setGun(weapon);
		assertEquals(weapon, survivor.getGun());
	}
	
	@Test
	public void occupies2x2Space() {
		assertEquals(new Rectangle2D(9,9,2,2), survivor.occupiedArea());
	}
	
	@Test
	public void movesTowardsDestination() {
		survivor.moveTowards(new Point(14, 14));
		survivor.move();
		assertEquals(new Point(10 + speed, 10 + speed), survivor.getLocation());
	}
	
	@Test
	public void movesTowardsDestination2() {
		survivor.moveTowards(new Point(0, 0));
		moveNtimes(4);
		assertEquals(new Point(10 - speed * 4, 10 - speed * 4), survivor.getLocation());
	}
	
	@Test
	public void doesNotMovePastDestination() {
		survivor.moveTowards(new Point(14, 14));
		moveNtimes(20);
		assertEquals(new Point(14, 14), survivor.getLocation());
	}
	
	@Test
	public void movesUntilDestinationHasBeenReached() {
		survivor.moveTowards(new Point(15, 15));
		moveNtimes(2);
		assertFalse(survivor.hasReachedLocation());
	}
	
	@Test
	public void doesNotMoveFurtherIfHasReachedAsCloseAsItCanGet() {
		survivor.moveTowards(new Point(11.25, 11.25));
		moveNtimes(5);
		assertEquals(new Point(11, 11), survivor.getLocation());
	}
	
	@Test
	public void doesNotMoveOutsideMap() {
		survivor.moveTowards(new Point(10, -10));
		moveNtimes(20);
		assertEquals(new Point(10, 0), survivor.getLocation());
	}
	
	@Test
	public void movesSpeeedSquaresUp() {
		survivor.move(0, -1);
		assertEquals(new Point(10, 10 - speed), survivor.getLocation());
	}
	
	@Test
	public void movesSpeedSquaresDown() {
		survivor.move(0, 1);
		assertEquals(new Point(10, 10 + speed), survivor.getLocation());
	}
	
	@Test
	public void movesSpeedSquaresRight() {
		survivor.move(1, 0);
		assertEquals(new Point(10 + speed, 10), survivor.getLocation());
	}
	
	@Test
	public void movesSpeedSquaresLeft() {
		survivor.move(-1, 0);
		assertEquals(new Point(10 - speed, 10), survivor.getLocation());
	}
	
	@Test
	public void canMoveDiagonally() {
		survivor.move(-1, -1);
		assertEquals(new Point(10 - speed, 10 - speed), survivor.getLocation());
	}
	
	@Test
	public void movesOnlySpeedSquaresUpWithOneMove() {
		survivor.move(0, -20);
		assertEquals(new Point(10, 10 - speed), survivor.getLocation());
	}
	
	@Test
	public void movesCorrectlyAfterSeveralSteps() {
		survivor.move(0, 20);
		survivor.move(50, 10);
		survivor.move(80, -20);
		assertEquals(new Point(10 + speed * 2, 10 + speed), survivor.getLocation());
	}
	
	@Test
	public void printsSurvivorLocationCorrectly() {
		survivor.move(1, 1);
		assertEquals("Survivor location: 10," + (int)(speed * 10) + ",10," + (int)(speed * 10), survivor.toString());
	}
	
	@Test
	public void survivorDoesNotMoveOutsideMap() {
		moveNtimes(-1, -1, 30);
		assertEquals(new Point(0, 0), survivor.getLocation());
	}
	
	@Test
	public void survivorDoesNotMoveOutsideMap2() {
		moveNtimes(1, 1, 50);
		assertEquals(new Point(30, 30), survivor.getLocation());
	}
	
	@Test
	public void doesNotMoveWithoutCoordinates() {
		survivor.move();
		assertEquals(new Point(10, 10), survivor.getLocation());
	}
	
	private void moveNtimes(int x, int y, int n) {
		for (int i = 0; i < n; i++) {
			survivor.move(x, y);
		}
	}
	
	private void moveNtimes(int n) {
		for (int i = 0; i < n; i++) {
			survivor.move();
		}
	}
	
}
