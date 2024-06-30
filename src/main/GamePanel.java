package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.*;
import javax.swing.*;
import static main.Game.*;

public class GamePanel extends JPanel {

    // crezione di una classe di tipo mouse inputs per poter riferire ad un
    // unico oggetto
    private final MouseInputs mouseInputs = new MouseInputs(this);
    private Dimension panelDimension = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private final Game game;

    /**
     * adds all the keylisteners
     */
    public GamePanel(Game game) {
        addKeyListener(new KeyboardInputs(this));
        this.game = game;
        // classe che consente di ricevere inputs dal mouse
        // come clicked p[ressed e released
        addMouseListener(mouseInputs);

        // classe che osserva i movimenti del mouse
        addMouseMotionListener(mouseInputs);
        setPanelSize();
    }

    /**
     * method to set the size of the panel, if we do it on the gamewindow the border
     * interfere
     */
    private void setPanelSize() {
        setMinimumSize(panelDimension);
        setMaximumSize(panelDimension);
        setPreferredSize(panelDimension);
        System.out.println("size : " + GAME_WIDTH + " " + GAME_HEIGHT);
    }

    /**
     * method that redraws a rectangle with a simple loop
     * 
     * @g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public void updateGame() {
    }

    public Game getGame() {
        return game;
    }
}
