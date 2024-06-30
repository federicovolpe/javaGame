package inputs;

import main.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    /**
     * metodo costruttore per un un listener per tastiera
     * 
     * @param gamePanel pannello a cui faranno riferimento gli inputs del keyboard
     * @throws NullPointerException se l'argomento passato è {@code null}
     */

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        // in base alla lettera che ascolto chiamo il metodo corrispondente del
        // gamepanel
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                // System.out.println("premuto il tasto W");
                gamePanel.getGame().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_A:
                // System.out.println("premuto il tasto A");
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:
                // System.out.println("premuto il tasto S");
                gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                // System.out.println("premuto il tasto D");
                gamePanel.getGame().getPlayer().setRight(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
        }

    }
}
