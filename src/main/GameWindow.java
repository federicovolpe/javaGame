package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.*;

public class GameWindow {
    private JFrame jframe;

    /**
     * sets the dimensions of the window
     * 
     * @param gamePanel the initial panel assigned to this window
     */
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();
        jframe.add(gamePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.pack(); // to apply the dimension of the panel
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(true);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

        });
    }
}
