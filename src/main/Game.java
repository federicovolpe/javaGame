package main;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;// variable that contains the thread of the game
    private final int FPS = 30; // how many frames arecreated in a second
    private final int UPS = 30;

    /**
     * creates a gamewindow to which assign a gamepanel and starts the gameloop
     */
    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        // funzione che richiede l'attenzione del programma sul pannello creato
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        gamePanel.updateGame();
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
}
