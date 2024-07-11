package utils;

import entities.Crabby;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.Game;
import objects.GameContainer;
import objects.Potion;

import static utils.Constants.EnemyConstants.CRABBY;
import static utils.Constants.ObjectConstants.*;

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
    // keeping in mind that Game.GAME_WIDTH is the visible size
    int maxWidth = lvlData[0].length * Game.TILES_SIZE;
    if (x < 0 || x >= maxWidth)
      return true;
    if (y < 0 || y >= Game.GAME_HEIGHT)
      return true;

    float xIndex = x / Game.TILES_SIZE;
    float yIndex = y / Game.TILES_SIZE;

    return isTileSolid((int) xIndex, (int) yIndex, lvlData);
  }

  public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
    int value = lvlData[yTile][xTile];

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

  public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
    if (xSpeed > 0)
      return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    else
      return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
  }

  public static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
    for (int i = 0; i < xEnd - xStart; i++) {
      if (isTileSolid(xStart + i, y, lvlData))
        return false;
      if (!isTileSolid(xStart + i, y + 1, lvlData))
        return false;
    }
    return true;
  }

  /**
   * checks if in between two entities there are solid blocks
   *
   * @param lvlData
   * @param firstHitb
   * @param secondHitb
   * @param tileY
   * @return
   */
  public static boolean isSightClear(int[][] lvlData,
                                     Rectangle2D.Float firstHitb,
                                     Rectangle2D.Float secondHitb,
                                     int tileY) {
    int firstXTile = (int) (firstHitb.x / Game.TILES_SIZE);
    int secondXTile = (int) (secondHitb.x / Game.TILES_SIZE);

    // check if in between the two entities theres a solid block
    if (firstXTile > secondXTile)
      return isAllTilesWalkable(secondXTile, firstXTile, tileY, lvlData);
    else
      return isAllTilesWalkable(firstXTile, secondXTile, tileY, lvlData);

  }

  public static List<Crabby> getCrabs(BufferedImage img) {
    List<Crabby> crabbyList = new ArrayList<>();

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Color color = new Color(img.getRGB(j, i));
        int value = color.getGreen();
        if (value == CRABBY)
          crabbyList.add(new Crabby(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
      }
    }
    return crabbyList;
  }

  public static int[][] getLevelData(BufferedImage img) {
    int[][] lvlData = new int[img.getHeight()][img.getWidth()];

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Color color = new Color(img.getRGB(j, i));
        int value = color.getRed();
        if (value >= 48) {
          value = 0;
        }
        lvlData[i][j] = color.getRed();
      }
    }
    return lvlData;
  }

  public static Point getPlayerSpawn(BufferedImage img) {
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Color color = new Color(img.getRGB(j, i));
        int value = color.getGreen();
        if (value == 100)
          return new Point(j * Game.TILES_SIZE, i * Game.TILES_SIZE);
      }
    }
    return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
  }

  public static List<Potion> getPotions(BufferedImage img) {
    List<Potion> potionList = new ArrayList<>();

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Color color = new Color(img.getRGB(j, i));
        int value = color.getBlue();
        if (value == RED_POTION || value == BLUE_POTION)
          potionList.add(new Potion(j * Game.TILES_SIZE, i * Game.TILES_SIZE, value));
      }
    }
    System.out.println("found " + potionList.size() + " potions");
    return potionList;
  }

  public static List<GameContainer> getContainers(BufferedImage img) {
    List<GameContainer> containerList = new ArrayList<>();

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Color color = new Color(img.getRGB(j, i));
        int value = color.getBlue();
        if (value == BARREL || value == BOX) {
          System.out.println("trovato un container di tipo: " + value);
          containerList.add(new GameContainer(j * Game.TILES_SIZE, i * Game.TILES_SIZE, value));
        }
      }
    }
    System.out.println("found " + containerList.size() + " containers");
    return containerList;
  }

}
