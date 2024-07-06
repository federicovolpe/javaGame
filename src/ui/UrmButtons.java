package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utils.Constants.UI.URMButtons.*;
import utils.LoadSave;

public class UrmButtons extends PauseButton {

    private BufferedImage[] img;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public UrmButtons(int x, int y, int w, int h, int rowIndex) {
        super(x, y, w, h);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.URM_BUTTONS);
        img = new BufferedImage[3];
        for (int i = 0; i < img.length; i++)
            img[i] = tmp.getSubimage(i * URM_DEFAULT, rowIndex * URM_DEFAULT, URM_DEFAULT, URM_DEFAULT);

    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(img[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
