
package abandonallhope.logic;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SurvivorSelectorTest {
	
	private SurvivorSelector selector;
	private List<Survivor> survivors;
	
	@Before
	public void setUp() {
		Game game = new Game(30);
		survivors = game.getItems().getSurvivors();
		selector = new SurvivorSelector(survivors);
		survivors.add(new Survivor(new Point(10, 10), game.getItems().getMap(), "name", 1));
		survivors.add(new Survivor(new Point(20, 20), game.getItems().getMap(), "name", 1));
	}
	
	@Test
	public void selectsSurvivorAtTheRightCoordinates() {
		selector.select(10, 10);
		assertTrue(survivors.get(0).isSelected());
		assertFalse(survivors.get(1).isSelected());
	}
	
	@Test
	public void selectSurvivorEvenIfCoordinate4pixelsWrong() {
		selector.select(14, 14);
		assertTrue(survivors.get(0).isSelected());
		assertFalse(survivors.get(1).isSelected());
	}
	
	@Test
	public void selectSurvivor2EvenIfCoordinate4pixelsWrong() {
		selector.select(16, 16);
		assertFalse(survivors.get(0).isSelected());
		assertTrue(survivors.get(1).isSelected());
	}
	
	@Test
	public void dontSelectSurvivorsIfCoordinates5pixelsWrong() {
		selector.select(15, 15);
		assertFalse(survivors.get(0).isSelected());
		assertFalse(survivors.get(1).isSelected());
	}
	
	@Test
	public void unselectSurvivorIfItsAlreadySelected() {
		survivors.get(0).select();
		selector.select(10, 10);
		assertFalse(survivors.get(0).isSelected());
		assertFalse(survivors.get(1).isSelected());
	}
	
	@Test
	public void unselectSurvivor2IfItsAlreadySelected() {
		survivors.get(1).select();
		selector.select(20, 20);
		assertFalse(survivors.get(0).isSelected());
		assertFalse(survivors.get(1).isSelected());
	}
	
	@Test
	public void unselectOtherSurvivorsWhenNewOneIsSelected() {
		survivors.get(0).select();
		selector.select(20, 20);
		assertFalse(survivors.get(0).isSelected());
		assertTrue(survivors.get(1).isSelected());
	}
	
	@Test
	public void unselectOtherSurvivorsWhenNewOneIsSelected2() {
		survivors.get(1).select();
		selector.select(10, 10);
		assertTrue(survivors.get(0).isSelected());
		assertFalse(survivors.get(1).isSelected());
	}
	
	@Test
	public void returnNullIfSurvivorIsSelected() {
		Survivor survivor = selector.select(10, 10);
		assertEquals(null, survivor);
	}
	
	@Test
	public void returnNullIfANewSurvivorGetsSelected() {
		survivors.get(1).select();
		Survivor survivor = selector.select(10, 10);
		assertEquals(null, survivor);
	}
	
	@Test
	public void returnNullIfNoSurvivorsGetSelectedAndNoneAreCurrentlySelected() {
		Survivor survivor = selector.select(25, 25);
		assertEquals(null, survivor);
	}
	
	@Test
	public void returnSurvivorThatWasSelectedAndNoNewSelectionsWereMeade() {
		survivors.get(0).select();
		Survivor survivor = selector.select(25, 25);
		assertEquals(survivors.get(0), survivor);
	}
	
	@Test
	public void returnSurvivor2ThatWasSelectedAndNoNewSelectionsWereMeade() {
		survivors.get(1).select();
		Survivor survivor = selector.select(25, 25);
		assertEquals(survivors.get(1), survivor);
	}
	
	@Test
	public void deselectsSurvivors() {
		survivors.get(0).select();
		selector.unselectAll();
		assertFalse(survivors.get(0).isSelected());
	}
	
}
