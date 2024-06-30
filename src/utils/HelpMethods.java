package utils;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!isSolid(x, y, lvlData))
            if (!isSolid(x + width, y + height, lvlData))
                if (!isSolid(x + width, y, lvlData))
                    if (!isSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        // 48 total number of tiles
        // 0 first type of tiles
        // 11 empty tile
        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE); // the tile the player is currently on

        if(xSpeed > 0){ // the player is going to right
            int tileXPos = currentTile * Game.TILES_SIZE; // coordinates in pixels
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width) ;// the difference between the size of the tile and the entity
            return tileXPos + xOffset -1;

        }else { // the player is going to the left
            return currentTile * Game.TILES_SIZE;
        }
    }
}
