package abandonallhope.logic;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.domain.weapons.Axe;
import abandonallhope.domain.weapons.Pistol;
import abandonallhope.events.action.NewSurvivorEvent;
import abandonallhope.events.handlers.NewSurvivorEventHandler;
import abandonallhope.events.handlers.ResourceEventHandler;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DayChangerTest {

	private Items items;
	private Turn turn;

	@Before
	public void setUp() {
		Game game = new Game(500);
		turn = game.getTurn();
		items = game.getItems();
		DayChanger.setGame(items, game.getTurn());
	}

	@Test
	public void dayOneStartsWith5Survivors() {
		DayChanger.setupDayOne();
		assertEquals(5, items.getSurvivors().size());
	}

	@Test
	public void dayOneSurvivorsHaveAnAxe() {
		DayChanger.setupDayOne();
		List<Survivor> survivors = items.getSurvivors();
		for (int i = 0; i < 5; i++) {
			assertTrue(survivors.get(0).getWeapon() instanceof Axe);
		}
	}

	@Test
	public void dayOneSurvivors0and3haveAPistol() {
		DayChanger.setupDayOne();
		List<Survivor> survivors = items.getSurvivors();
		assertTrue(survivors.get(0).getGun() instanceof Pistol);
		assertNull(survivors.get(1).getGun());
		assertNull(survivors.get(2).getGun());
		assertTrue(survivors.get(3).getGun() instanceof Pistol);
		assertNull(survivors.get(4).getGun());
	}

	@Test
	public void dayOneSurvivorsAreLocatedInsideTheCamp() {
		DayChanger.setupDayOne();
		List<Survivor> survivors = items.getSurvivors();
		for (Survivor survivor : survivors) {
			assertTrue(survivor.getLocation().x >= 325);
			assertTrue(survivor.getLocation().x < 375);
			assertTrue(survivor.getLocation().y >= 275);
			assertTrue(survivor.getLocation().y < 325);
		}
	}

	@Test
	public void dayOneSurvivorsHaveCorrectIds() {
		DayChanger.setupDayOne();
		List<Survivor> survivors = items.getSurvivors();
		for (int i = 1; i <= survivors.size(); i++) {
			assertEquals(i, survivors.get(i - 1).getId());
		}
	}

	@Test
	public void dayOneStartsWith1To5Zombies() {
		DayChanger.setupDayOne();
		assertTrue(items.getZombies().size() < 6);
		assertTrue(items.getZombies().size() > 0);
	}

	@Test
	public void zombieLocationIsOnTheBorderOfTheMap() {
		DayChanger.setupDayOne();
		Point firstZombieLocation = items.getZombies().get(0).getLocation();
		assertTrue((firstZombieLocation.x == 1 || firstZombieLocation.x == 499) ? true :
				(firstZombieLocation.y == 1 || firstZombieLocation.y == 499));
	}

	@Test
	public void survivorsStartUpWith10Bullets() {
		DayChanger.setupDayOne();
		assertEquals(10, items.getInventory().getPistolBullets().getBullets());
	}
	
	@Test
	public void survivorsStartsWith75Wood() {
		DayChanger.setupDayOne();
		assertEquals(75, items.getInventory().getWood());
	}
	
	@Test
	public void survivorsStartWith25Metal() {
		DayChanger.setupDayOne();
		assertEquals(25, items.getInventory().getMetal());
	}

	@Test
	public void nextDaysHaveCorrectAmountOfZombies() {
		DayChanger.nextDay();
		assertTrue(items.getZombies().size() < 10);
		assertTrue(items.getZombies().size() > 0);
	}

	@Test
	public void randomLocationTestCoordinatesInCorrectIntervals() {
		for (int i = 0; i < 100; i++) {
			Point point = DayChanger.createRandomLocation();
			if (point.x == 1 || point.x == 499) {
				assertTrue(point.y >= 1);
				assertTrue(point.y < 500);
			} else {
				assertTrue(point.x >= 1);
				assertTrue(point.x < 500);
			}
		}
	}

	@Test
	public void locationNearCity0Test() {
		for (int i = 0; i < 100; i++) {
			Point point = DayChanger.createLocationNearPoint(new Point(1, 1));
			assertTrue(point.x >= 1);
			assertTrue(point.x < 101);
			assertTrue(point.y == 1);
		}
	}

	@Test
	public void locationNearCity1Test() {
		for (int i = 0; i < 100; i++) {
			Point point = DayChanger.createLocationNearPoint(new Point(0, 200));
			assertTrue(point.y >= 200);
			assertTrue(point.y < 300);
			assertTrue(point.x == 1);
		}
	}

	@Test
	public void locationNearCity2Test() {
		for (int i = 0; i < 100; i++) {
			Point point = DayChanger.createLocationNearPoint(new Point(50, 499));
			assertTrue(point.x >= 50);
			assertTrue(point.x < 150);
			assertTrue(point.y == 499);
		}
	}
	
	@Test
	public void dayNHasCorrectAmoutnOfZombies() {
		for (int i = 2; i < 50; i++) {
			items.reset();
			DayChanger.day = i - 1;
			DayChanger.nextDay();
			assertTrue(items.getZombies().size() >= i * 2);
			assertTrue(items.getZombies().size() <= i * 3);
		}
	}
	
	@Test
	public void newSurvivorEventTriggersWhenSurvivorIsAddedToGame() {
		NewSurvivorEventHandlerMock res = (NewSurvivorEventHandlerMock) newSurvivorEvent();
		turn.addNewResourceEventHandler(res, "newSurvivor");
		DayChanger.setupDayOne();
		assertEquals(5, res.handleEvents);
	}

	private ResourceEventHandler newSurvivorEvent() {
		return new NewSurvivorEventHandlerMock();
	}

	private class NewSurvivorEventHandlerMock implements NewSurvivorEventHandler {
		public int handleEvents = 0;
		
		@Override
		public void handle(NewSurvivorEvent e) {
			handleEvents++;
		}
	}
}
