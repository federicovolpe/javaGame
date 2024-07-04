package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable {
    // rivate GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;// variable that contains the thread of the game
    private final int FPS = 30; // how many frames arecreated in a second
    private final int UPS = 60;
    private Player player;
    private LevelManager levelManager;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1f;
    public final static int TILES_IN_WITH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WITH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    /**
     * creates a gamewindow to which assign a gamepanel and starts the gameloop
     */
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        /* gameWindow = */new GameWindow(gamePanel);
        // funzione che richiede l'attenzione del programma sul pannello creato
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        player = new Player(200, 200, (int) (40 * SCALE), (int) (64 * SCALE));
        player.loadLvlData(levelManager.getCurrLevel().getLvlData());
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        levelManager.update();
        player.update();
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void run() {
        // how many seconds a frame should last
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;

        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
            // when to change frame?
            if (deltaF >= 1) {
                // if now - the last time i updated the frame is greater than the time per frame
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                // checking if the current time - last time it went into this branch is greater
                // than 1 sec
                lastCheck = System.currentTimeMillis();
                System.out.println("Fps: " + frames + " | UPS : " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetBooleans();
    }
}
