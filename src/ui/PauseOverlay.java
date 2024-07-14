package ui;

import gamestates.GameStates;
import gamestates.Playing;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;

import static utils.Constants.UI.URMButtons.URM_SIZE;

import utils.LoadSave;

public class PauseOverlay {

  private BufferedImage background;
  private int bX, bY, bH, bW;
  private final AudioOptions audioOptions;
  private UrmButtons menuB, replayB, unpauseB;
  private final Playing playing;


  public PauseOverlay(Playing playing) {
    this.playing = playing;
    audioOptions = playing.getGame().getAudioOptions();
    loadBackground();
    createUrmButtons();
  }

  private void loadBackground() {
    background = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BOARD);
    bW = (int) (background.getWidth() * Game.SCALE);
    bH = (int) (background.getHeight() * Game.SCALE);
    bX = Game.GAME_WIDTH / 2 - bW / 2;
    bY = (int) (25 * Game.SCALE);
  }


  private void createUrmButtons() {
    int menuX = (int) (315 * Game.SCALE);
    int replayX = (int) (387 * Game.SCALE);
    int unpauseX = (int) (462 * Game.SCALE);

    int bY = (int) (315 * Game.SCALE);

    menuB = new UrmButtons(menuX, bY, URM_SIZE, URM_SIZE, 2);
    replayB = new UrmButtons(replayX, bY, URM_SIZE, URM_SIZE, 1);
    unpauseB = new UrmButtons(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
  }


  public void update() {
    menuB.update();
    replayB.update();
    unpauseB.update();
    audioOptions.update();
  }

  public void draw(Graphics g) {
    g.drawImage(background, bX, bY, bW, bH, null);
    menuB.draw(g);
    replayB.draw(g);
    unpauseB.draw(g);
    audioOptions.draw(g);
  }

  public void mouseDragged(MouseEvent e) {
    audioOptions.mouseDragged(e);
  }

  public void mousePressed(MouseEvent e) {
    if (isIn(e, menuB))
      menuB.setMousePressed(true);
    else if (isIn(e, replayB))
      replayB.setMousePressed(true);
    else if (isIn(e, unpauseB))
      unpauseB.setMousePressed(true);
    else audioOptions.mousePressed(e);

  }

  public void mouseReleased(MouseEvent e) {
    if (isIn(e, menuB)) {
      if (menuB.isMousePressed())
      playing.resetAll();
        playing.setGameState(GameStates.MENU);
      // when the game is starting it shoudnt be paused
      playing.unpauseGame();
    } else if (isIn(e, replayB)) {
      if (replayB.isMousePressed()) {
        playing.resetAll();
        playing.unpauseGame();
      }
    } else if (isIn(e, unpauseB)) {
      if (unpauseB.isMousePressed())
        playing.unpauseGame();
    } else audioOptions.mouseReleased(e);


    menuB.resetBools();
    replayB.resetBools();
    unpauseB.resetBools();
  }

  public void mouseMoved(MouseEvent e) {
    menuB.setMouseOver(false);
    replayB.setMouseOver(false);
    unpauseB.setMouseOver(false);

    if (isIn(e, menuB))
      menuB.setMouseOver(true);
    else if (isIn(e, replayB))
      replayB.setMouseOver(true);
    else if (isIn(e, unpauseB))
      unpauseB.setMouseOver(true);
    else audioOptions.mouseMoved(e);
  }

  private boolean isIn(MouseEvent e, PauseButton b) {
    return b.getBounds().contains(e.getX(), e.getY());
  }

}