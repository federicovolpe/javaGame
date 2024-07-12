package gamestates;

import main.Game;
import ui.AudioOptions;
import ui.UrmButtons;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static utils.Constants.UI.URMButtons.URM_SIZE;

public class GameOptions extends State implements StateMethods {
  private final AudioOptions audioOptions;
  private BufferedImage optBgImg, bgImg;
  private int bgX, bgY, bgW, bgH;
  private UrmButtons menuB;

  public GameOptions(Game game) {
    super(game);
    loadImgs();
    loadButtons();
    audioOptions = game.getAudioOptions();
  }

  private void loadButtons() {
    int menuX = (int) (387 * Game.SCALE);
    int menuY = (int) (325 * Game.SCALE);
    menuB = new UrmButtons(menuX, menuY, URM_SIZE, URM_SIZE, 2);
  }

  private void loadImgs() {
    bgImg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
    optBgImg = LoadSave.getSpriteAtlas(LoadSave.OPTIONS_MENU);

    bgW = (int) (optBgImg.getWidth() * Game.SCALE);
    bgH = (int) (optBgImg.getHeight() * Game.SCALE);
    bgX = Game.GAME_WIDTH / 2 - bgW / 2;
    bgY = (int) (Game.SCALE * 33);
  }

  @Override
  public void update() {
    menuB.update();
    audioOptions.update();
  }

  @Override
  public void draw(Graphics g) {
    g.drawImage(bgImg, 0, 0, GAME_WIDTH, Game.GAME_HEIGHT, null);
    g.drawImage(optBgImg, bgX, bgY, bgW, bgH, null);
    menuB.draw(g);
    audioOptions.draw(g);
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  public void mouseDragged(MouseEvent e) {
    audioOptions.mouseDragged(e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (isIn(e, menuB))
      menuB.setMousePressed(true);
    else
      audioOptions.mousePressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (isIn(e, menuB)) {
      if (menuB.isMousePressed())
        GameStates.state = GameStates.MENU;
    } else
      audioOptions.mouseReleased(e);
    menuB.resetBools();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    menuB.setMouseOver(false);
    if (isIn(e, menuB))
      menuB.setMouseOver(true);
    else
      audioOptions.mouseMoved(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
      GameStates.state = GameStates.MENU;
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  public boolean isIn(MouseEvent e, UrmButtons mb) {
    return mb.getBounds().contains(e.getX(), e.getY());
  }
}
