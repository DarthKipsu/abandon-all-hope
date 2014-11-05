
package abandonallhope;

import abandonallhope.logic.Game;

public class AbandonAllHope {
	
	public static void main(String[] args) {
		Game game = new Game(500);
		game.addZombies(1);
		game.run();
	}
	
}
