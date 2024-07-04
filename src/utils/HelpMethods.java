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
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE); // the tile the player is currently on

        if (xSpeed > 0) { // the player is going to right
            int tileXPos = currentTile * Game.TILES_SIZE; // coordinates in pixels
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);// the difference between the size of the tile and the
                                                                 // entity
            return tileXPos + xOffset - 1;
        } else // the player is going to the left
            return currentTile * Game.TILES_SIZE;
    }

    public static float getYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        // the tile the player is currently on
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE); 
        // the player is going to down (falling)
        if (airSpeed > 0) { 
            // coordinates in pixels
            int tileYPos = currentTile * Game.TILES_SIZE; 
            // the difference between the size of the tile and the
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            // entity
            System.out.println("hitting ground on " + hitbox.y + " tilesize : " + tileYPos + " yoffset" + yOffset);
            System.out.println("new y : " + (tileYPos + yOffset - 1));
            return tileYPos + yOffset - 1;
        } else 
            // the player is going up (jumping)
            return currentTile * Game.TILES_SIZE;
            
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // check the pixel on the bottom left and bottom right
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }
}
