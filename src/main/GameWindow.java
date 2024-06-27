package main;

import javax.swing.*;

public class GameWindow {
    private JFrame jframe;

    /**
     * sets the dimensions of the window
     * @param gamePanel the initial panel assigned to this window
     */
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack(); // to apply the dimension of the panel
        jframe.setResizable(false);
        jframe.setVisible(true);

    }
}
