package levels;

import entities.Crabby;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import main.Game;
import objects.Cannon;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utils.HelpMethods;

import static utils.Constants.ObjectConstants.*;
import static utils.HelpMethods.*;

public class Level {
  private int[][] lvlData;
  private final BufferedImage img;
  private final List<Crabby> crabs;
  private final List<Potion> potions;
  private final List<GameContainer> containers;
  private final List<Spike> spikes;
  private final List<Cannon> cannons;
  private int lvlTilesWide; // the number of tiles of the level in width
  private int maxTilesOffset;
  private int maxLvlOffset;
  private Point playerSpawn;

  public Level(BufferedImage img) {
    this.img = img;
    createLevelData(img);
    crabs = HelpMethods.getCrabs(img);
    potions = HelpMethods.getGameObjects(img, List.of(RED_POTION, BLUE_POTION), Potion::new);
    containers = HelpMethods.getGameObjects(img, List.of(BARREL, BOX), GameContainer::new);
    cannons = HelpMethods.getGameObjects(img, List.of(CANNON_RIGHT, CANNON_LEFT), Cannon::new);
    spikes = HelpMethods.getGameObjects(img, List.of(SPIKE), Spike::new);
    calcLvlOffsets();
    calcPlayerSpawn(img);
  }

  private void calcPlayerSpawn(BufferedImage img) {
    playerSpawn = HelpMethods.getPlayerSpawn(img);
  }

  private void createLevelData(BufferedImage img) {
    lvlData = getLevelData(img);
  }

  private void calcLvlOffsets() {
    lvlTilesWide = img.getWidth();
    maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    maxLvlOffset = Game.TILES_SIZE * maxTilesOffset;
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
}
