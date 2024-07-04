package ui;


import java.awt.image.BufferedImage;
import static utils.Constants.UI.Buttons.*;
import gamestates.GameStates;
import utils.LoadSave;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private GameStates state;
    private BufferedImage[] imgs;
    private int xOffsetCenter = B_WIDTH / 2;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowIndex, GameStates state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImages() {
        imgs = new BufferedImage[3];
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = tmp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        } 
    }

    public void draw(Graphics g){
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if(mouseOver)
            index = 1 ;
        if(mousePressed)
            index = 2;
    }

    public boolean getMouseOver () {
        return mouseOver;
    }
    public boolean getMousePressed () {
        return mousePressed;
    }
    public void setMouseOver (boolean value) {
        mouseOver = value;
    }
    public void setMousePressed (boolean value) {
        mousePressed = value;
    }
    public Rectangle getBounds () {
        return bounds;
    }

    public void applyGameState(){
        GameStates.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}
