package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * class with the only purpuse of getting the correct sprite of everything
 */
public class LoadSave {
  public static final String PLAYER_ATLAS = "player_sprites.png";
  public static final String LEVEL_ATLAS = "outside_sprites.png";
  public static final String MENU_BUTTONS = "button_atlas.png";
  public static final String MENU_BOARD = "menu_background_first.png";
  public static final String PAUSE_BOARD = "pause_menu.png";
  public static final String SOUND_BUTTONS = "sound_button.png";
  public static final String URM_BUTTONS = "urm_buttons.png";
  public static final String VOLUME_BUTTONS = "volume_buttons.png";
  public static final String MENU_BACKGROUND = "background_menu.png";
  public static final String PLAYING_BG_IMG = "playing_bg_img.png";
  public static final String BG_BIG_CLOUDS = "big_clouds.png";
  public static final String BG_SMALL_CLOUDS = "small_clouds.png";
  public static final String CRABBY_SPRITE = "crabby_sprite.png";
  public static final String STATUS_BAR = "health_power_bar.png";
  public static final String COMPLETED_IMG = "completed_sprite.png";
  public static final String POTION_ATLAS = "potions_sprites.png";
  public static final String CONTAINER_ATLAS = "objects_sprites.png";
  public static final String TRAP_ATLAS = "trap_atlas.png";
  public static final String CANNON_ATLAS = "cannon_atlas.png";
  public static final String CANNON_BALL = "ball.png";
  public static final String GO_BOARD = "death_screen.png";
  public static final String OPTIONS_MENU = "options_background.png";

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
    } finally {
      try {
        is.close();
      } catch (NullPointerException e) {
        System.out.println("\n\n\n\ncould not find : " + path + "\n\n\n\n");
      } catch (IOException e) {
      }
    }
    return img;
  }

  public static BufferedImage[] getAllLevels() {
    URL url = LoadSave.class.getResource("/resources/lvls" +
        "");
    File file = null;

    try {
      file = new File(url.toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    File[] files = file.listFiles();
    File[] filesSorted = new File[files.length];

    for (int i = 0; i < filesSorted.length; i++)
      for (File value : files) {
        if (value.getName().equals((i + 1) + ".png"))
          filesSorted[i] = value;

      }

    BufferedImage[] imgs = new BufferedImage[filesSorted.length];

    for (int i = 0; i < imgs.length; i++)
      try {
        imgs[i] = ImageIO.read(filesSorted[i]);
      } catch (IOException e) {
      }

    return imgs;
  }
}
