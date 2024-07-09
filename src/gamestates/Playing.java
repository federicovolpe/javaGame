package gamestates;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import entities.Crabby;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utils.LoadSave;
import java.awt.image.BufferedImage;
import java.util.Random;
import utils.Constants.Environment;

public class Playing extends State implements StateMethods {

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private boolean paused = false; // showing the pause screen or

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.getLevelData()[0].length; // the number of tiles of the level in width
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WITH;
    private int maxLvlOffset = maxTilesOffset * Game.TILES_SIZE;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudPos;
    private Random rnd = new Random();
    private boolean gameOver;

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
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);

        player = new Player(200, 200, (int) (40 * Game.SCALE), (int) (64 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrLevel().getLvlData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetBooleans();
    }

    public void unpauseGame() {
        paused = false;
    }

    @Override
    public void update() {
        if (!paused && !gameOver) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrLevel().getLvlData(), player);
            checkCloseToBorder();
        } else
            pauseOverlay.update();
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

        player.render(g, xLvlOffset);
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, 0 + i * Environment.BIG_CLOUD_DEF_WIDTH - (int) (xLvlOffset * .3),
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

    public void resetAll() {
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseMoved(e);
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
                case KeyEvent.VK_W:
                    player.setUp(true);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    GameStates.state = GameStates.MENU;
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = true;
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }
}
