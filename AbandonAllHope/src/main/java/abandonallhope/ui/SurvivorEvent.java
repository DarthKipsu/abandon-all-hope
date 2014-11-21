
package abandonallhope.ui;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.logic.Game;
import abandonallhope.logic.SurvivorSelector;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Handles the mouse event used to select survivors
 * @author kipsu
 */
public class SurvivorEvent implements EventHandler<MouseEvent> {
	
	private List<Survivor> survivors;

	/**
	 * Create a new mouse event for tracking survivor selector
	 * @param game 
	 */
	public SurvivorEvent(Game game) {
		survivors = game.getSurvivors();
	}

	/**
	 * Handles a mouse click moving survivor if one is selected and selecting one
	 * if not or if selecting a new survivor, using SurvivorSelector class.
	 * @param event 
	 */
	@Override
	public void handle(MouseEvent event) {
		Survivor moving = new SurvivorSelector(survivors).select(event.getX(), event.getY());
		if (moving != null) {
			moveSurvivor(moving, event);
		}
	}

	private void moveSurvivor(Survivor oldSelection, MouseEvent event) {
		oldSelection.moveTowards(new Point(event.getX(), event.getY()));
	}
	
}
