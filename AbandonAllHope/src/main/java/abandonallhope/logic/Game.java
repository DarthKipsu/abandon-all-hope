
package abandonallhope.logic;

import abandonallhope.domain.Map;
import abandonallhope.domain.Person;
import abandonallhope.domain.Zombie;
import java.awt.Point;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	
	private Map map;
	private List<Person> zombies;

	public Game(int mapSize) {
		map = new Map(mapSize, mapSize);
		zombies = new ArrayList<>();
	}
	
	public void run() {
		while (true) {
			moveZombies();
			try {
				sleep(200);
			} catch (InterruptedException ex) {
			}
		}
	}

	private void moveZombies() {
		Random random = new Random();
		for (Person zombie : zombies) {
			zombie.move(random.nextInt(2) - 1, random.nextInt(2) - 1);
			System.out.println(zombie);
		}
	}
	
	// Only here for now...
	public void addZombies(int amount) {
		Random random = new Random();
		for (int i = 0; i < amount; i++) {
			zombies.add(new Zombie(new Point(random.nextInt(500), random.nextInt(500))));
		}
	}
	
}