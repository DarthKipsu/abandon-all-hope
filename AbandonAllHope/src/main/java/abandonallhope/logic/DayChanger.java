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

	private static final Random random = new Random(42);
	private static final Point[] nearByCities = new Point[]{
		new Point(100, 0),
		new Point(0, 200),
		new Point(100, 500)};
	private static Game game;
	private static Map map;
	
	/**
	 * Game day
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
		addZombies(nearByCities[1]);
		addResources();
	}

	/**
	 * Add zombies for the next day.
	 */
	public static void nextDay() {
		day++;
		addZombies(nearByCities[random.nextInt(3)]);
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
		return new Point(325 + random.nextInt(50), 275 + random.nextInt(50));
	}

	private static void addWeapons(int i, Survivor survivor) {
		addMeleeWeapon(i, survivor);
		addFirearm(i, survivor);
	}

	private static void addMeleeWeapon(int i, Survivor survivor) {
		if (i < 3) {
			survivor.setWeapon(new Axe());
		}
	}

	private static void addFirearm(int i, Survivor survivor) {
		if (i % 3 == 0) {
			survivor.setGun(new Pistol(game.getInventory()));
		}
	}

	private static void addZombies(Point point) {
		for (int i = 0; i < day * (random.nextInt(5) + 1); i++) {
			game.add(createNewZombie(point));
		}
	}

	private static Zombie createNewZombie(Point point) {
		Point randomLocation = createRandomLocation();
		Point locationNearPoint = createLocationNearPoint(point);
		return new Zombie(
				random.nextDouble() < 0.25 ? randomLocation : locationNearPoint,
				map, game.getZombies());
	}

	protected static Point createRandomLocation() {
		double side = random.nextDouble();
		if (side < 0) {
			return new Point(random.nextInt(500), random.nextDouble() < 0 ? 0 : 499);
		} else {
			return new Point(random.nextDouble() < 0 ? 0 : 499, random.nextInt(500));
		}
	}

	protected static Point createLocationNearPoint(Point point) {
		if (point.x == 0) {
			return new Point(0, point.y + random.nextInt(100));
		} else {
			return new Point(point.x + random.nextInt(100), point.y);
		}
	}

	private static void addResources() {
		game.getInventory().addPistolBullets(5);
		game.getInventory().addWood(20);
		game.getInventory().addMetal(5);
	}

}
