package abandonallhope.domain.constructions;

import javafx.scene.paint.Color;

/**
 * Contains the details of each wall type
 * @author kipsu
 */
public enum WallType {

	WOODEN(10, 2, 500, Color.BROWN);

	private final int width;
	private final int height;
	private final int maxHP;
	private final Color color;

	private WallType(int width, int height, int maxHP, Color color) {
		this.width = width;
		this.height = height;
		this.maxHP = maxHP;
		this.color = color;
	}

	public int getWidth(Wall.Orientation orientation) {
		if (orientation == Wall.Orientation.VERTICAL) {
			return height;
		}
		return width;
	}

	public int getHeight(Wall.Orientation orientation) {
		if (orientation == Wall.Orientation.VERTICAL) {
			return width;
		}
		return height;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public Color getColor() {
		return color;
	}
}
