package levels;

import entities.Crabby;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import utils.HelpMethods;
import static utils.HelpMethods.*;

public class Level {
    private int[][] lvlData;
    private BufferedImage img;
    private List<Crabby> crabs;
    private List<Potion> potions;
    private List<GameContainer> containers;
    private int lvlTilesWide ; // the number of tiles of the level in width
    private int maxTilesOffset;
    private int maxLvlOffset ;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData(img);
        createEnemies(img);
        createPotions(img);
        createContainers(img);
        calcLvlOffsets();
        calcPlayerSpawn(img);
    }

    private void createContainers(BufferedImage img) {
        containers = HelpMethods.getContainers(img);
    }

    private void createPotions(BufferedImage img) {
        potions = HelpMethods.getPotions(img);
    }

    private void calcPlayerSpawn(BufferedImage img) {
        playerSpawn = HelpMethods.getPlayerSpawn(img);
    }

    private void createLevelData(BufferedImage img) {
        lvlData = getLevelData(img);
    }

    private void createEnemies(BufferedImage img) {
        crabs = HelpMethods.getCrabs(img);
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
    public List<Crabby> getCrabs () {
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
}
