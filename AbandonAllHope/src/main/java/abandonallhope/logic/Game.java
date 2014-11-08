
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
	private List<Survivor> survivors;

	public Game(int mapSize) {
		zombies = new ArrayList<>();
		survivors = new ArrayList<>();
		map = new Map(mapSize, mapSize, survivors);
		addFigures(5, 2);
	}

	public List<Survivor> getSurvivors() {
		return survivors;
	}

	public List<Person> getZombies() {
		return zombies;
	}

	@Override
	public void handle(Event t) {
		moveSurvivors();
		moveZombies();
	}

	private void moveSurvivors() {
		for (Survivor survivor : survivors) {
			survivor.move();
			if (survivor.isSelected()) {
			System.out.println("selected: " + survivor);
			}
		}
	}

	private void moveZombies() {
		for (Person zombie : zombies) {
			zombie.move();
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