package abandonallhope.ui.drawing;

import abandonallhope.domain.DrawableObject;
import abandonallhope.domain.Inventory;
import abandonallhope.domain.constructions.Cost;
import abandonallhope.logic.Game;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * class to draw construction hover shadows while hovering over game field
 * during build mode.
 *
 * @author kipsu
 */
public class ConstructionHoverDrawer extends Drawer {

	private int upperLeftX;
	private int upperLeftY;
	private int wallWidth;
	private int wallHeight;
	private int width;
	private int height;
	private Inventory inventory;
	private Cost buildingCost;

	/**
	 * Creates a new construction hover drawing object, used to draw shadows of
	 * constructions in build mode.
	 *
	 * @param game game object containing game content
	 * @param gc graphics context to draw the object with
	 */
	public ConstructionHoverDrawer(Game game, GraphicsContext gc) {
		super(game, gc);
		inventory = game.getInventory();
		upperLeftX = 0;
		upperLeftY = 0;
	}

	/**
	 * gives the drawer dimensions used to build the hover units
	 * @param buildingDimension
	 */
	public void setConstructionDimensions(Rectangle2D buildingDimension) {
		width = (int) buildingDimension.getWidth();
		height = (int) buildingDimension.getHeight();
		wallWidth = width;
		wallHeight = height;
	}
	
	public void setBuildingCost(Cost cost) {
		buildingCost = cost;
	}

	/**
	 * Updates the coordinates where the shadow wall is drawn to.
	 *
	 * @param x
	 * @param y
	 */
	public void updateUpperLeftCornerCoordinates(int x, int y) {
		upperLeftX = x;
		upperLeftY = y;
	}

	/**
	 * Updates the dimensions used to calculate how long wall units will be drawn
	 * @param width width of the new wall, 0 if built vertically
	 * @param height height of the new wall, 0 if built horizonally
	 */
	public void updateBuildingDimensions(int width, int height) {
		if (width != 0) {
			updateHorizonalDimension(width);
		} else {
			updateVerticalDimension(height);
		}
	}

	/**
	 * Draws the construction shadows of the object player is hovering over game
	 * field.
	 */
	public void drawConstructionShadows() {
		setGraphicsContextAttributes(Color.LIGHTGREEN, 1);
		if (width < 0) {
			gc.strokeRect(upperLeftX + width, upperLeftY, Math.abs(width), height);
		} else if (height < 0) {
			gc.strokeRect(upperLeftX, upperLeftY + height, width, Math.abs(height));
		} else {
			gc.strokeRect(upperLeftX, upperLeftY, width, height);
		}
	}

	/**
	 * Draws unbuilt objects in the list.
	 *
	 * @param objects List of objects to draw.
	 */
	public void drawUnbuilt(List<? extends DrawableObject> objects) {
		for (DrawableObject obj : objects) {
			setGraphicsContextAttributes(Color.LIGHTGREEN, 1);
		}
	}

	private void setGraphicsContextAttributes(Color stroke, int lineWidth) {
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);
	}

	private void updateHorizonalDimension(int width1) {
		this.height = wallHeight;
		this.width = (width1 / wallWidth) * wallWidth;
		if (Math.abs(this.width) > inventory.getWood() * wallWidth) {
			this.width = this.width < 0 ? inventory.getWood() * wallWidth * (-1) :
					inventory.getWood() * wallWidth;
		}
	}

	private void updateVerticalDimension(int height1) {
		this.width = wallHeight;
		this.height = (height1 / wallWidth) * wallWidth;
		if (Math.abs(this.height) > inventory.getWood() * wallWidth) {
			this.height = this.height < 0 ? inventory.getWood() * wallWidth * (-1) :
					inventory.getWood() * wallWidth;
		}
	}
}
