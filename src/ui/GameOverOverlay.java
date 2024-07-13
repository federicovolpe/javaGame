package ui;

import gamestates.GameStates;
import gamestates.Playing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

import static utils.Constants.UI.URMButtons.URM_SIZE;

public class GameOverOverlay {

  private final Playing playing;
  private final BufferedImage img;
  private final int imgX, imgY, imgW, imgH;
  private UrmButtons menu, play;

  public GameOverOverlay(Playing playing) {
    this.playing = playing;
    createButtons();
    img = LoadSave.getSpriteAtlas(LoadSave.GO_BOARD);
    imgW = (int) (img.getWidth() * Game.SCALE);
    imgH = (int) (img.getHeight() * Game.SCALE);
    imgX = Game.GAME_WIDTH / 2 - imgW / 2;
    imgY = (int) (100 * Game.SCALE);
  }

  private void createButtons() {
    int menux = (int) (335 * Game.SCALE);
    int playx = (int) (440 * Game.SCALE);
    int y = (int) (195 * Game.SCALE);
    play = new UrmButtons(playx, y, URM_SIZE, URM_SIZE, 0);
    menu = new UrmButtons(menux, y, URM_SIZE, URM_SIZE, 2);
  }

  public void update() {
    play.update();
    menu.update();
  }

  public void draw(Graphics g) {
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

    g.drawImage(img, imgX, imgY, imgW, imgH, null);

    menu.draw(g);
    play.draw(g);
  }

  public void keyPressed(KeyEvent e) {

  }

  public void mouseMoved(MouseEvent e) {
    play.setMouseOver(false);
    menu.setMouseOver(false);
    if (isIn(menu, e))
      menu.setMouseOver(true);
    else if (isIn(play, e))
      play.setMouseOver(true);
  }

  public void mouseReleased(MouseEvent e) {
    if (isIn(menu, e)) {
      if (menu.isMousePressed()) {
        playing.resetAll();
        playing.setGameState(GameStates.MENU);
      }
    } else if (isIn(play, e))
      if (play.isMousePressed()) {
        playing.resetAll();
        playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
      }
    menu.resetBools();
    play.resetBools();
  }

  public void mousePressed(MouseEvent e) {
    if (isIn(menu, e))
      menu.setMousePressed(true);
    else if (isIn(play, e))
      play.setMousePressed(true);
  }

  private boolean isIn(UrmButtons b, MouseEvent e) {
    System.out.println("mouse is in buttonnn");
    return b.getBounds().contains(e.getX(), e.getY());
  }

}
