package levels;

import gamestates.GameStates;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import main.Game;
import static main.Game.TILES_SIZE;
import utils.LoadSave;

public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private final List<Level> levels;
    private int lvlIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels() {
        BufferedImage[] allLelvels = LoadSave.getAllLevels();
        int i = 1;
        for (BufferedImage img : allLelvels){
            levels.add(new Level(img, i));
            i++;}
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        // standard dimension of a level in tiles
        levelSprite = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g, int lvlOffset) {
        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < levels.get(lvlIndex).getLvlData()[0].length; j++) {
                int index = levels.get(lvlIndex).getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], TILES_SIZE * j - lvlOffset, TILES_SIZE * i, TILES_SIZE, TILES_SIZE,
                        null);
            }
        }
    }

    public void update() {

    }

    public void loadNextLevel() {
        lvlIndex ++;
        if(lvlIndex >= levels.size()){
            lvlIndex = 0;
            System.out.println("no more levels");
            GameStates.state = GameStates.MENU;
        }
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    public Level getCurrLevel() {
        return levels.get(lvlIndex);
    }

    public int getLvlIndex() {return lvlIndex;}
}
