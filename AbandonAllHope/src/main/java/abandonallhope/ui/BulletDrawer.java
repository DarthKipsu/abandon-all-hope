
package abandonallhope.ui;

import abandonallhope.domain.MovingObject;
import abandonallhope.domain.Point;
import abandonallhope.domain.weapons.Bullet;
import abandonallhope.logic.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BulletDrawer {
	
	private Game game;
	private GraphicsContext gc;

	public BulletDrawer(Game game, GraphicsContext gc) {
		this.game = game;
		this.gc = gc;
	}
		
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
