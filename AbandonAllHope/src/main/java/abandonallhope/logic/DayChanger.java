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
 *
 * @author kipsu
 */
public class DayChanger {

	private static final Random RANDOM = new Random(42);
	private static final Point[] CITIES = new Point[]{
		new Point(100, 0),
		new Point(0, 200),
		new Point(100, 500)};
	private static Game game;
	private static Map map;
	
	/**
	 * Game day counter
	 */
	public static int day;

	/**
	 * Give game object to game changer so new survivors and zombies can be
	 * added.
	 *
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
		day = 1;
		addDayOneSurvivors();
		addZombies(CITIES[1]);
		addResources();
	}

	/**
	 * Add zombies for the next day.
	 */
	public static void nextDay() {
		day++;
		addZombies(CITIES[RANDOM.nextInt(3)]);
	}

	private static void addDayOneSurvivors() {
		String[] name = new String[]{"Uolevi", "Maija", "Bob", "Eve", "Alice"};
		for (int i = 0; i < name.length; i++) {
			Survivor survivor = new Survivor(pointInsideCamp(), map, name[i], i + 1);
			addWeapons(i, survivor);
			game.add(survivor);
		}
	}

	private static Point pointInsideCamp() {
		return new Point(325 + RANDOM.nextInt(50), 275 + RANDOM.nextInt(50));
	}

	private static void addWeapons(int i, Survivor survivor) {
		survivor.setWeapon(new Axe());
		addFirearm(i, survivor);
	}

	private static void addFirearm(int i, Survivor survivor) {
		if (i % 3 == 0) {
			survivor.setGun(new Pistol(game.getInventory()));
		}
	}

	private static void addZombies(Point point) {
		int multiplier = RANDOM.nextInt(2) + 1;
		for (int i = 0; i < day * multiplier + day; i++) {
			game.add(createNewZombie(point));
		}
	}

	private static Zombie createNewZombie(Point point) {
		Point randomLocation = createRandomLocation();
		Point locationNearPoint = createLocationNearPoint(point);
		return new Zombie(
				RANDOM.nextDouble() < 0.25 ? randomLocation : locationNearPoint,
				map, game.getZombies());
	}

	protected static Point createRandomLocation() {
		double side = RANDOM.nextDouble();
		if (side < 0) {
			return new Point(RANDOM.nextInt(500), RANDOM.nextDouble() < 0 ? 0 : 499);
		} else {
			return new Point(RANDOM.nextDouble() < 0 ? 0 : 499, RANDOM.nextInt(500));
		}
	}

	protected static Point createLocationNearPoint(Point point) {
		if (point.x == 0) {
			return new Point(0, point.y + RANDOM.nextInt(100));
		} else {
			return new Point(point.x + RANDOM.nextInt(100), point.y);
		}
	}

	private static void addResources() {
		game.getInventory().addPistolBullets(10);
		game.getInventory().addWood(75);
		game.getInventory().addMetal(25);
	}

}
