
package abandonallhope.ui;

import abandonallhope.domain.Survivor;
import abandonallhope.logic.Game;
import abandonallhope.logic.SurvivorSelector;
import java.awt.Point;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SurvivorEvent implements EventHandler<MouseEvent> {
	
	private List<Survivor> survivors;

	public SurvivorEvent(Game game) {
		survivors = game.getSurvivors();
	}

	@Override
	public void handle(MouseEvent event) {
		Survivor moving = new SurvivorSelector(survivors).select(event.getX(), event.getY());
		if (moving != null) {
			moveSurvivor(moving, event);
		}
	}

	private void moveSurvivor(Survivor oldSelection, MouseEvent event) {
		oldSelection.moveTowards(new Point((int)event.getX(), (int)event.getY()));
	}
	
}
