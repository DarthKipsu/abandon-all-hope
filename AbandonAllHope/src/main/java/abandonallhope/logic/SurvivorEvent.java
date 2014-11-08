
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
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
		for (Survivor survivor : survivors) {
			int survX = survivor.getLocation().x;
			int survY = survivor.getLocation().y;
			if (Math.abs(event.getX() - survX) < 10 && Math.abs(event.getY() - survY) < 10) {
				survivor.select();
			} else {
				survivor.unselect();
			}
		}
	}
	
}
