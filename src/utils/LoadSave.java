package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.Game;
import java.io.IOException;

/**
 * class with the only purpuse of getting the correct sprite of everything
 */
public class LoadSave {
    public static final String PLAYER_ATLAS = "resources/sprites.png";
    public static final String LEVEL_ATLAS = "resources/outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "resources/level_one_data.png";

    /**
     * gets the player sprites
     * 
     * @return
     */
    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] getLevelData() {
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WITH];
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
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
}
