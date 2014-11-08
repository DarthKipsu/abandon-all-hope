
package abandonallhope.logic;

import abandonallhope.domain.Map;
import abandonallhope.domain.Person;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.Zombie;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Game implements EventHandler {
	
	private Map map;
	private List<Person> zombies;
	private List<Person> survivors;

	public Game(int mapSize) {
		map = new Map(mapSize, mapSize);
		zombies = new ArrayList<>();
		survivors = new ArrayList<>();
		addFigures(5, 1);
	}

	public List<Person> getSurvivors() {
		return survivors;
	}

	public List<Person> getZombies() {
		return zombies;
	}

	@Override
	public void handle(Event t) {
		moveZombies();
	}

	private void moveZombies() {
		Random random = new Random();
		for (Person zombie : zombies) {
			zombie.move(random.nextInt(3) - 1, random.nextInt(3) - 1);
			System.out.println(zombie);
		}
	}
	
	// Only here for now...
	public void addFigures(int zombie, int survivor) {
		Random random = new Random();
		for (int i = 0; i < zombie; i++) {
			zombies.add(new Zombie(new Point(random.nextInt(500), random.nextInt(500)), map));
		}
		for (int i = 0; i < survivor; i++) {
			survivors.add(new Survivor(new Point(random.nextInt(500), random.nextInt(500)), map));
		}
	}
	
}