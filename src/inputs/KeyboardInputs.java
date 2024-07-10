package inputs;

import gamestates.GameStates;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

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
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
               gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { 
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
               gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
