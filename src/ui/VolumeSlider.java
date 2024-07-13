package ui;

import static utils.Constants.UI.VolumeButtons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.LoadSave;

public class VolumeSlider extends PauseButton {
private float floatValue = 0f;
    private BufferedImage[] img;
    private BufferedImage slider;
    private int index, buttonX;
        private final int minX, maxX;
    private boolean mouseOver, mousePressed;

    public VolumeSlider(int x, int y, int w, int h) {
        super(x + w / 2, y, VOLUME_WIDTH, h);
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + w / 2;
        this.x = x;
        this.w = w;
        minX = x + VOLUME_WIDTH / 2;
        maxX = x + w - VOLUME_WIDTH / 2;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        img = new BufferedImage[3];
        for (int i = 0; i < img.length; i++) {
            img[i] = tmp.getSubimage(i * DEFAULT_WIDTH, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
        slider = tmp.getSubimage(3 * DEFAULT_WIDTH, 0, DEFAULT_SLIDER_WIDTH, DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(slider, x, y, w, h - 10, null);
        g.drawImage(img[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, h - 10, null);
    }

    public void changeX(int x) { // the x of the mouse
        if (x < minX)
            buttonX = minX;
        else buttonX = Math.min(x, maxX);
updateFloatValue();
        bounds.x = buttonX - VOLUME_WIDTH / 2; // the bounds of the moved cursor should be updated
    }

    private void updateFloatValue() {
        int range = maxX - minX;
        float value = buttonX - minX;
        floatValue = value / range;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
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

    public float getFloatValue() {
        return floatValue;
    }

}
