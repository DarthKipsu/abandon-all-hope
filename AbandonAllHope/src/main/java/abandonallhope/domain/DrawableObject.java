
package abandonallhope.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

/**
 * Interface for objects that will be rendered to the game canvas.
 * @author kipsu
 */
public interface DrawableObject {
	
	Rectangle2D occupiedArea();
	Color getColor();
	
}
