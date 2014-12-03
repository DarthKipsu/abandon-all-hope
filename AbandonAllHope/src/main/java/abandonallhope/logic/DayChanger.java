
package abandonallhope.logic;

import abandonallhope.domain.Map;
import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Pistol;
import java.util.Random;

/**
 * Class to contain logic for a change of a day.
 * @author kipsu
 */
public class DayChanger {
	
	private static final Random r = new Random(42);
	private static final Point[] cities = new Point[]{
		new Point(100, 0),
		new Point(0, 200),
		new Point(100, 500)};
	private static Game game;
	private static Map map;

	/**
	 * Give game object to game changer so new survivors and zombies can be
	 * added.
	 * @param game game where the units will be placed.
	 */
	public static void setGame(Game game) {
		DayChanger.game = game;
		map = game.getMap();
	}
	
	/**
	 * Add survivors and zombies for the games first level.
	 */
	public static void setupDayOne() {
		addDayOneSurvivors();
		addZombies(cities[1]);
		addBullets();
	}
	
	/**
	 * Add zombies for the next day.
	 */
	public static void nextDay() {
		addZombies(cities[r.nextInt(3)]);
	}
	
	private static void addDayOneSurvivors() {
		String[] name = new String[]{"Uolevi", "Maija", "Bob", "Eve", "Alice"};
		for (int i = 0; i < name.length; i++) {
			Point point = new Point(325 + r.nextInt(50), 275 + r.nextInt(50));
			Survivor survivor = new Survivor(point, map, name[i], i + 1);
			addWeapons(i, survivor);
			game.add(survivor);
		}
	}

	private static void addWeapons(int i, Survivor survivor) {
		if (i < 3) {
			survivor.setWeapon(new Axe());
		}
		if (i % 3 == 0) {
			survivor.setGun(new Pistol(game.getInventory()));
		}
	}
	
	private static void addBullets() {
		game.getInventory().addPistolBullets(5);
	}
	
	private static void addZombies(Point point) {
		for (int i = 0; i < game.getDay() * (r.nextInt(5) + 1); i++) {
			Point randomLocation = createRandomLocation();
			Point locationNearPoint = createLocationNearPoint(point);
			Zombie zombie = new Zombie(
					r.nextDouble() < 0.25 ? randomLocation : locationNearPoint,
					map);
			game.add(zombie);
		}
	}
	
	protected static Point createRandomLocation() {
		double side = r.nextDouble();
		if (side < 0) {
			return new Point(r.nextInt(500), r.nextDouble() < 0 ? 0 : 499);
		} else {
			return new Point(r.nextDouble() < 0 ? 0 : 499, r.nextInt(500));
		}
	}
	
	protected static Point createLocationNearPoint(Point point) {
		if (point.x == 0) {
			return new Point(0, point.y + r.nextInt(100));
		} else {
			return new Point(point.x + r.nextInt(100), point.y);
		}
	}
	
}
