package inputs;

import gamestates.GameStates;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    /**
     * constructor method for a reciever of commands from the mouse
     * 
     * @param gamePanel game panel from which this class connects to
     */
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.state) {
            case PLAYING:
               gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
               gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
               gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.state) {
            case PLAYING:
               gamePanel.getGame().getPlaying().mouseDragged(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
               gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            default:
                break;
        }
    }
}
