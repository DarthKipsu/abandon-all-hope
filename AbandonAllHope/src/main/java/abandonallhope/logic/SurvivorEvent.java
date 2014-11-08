
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import java.awt.Point;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SurvivorEvent implements EventHandler<MouseEvent> {
	
	private List<Survivor> survivors;
	private Survivor newSelection;
	private Survivor oldSelection;

	public SurvivorEvent(Game game) {
		survivors = game.getSurvivors();
	}

	@Override
	public void handle(MouseEvent event) {
		newSelection = null;
		oldSelection = null;
		checkIfSurvivorsNeedToBeSelected(event);
		if (newSelection != null) {
			unselectOtherSurvivors(newSelection);
		} else if (oldSelection != null) {
			moveSurvivor(oldSelection, event);
		}
	}

	private void checkIfSurvivorsNeedToBeSelected(MouseEvent event) {
		for (Survivor survivor : survivors) {
			if (eventCloseToSurvivor(event, survivor)) {
				changeSelectionStatusFor(survivor);
				break;
			} else if (survivor.isSelected()) {
				oldSelection = survivor;
				break;
			}
		}
	}

	private static boolean eventCloseToSurvivor(MouseEvent event, Survivor survivor) {
		int survX = survivor.getLocation().x;
		int survY = survivor.getLocation().y;
		return Math.abs(event.getX() - survX) < 5 && Math.abs(event.getY() - survY) < 5;
	}

	private void changeSelectionStatusFor(Survivor survivor) {
		if (!survivor.isSelected()) {
			survivor.select();
			newSelection = survivor;
		} else {
			survivor.unselect();
		}
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
