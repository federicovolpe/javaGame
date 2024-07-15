package levels;

import entities.Crabby;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.Game;
import objects.*;
import utils.Constants;

import static utils.Constants.EnemyConstants.CRABBY;
import static utils.Constants.ObjectConstants.*;

public class Level {
  private final int[][] lvlData;
  private final int lvlIndex;
  private final BufferedImage img;
  private final List<Crabby> crabs = new ArrayList<>();
  private final List<Potion> potions = new ArrayList<>();
  private final List<GameContainer> containers = new ArrayList<>();
  private final List<Spike> spikes = new ArrayList<>();
  private final List<Cannon> cannons = new ArrayList<>();
  private final List<Tree> trees = new ArrayList<>();
  private int lvlTilesWide; // the number of tiles of the level in width
  private int maxTilesOffset;
  private int maxLvlOffset;
  private Point playerSpawn;

  public Level(BufferedImage img, int lvlIndex) {
    this.lvlIndex = lvlIndex;
    this.img = img;
    lvlData = new int[img.getHeight()][img.getWidth()];
    loadLevel();
    calcLvlOffsets();
  }

  private void calcLvlOffsets() {
    lvlTilesWide = img.getWidth();
    maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    maxLvlOffset = Game.TILES_SIZE * maxTilesOffset;
  }

  private void loadLevel() {
    for (int y = 0; y < img.getHeight(); y++)
      for (int x = 0; x < img.getWidth(); x++) {
        Color c = new Color(img.getRGB(x, y));

        loadLevelData(c.getRed(), x, y);
        loadEntities(c.getGreen(), x, y);
        loadObjects(c, x, y);
      }
    System.out.println("found "+trees.size()+" trees");
  }

  private void loadLevelData(int redValue, int x, int y) {
    if (redValue >= 50)
      lvlData[y][x] = 0;
    else
      lvlData[y][x] = redValue;
    /*switch (redValue) {
      case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 ->
          grass.add(new Grass((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
    }*/
  }
  private void loadEntities(int greenValue, int x, int y) {
    switch (greenValue) {
      case CRABBY -> crabs.add(new Crabby(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
      /*case PINKSTAR -> pinkstars.add(new Pinkstar(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
      case SHARK -> sharks.add(new Shark(x * Game.TILES_SIZE, y * Game.TILES_SIZE));*/
      case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
    }
  }

  private void loadObjects(Color color, int x, int y) {
    int blueValue = color.getBlue();
    switch (blueValue) {
      case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
      case BOX, BARREL -> containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
      case SPIKE -> spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
      case CANNON_LEFT, CANNON_RIGHT -> cannons.add(new Cannon(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
      case TREE_0NE, TREE_TWO/*, TREE_THREE */-> {
        System.out.println("palm rgb: R "+color.getRed()+" G "+color.getGreen()+" B "+color.getBlue());trees.add(new Tree(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
      }
    }
  }
  public int getSpriteIndex(int i, int j) {
    return lvlData[j][i];
  }

  public int[][] getLvlData() {
    return lvlData;
  }

  public int getLvlOffset() {
    return maxLvlOffset;
  }

  public List<Crabby> getCrabs() {
    return crabs;
  }

  public Point getPlayerSpawn() {
    return playerSpawn;
  }

  public List<Potion> getPotions() {
    return potions;
  }

  public List<GameContainer> getContainers() {
    return containers;
  }

  public List<Spike> getSpikes() {
    return spikes;
  }

  public List<Cannon> getCannons() {
    return cannons;
  }

  public List<Tree> getTrees() {
    return trees;
  }
}
