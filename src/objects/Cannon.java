package objects;

import main.Game;
import java.awt.geom.Rectangle2D;
import static utils.HelpMethods.isAllTilesClear;

public class Cannon extends GameObject {

	private final int tileY;

	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY = y / Game.TILES_SIZE;
		initHitbox(40, 26);
		hitbox.x -= (int) (4 * Game.SCALE);
		hitbox.y += (int) (6 * Game.SCALE);
	}

	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	public int getTileY() {
		return tileY;
	}

	public static boolean canCannonSeePlayer(int[][] lvlData,
											 Rectangle2D.Float firstHitb,
											 Rectangle2D.Float secondHitb,
											 int tileY) {
		int firstXTile = (int) (firstHitb.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitb.x / Game.TILES_SIZE);

		// check if in between the two entities theres a solid block
		if (firstXTile > secondXTile)
			return isAllTilesClear(secondXTile, firstXTile, tileY, lvlData);
		else
			return isAllTilesClear(firstXTile, secondXTile, tileY, lvlData);

	}
}
