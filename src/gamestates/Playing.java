package gamestates;

import Audio.AudioPlayer;
import entities.EnemyManager;
import entities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import levels.LevelManager;
import main.Game;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utils.Constants.Environment;
import utils.LoadSave;

public class Playing extends State implements StateMethods {

  private Player player;
  private LevelManager levelManager;
  private EnemyManager enemyManager;
  private PauseOverlay pauseOverlay;
  private GameOverOverlay gameOverOverlay;
  private ObjectManager objectManager;
  private LevelCompletedOverlay levelCompletedOverlay;
  private boolean paused = false; // showing the pause screen or

  private int xLvlOffset;
  private final int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
  private final int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
  private int maxLvlOffset;

  private final BufferedImage backgroundImg, bigCloud, smallCloud;
  private final int[] smallCloudPos;
  private final Random rnd = new Random();
  private boolean gameOver;
  private boolean lvlCompleted;
  private boolean playerDying = false;

  public Playing(Game game) {
    super(game);
    initClasses();
    backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG);
    bigCloud = LoadSave.getSpriteAtlas(LoadSave.BG_BIG_CLOUDS);
    smallCloudPos = new int[8];
    for (int i = 0; i < smallCloudPos.length; i++) {
      smallCloudPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
    }
    smallCloud = LoadSave.getSpriteAtlas(LoadSave.BG_SMALL_CLOUDS);

