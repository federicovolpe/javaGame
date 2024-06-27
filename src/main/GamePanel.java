package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {

    //crezione di una classe di tipo mouse inputs per poter riferire ad un 
    //unico oggetto
    private MouseInputs mouseInputs ;

    //variables that are useful
    private float xDelta = 100, yDelta = 100;//remembers the coordinates position of the object

    private float xDir = 4, yDir = 4; //useful to indicate the direction of motion
    private Color color = Color.BLACK;
    private Random rnd = new Random();
    private Dimension panelDimension = new Dimension(1280, 800);

    /**
     * adds all the keylisteners
     */
    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));

        //classe che consente di ricevere inputs dal mouse
        //come clicked pressed e released
        addMouseListener(mouseInputs);

        //classe che osserva i movimenti del mouse
        addMouseMotionListener(mouseInputs );
        setPanelSize();
    }

    /**
     * method to set the size of the panel, if we do it on the gamewindow the border interfere
     */
    private void setPanelSize() {
        setMinimumSize(panelDimension);
        setMaximumSize(panelDimension);
        setPreferredSize(panelDimension);
    }

    /**
     * metodo che ricevuto un input numerico cambia la posizione orizzontale
     *  del pannello prima di ripitturarlo
     * @param x delta di cui spostare orizzontalmente l'oggetto
     */
    public void changeXDelta(int x){
        this.xDelta += x;
    }

    /**
     * metodo che ricevuto un input numerico cambia la posizione verticale
     *  del pannello prima di ripitturarlo
     * @param y delta di cui spostare orizzontalmente l'oggetto
     */
    public void changeYDelta(int y){
        this.yDelta += y;
    }

    /**
     * method that sets the inital position of the rectangle thats about to be drawn
     * @param x horizondal position (pixels from the left margin)
     * @param y vertical position (pixels from the top of the window)
     */
    public void setRectPos(int x, int y){
        this.xDelta = x;
        this.yDelta = y;
    }

    /**
     * method that redraws a rectangle with a simple loop
     * @g 
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        updateRectangle();
        g.setColor(color);
        g.fillRect((int)xDelta, (int)yDelta,200,50);
    }

    private void updateRectangle(){
        xDelta += xDir;
        if(xDelta >= panelDimension.width - 200 || xDelta < 0){
            xDir *= -1;
            color = getRndColor();
        }
        yDelta += yDir;
        if(yDelta >= panelDimension.height - 50 || yDelta < 0){
            yDir *= -1;
            color = getRndColor();
        }
    }

    private Color getRndColor() {
        return new Color(rnd.nextInt(0, 255), rnd.nextInt(0, 255), rnd.nextInt(0, 255));
        // ieri non ho mangiato niente
    }
}
