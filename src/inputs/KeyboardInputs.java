package inputs;

import main.Game;
import main.GamePanel;

import static utils.Constants.Directions.*;

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

    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        //in base alla lettera che ascolto chiamo il metodo corrispondente del gamepanel
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                System.out.println("premuto il tasto W");
                gamePanel.setDirection(UP);
                break;
            case KeyEvent.VK_A:
                System.out.println("premuto il tasto A");
                gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                System.out.println("premuto il tasto S");
                gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                System.out.println("premuto il tasto D");
                gamePanel.setDirection(RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gamePanel.setMoving(false);
    }
}
