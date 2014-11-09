
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import java.util.List;

public class SurvivorSelector {
	private List<Survivor> survivors;
	private Survivor newSelection;
	private Survivor oldSelection;
	private double clickX;
	private double clickY;

	public SurvivorSelector(List<Survivor> survivors) {
		this.survivors = survivors;
	}
	
	public Survivor select(double clickX, double clickY) {
		resetVariables(clickX, clickY);
		checkIfSurvivorsNeedToBeSelected();
		if (survivorHasBeenSelected()) {
			unselectOtherSurvivors(newSelection);
			return null;
		}
		return oldSelection;
	}

	private void resetVariables(double clickX1, double clickY1) {
		newSelection = null;
		oldSelection = null;
		this.clickX = clickX1;
		this.clickY = clickY1;
	}

	private void checkIfSurvivorsNeedToBeSelected() {
		for (Survivor survivor : survivors) {
			if (eventCloseToSurvivor(survivor)) {
				changeSelectionStatusFor(survivor);
				break;
			} else if (survivor.isSelected()) {
				oldSelection = survivor;
			}
		}
	}

	private boolean eventCloseToSurvivor(Survivor survivor) {
		int survX = survivor.getLocation().x;
		int survY = survivor.getLocation().y;
		return Math.abs(clickX - survX) < 5 && Math.abs(clickY - survY) < 5;
	}

	private void changeSelectionStatusFor(Survivor survivor) {
		if (!survivor.isSelected()) {
			survivor.select();
			newSelection = survivor;
		} else {
			survivor.unselect();
		}
	}

	private boolean survivorHasBeenSelected() {
		return newSelection != null;
	}

	private void unselectOtherSurvivors(Survivor selected) {
		for (Survivor survivor : survivors) {
			if (!survivor.equals(selected) && survivor.isSelected()) {
				survivor.unselect();
			}
		}
	}
}