    calcLvlOffset();
    loadStartLevel();
  }

  public void loadNextLevel() {
    resetAll();
    levelManager.loadNextLevel();
    player.setSpawn(levelManager.getCurrLevel().getPlayerSpawn());
    objectManager.resetAllObject();
  }

  private void loadStartLevel() {
    enemyManager.loadEnemies(levelManager.getCurrLevel());
    objectManager.loadObjects(levelManager.getCurrLevel());
  }

  private void calcLvlOffset() {
    maxLvlOffset = levelManager.getCurrLevel().getLvlOffset();
  }

  private void initClasses() {
    levelManager = new LevelManager(game);
    enemyManager = new EnemyManager(this);
    objectManager = new ObjectManager(this);

    player = new Player(200, 200, (int) (40 * Game.SCALE), (int) (64 * Game.SCALE), this);
    player.setSpawn(levelManager.getCurrLevel().getPlayerSpawn());
    player.loadLvlData(levelManager.getCurrLevel().getLvlData());

    pauseOverlay = new PauseOverlay(this);
    gameOverOverlay = new GameOverOverlay(this);
    levelCompletedOverlay = new LevelCompletedOverlay(this);
  }

  public Player getPlayer() {
    return player;
  }

  public void unpauseGame() {
    paused = false;
  }

  @Override
  public void update() {
    if (paused)
      pauseOverlay.update();
    else if (lvlCompleted)
      levelCompletedOverlay.update();
    else if (gameOver)
      gameOverOverlay.update();
    else if (playerDying)
      player.update();
    else {
      objectManager.update(levelManager.getCurrLevel().getLvlData(), player);
      levelManager.update();
      player.update();
      enemyManager.update(levelManager.getCurrLevel().getLvlData(), player);
      checkCloseToBorder();
    }
  }

  // if the player is out of the offset do something
  private void checkCloseToBorder() {
    int playerX = (int) player.getHitbox().getX();
    int diff = playerX - xLvlOffset;
    if (diff > rightBorder)
      xLvlOffset += diff - rightBorder;
    else if (diff < leftBorder)
      xLvlOffset += diff - leftBorder;

    if (xLvlOffset > maxLvlOffset) // offset cannot be greater than the fixed value
      xLvlOffset = maxLvlOffset;
    else if (xLvlOffset < 0)
      xLvlOffset = 0;
  }

  @Override
  public void draw(Graphics g) {
    g.drawImage(backgroundImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    drawClouds(g);

    levelManager.draw(g, xLvlOffset);
    enemyManager.draw(g, xLvlOffset);
    objectManager.draw(g, xLvlOffset);

    player.render(g, xLvlOffset);
    if (paused) {
      g.setColor(new Color(0, 0, 0, 150));
      g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
      pauseOverlay.draw(g);
    } else if (gameOver)
      gameOverOverlay.draw(g);
    else if (lvlCompleted)
      levelCompletedOverlay.draw(g);
  }

  private void drawClouds(Graphics g) {
    for (int i = 0; i < 3; i++) {
      g.drawImage(bigCloud, i * Environment.BIG_CLOUD_DEF_WIDTH - (int) (xLvlOffset * .3),
          (int) (204 * Game.SCALE),
          Environment.BIG_CLOUD_DEF_WIDTH,
          Environment.BIG_CLOUD_HEIGHT, null);
    }
    for (int j = 0; j < smallCloudPos.length; j++) {
      g.drawImage(smallCloud, Environment.SMALL_CLOUD_WIDTH * 4 * j - (int) (xLvlOffset * .7),
          smallCloudPos[j],
          Environment.SMALL_CLOUD_WIDTH,
          Environment.SMALL_CLOUD_HEIGHT, null);
    }
  }

  public void checkEnemyHit(Rectangle2D.Float attackBox) {
    enemyManager.checkEnemyHit(attackBox);
  }

  public void checkPotionTouched(Rectangle2D.Float hitbox) {
    objectManager.checkObjectTouched(hitbox);
  }

  public void checkObjectAttacked(Rectangle2D.Float attackBox) {
    objectManager.checkObjectHit(attackBox);
  }

  public void resetAll() {
    gameOver = false;
    paused = false;
    lvlCompleted = false;
    player.resetAll();
    playerDying = false;
    enemyManager.resetAllEnemies();
    objectManager.resetAllObject();
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public void setLevelCompeted() {
    lvlCompleted = true;
    getGame().getAudioPlayer().playSong(AudioPlayer.LVL_COMPLETED);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (!gameOver)
      if (e.getButton() == MouseEvent.BUTTON1)
        player.setAttacking(true);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (!gameOver) {
      if (paused)
        pauseOverlay.mousePressed(e);
      else if (lvlCompleted)
        levelCompletedOverlay.mousePressed(e);

    } else
      gameOverOverlay.mousePressed(e);

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (!gameOver) {
      if (paused)
        pauseOverlay.mouseReleased(e);
      else if (lvlCompleted)
        levelCompletedOverlay.mouseReleased(e);

    }else
      gameOverOverlay.mouseReleased(e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (!gameOver) {
      if (paused)
        pauseOverlay.mouseMoved(e);
      else if (lvlCompleted)
        levelCompletedOverlay.mouseMoved(e);

    }else
      gameOverOverlay.mouseMoved(e);
  }

  public void mouseDragged(MouseEvent e) {
    if (!gameOver)
      if (paused)
        pauseOverlay.mouseDragged(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (gameOver)
      gameOverOverlay.keyPressed(e);
    else
      switch (e.getKeyCode()) {
        case KeyEvent.VK_A -> player.setLeft(true);
        case KeyEvent.VK_D -> player.setRight(true);
        case KeyEvent.VK_SPACE -> player.setJump(true);
        case KeyEvent.VK_BACK_SPACE -> GameStates.state = GameStates.MENU;
        case KeyEvent.VK_ESCAPE -> paused = true;
      }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (!gameOver)
      switch (e.getKeyCode()) {
        case KeyEvent.VK_A -> player.setLeft(false);
        case KeyEvent.VK_D -> player.setRight(false);
        case KeyEvent.VK_SPACE -> player.setJump(false);
      }
  }

  public EnemyManager getEnemyManager() {
    return enemyManager;
  }

  public void setMaxLvlOffset(int lvlOffset) {
    this.maxLvlOffset = lvlOffset;
  }

  public ObjectManager getObjectManager() {
    return objectManager;
  }

  public LevelManager getLevelManager() {
    return levelManager;
  }

  public void checkSpikesTouched(Player player) {
    objectManager.checkSpikesTouched(player);
  }

  public void setPlayerDying(boolean b) {
    this.playerDying = b;
  }
}
