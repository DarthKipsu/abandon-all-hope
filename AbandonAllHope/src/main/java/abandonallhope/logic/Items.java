
package abandonallhope.logic;

import abandonallhope.domain.Inventory;
import abandonallhope.domain.Map;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import abandonallhope.domain.constructions.Trap;
import abandonallhope.domain.constructions.Wall;
import abandonallhope.domain.weapons.Bullet;
import java.util.ArrayList;
import java.util.List;

/**
 * Game items contains information about the items in game at any moment.
 */
public class Items {

	private Map map;
	private Inventory inventory;
	
	private List<Zombie> zombies;
	private List<Survivor> survivors;
	private List<Bullet> bullets;
	private List<Wall> walls;
	private List<Trap> traps;

	/**
	 * Create new game items container.
	 * @param mapSize size of map to be created.
	 * @param game Game to be connected with the Map.
	 */
	public Items(int mapSize) {
		inventory = new Inventory();
		zombies = new ArrayList<>();
		survivors = new ArrayList<>();
		bullets = new ArrayList<>();
		walls = new ArrayList<>();
		traps = new ArrayList<>();
		map = new Map(mapSize, this);
	}

	public List<Survivor> getSurvivors() {
		return survivors;
	}

	public List<Zombie> getZombies() {
		return zombies;
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public List<Trap> getTraps() {
		return traps;
	}

	public Map getMap() {
		return map;
	}

	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Add one survivor to the game
	 * @param survivor
	 */
	public void add(Survivor survivor) {
		survivors.add(survivor);
	}

	/**
	 * add one zombie to the game
	 * @param zombie
	 */
	public void add(Zombie zombie) {
		this.zombies.add(zombie);
	}

	/**
	 * Add a wall to the game.
	 * @param wall
	 */
	public void add(Wall wall) {
		walls.add(wall);
	}

	/**
	 * Add a trap to the game.
	 * @param trap
	 */
	public void add(Trap trap) {
		traps.add(trap);
	}

	/**
	 * Empties all traps in game.
	 */
	public void emptyTraps() {
		for (Trap trap : traps) {
			trap.empty();
		}
	}

	/**
	 * Checks if only trapped zombies are left in game.
	 * @return true if only trapped zombies are remaining.
	 */
	public boolean zombiesCleared() {
		for (Zombie zombie : zombies) {
			if (!zombie.isTrapped()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Clears all items in game.
	 */
	public void reset() {
		zombies.clear();
		walls.clear();
		traps.clear();
		inventory.reset();
	}
	
}
