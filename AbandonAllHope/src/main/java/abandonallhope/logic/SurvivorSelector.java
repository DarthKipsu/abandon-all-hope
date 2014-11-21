
package abandonallhope.logic;

import abandonallhope.domain.Survivor;
import java.util.List;

/**
 * Handles the logic behind selecting a survivor from the user interface
 * @author kipsu
 */
public class SurvivorSelector {
	private List<Survivor> survivors;
	private Survivor newSelection;
	private Survivor oldSelection;
	private double clickX;
	private double clickY;

	/**
	 * Create a new survivor selector class
	 * @param survivors list of survivors
	 */
	public SurvivorSelector(List<Survivor> survivors) {
		this.survivors = survivors;
	}
	
	/**
	 * Selects survivor if clicked location contains a survivor. If survivor is 
	 * all ready selected, will move the survivor or select a new survivor
	 * @param clickX x coordinate for the selection
	 * @param clickY y coordinate for the selection
	 * @return Survivor that was previously selected, or null if none was
	 */
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
		double survX = survivor.getLocation().x;
		double survY = survivor.getLocation().y;
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
