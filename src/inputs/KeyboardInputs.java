package inputs;

import main.Game;
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
                gamePanel.changeYDelta(-5);
                break;
            case KeyEvent.VK_A:
                System.out.println("premuto il tasto A");
                gamePanel.changeXDelta(-5);
                break;
            case KeyEvent.VK_S:
                System.out.println("premuto il tasto S");
                gamePanel.changeYDelta(5);
                break;
            case KeyEvent.VK_D:
                System.out.println("premuto il tasto D");
                gamePanel.changeXDelta(5);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
