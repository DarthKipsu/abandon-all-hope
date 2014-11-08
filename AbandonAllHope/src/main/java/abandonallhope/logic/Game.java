
package abandonallhope.logic;

import abandonallhope.domain.Map;
import abandonallhope.domain.Person;
import abandonallhope.domain.Zombie;
import java.awt.Point;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Game implements EventHandler {
	
	private Map map;
	private List<Person> zombies;

	public Game(int mapSize) {
		map = new Map(mapSize, mapSize);
		zombies = new ArrayList<>();
		addZombies(5);
	}

	@Override
	public void handle(Event t) {
		moveZombies();
	}

	public List<Person> getZombies() {
		return zombies;
	}

	private void moveZombies() {
		Random random = new Random();
		for (Person zombie : zombies) {
			zombie.move(random.nextInt(3) - 1, random.nextInt(3) - 1);
			System.out.println(zombie);
		}
	}
	
	// Only here for now...
	public void addZombies(int amount) {
		Random random = new Random();
		for (int i = 0; i < amount; i++) {
			zombies.add(new Zombie(new Point(random.nextInt(500), random.nextInt(500)), map));
		}
	}
	
}