package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {

    //crezione di una classe di tipo mouse inputs per poter riferire ad un 
    //unico oggetto
    private final MouseInputs mouseInputs = new MouseInputs(this);

    //variables that are useful
    private float xDelta = 100, yDelta = 100;//remembers the coordinates position of the object
    private Dimension panelDimension = new Dimension(1280, 800);
    private BufferedImage img;

    /**
     * adds all the keylisteners
     */
    public GamePanel(){
        importImg();
        addKeyListener(new KeyboardInputs(this));

        //classe che consente di ricevere inputs dal mouse
        //come clicked pressed e released
        addMouseListener(mouseInputs);

        //classe che osserva i movimenti del mouse
        addMouseMotionListener(mouseInputs );
        setPanelSize();
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/resources/sprites.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * method that redraws a rectangle with a simple loop
     * @g 
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img.getSubimage(0,0,64,40), 0 ,0 , 128, 80, null);
    }
}
