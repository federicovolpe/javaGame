package main;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;//variable that contains the thread of the game
    private final int FRAMES = 30; //how many frames arecreated in a second

    /**
     * creates a gamewindow to which assign a gamepanel and starts the gameloop
     */
    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        //funzione che richiede l'attenzione del programma sul pannello creato
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        //how many seconds a frame should last
        double timePerFrame = 1000000000.0 / FRAMES;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        System.out.println("inside the run method");

        while(true){
            now = System.nanoTime();
            //when to change frame?
            if(now - lastFrame >= timePerFrame){
                //if now - the last time i updated the frame is greater than the time per frame
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }


            if(System.currentTimeMillis() - lastCheck >= 1000){
                //checking if the current time - last time it went into this branch is greater than 1 sec
                lastCheck = System.currentTimeMillis();
                System.out.println("Fps: "+ frames);
                frames = 0;
            }
        }
    }
}
