
package abandonallhope.ui;

import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.weapons.Bullet;
import abandonallhope.logic.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Will draw bullets on game field 
 * @author kipsu
 */
public class BulletDrawer {
	
	private Game game;
	private GraphicsContext gc;

	/**
	 * Creates a new bullet drawer
	 * @param game game containing the bullets
	 * @param gc graphics context where the bullets will be displayed
	 */
	public BulletDrawer(Game game, GraphicsContext gc) {
		this.game = game;
		this.gc = gc;
	}
	
	/**
	 * Will draw each bullet in game
	 */
	public void drawBullets() {
		setGraphicsContextAttributes(Color.BLACK, Color.RED, 2);
		for (Bullet bullet : game.getBullets()) {
			drawABullet(bullet);
		}
	}

	private void setGraphicsContextAttributes(Color fill, Color stroke, int lineWidth) {
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.setLineWidth(lineWidth);
	}

	private void drawABullet(MovingObject bullet) {
		Point location = bullet.getLocation();
		gc.fillRect(location.x - 1, location.y - 1, 1.0, 1.0);
	}
}
