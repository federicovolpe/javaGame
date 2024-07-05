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
    public static final String PLAYER_ATLAS = "sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BOARD = "menu_background_first.png";
    public static final String PAUSE_BOARD = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";

    /**
     * gets the player sprites
     * 
     * @return
     */
    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;
        String path = "/resources/" + fileName;
        InputStream is = LoadSave.class.getResourceAsStream(path);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (NullPointerException e) {
                System.out.println("\n\n\n\ncould not find : " + path + "\n\n\n\n");
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
