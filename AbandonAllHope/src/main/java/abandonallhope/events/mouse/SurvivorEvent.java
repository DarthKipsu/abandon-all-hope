package abandonallhope.events.mouse;

import abandonallhope.domain.Point;
import abandonallhope.domain.Survivor;
import abandonallhope.logic.Items;
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
	 * @param items containing the survivors
	 */
	public SurvivorEvent(Items items) {
		survivors = items.getSurvivors();
	}

	@Override
	public void handle(MouseEvent event) {
		Survivor moving = getSelectedSurvivor(event);
		if (moving != null) {
			moveSurvivor(moving, event);
		}
	}

	private Survivor getSelectedSurvivor(MouseEvent event) {
		Survivor moving = new SurvivorSelector(survivors).select(event.getX(), event.getY());
		return moving;
	}

	private void moveSurvivor(Survivor moving, MouseEvent event) {
		moving.moveTowards(new Point(event.getX(), event.getY()));
	}

}
