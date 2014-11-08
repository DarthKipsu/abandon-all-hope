
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
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
		Survivor newSelection = null;
		Survivor oldSelection = null;
		for (Survivor survivor : survivors) {
			int survX = survivor.getLocation().x;
			int survY = survivor.getLocation().y;
			if (Math.abs(event.getX() - survX) < 5 && Math.abs(event.getY() - survY) < 5) {
				if (!survivor.isSelected()) {
					survivor.select();
					newSelection = survivor;
					break;
				} else {
					survivor.unselect();
				}
			} else if (survivor.isSelected()) {
				oldSelection = survivor;
			}
		}
		if (newSelection != null) {
			unselectOtherSurvivors(newSelection);
		} else if (oldSelection != null) {
			moveSurvivor(oldSelection, event);
		}
	}
	
	private void findSelections(Survivor newSelection, Survivor oldSelection, MouseEvent event) {
		
	}

	private void unselectOtherSurvivors(Survivor selected) {
		for (Survivor survivor : survivors) {
			if (!survivor.equals(selected)) {
				survivor.unselect();
			}
		}
	}

	private void moveSurvivor(Survivor oldSelection, MouseEvent event) {
		oldSelection.moveTowards(new Point((int)event.getX(), (int)event.getY()));
	}
	
}
