package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utils.Constants.UI.PauseButtons.*;
import utils.LoadSave;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImg;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;

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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public SoundButton(int x, int y, int w, int h) {
        super(x, y, w, h);
        loadSoundImgs();
    }

    public void loadSoundImgs() {
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImg = new BufferedImage[2][3];
        for (int i = 0; i < soundImg.length; i++)
            for (int j = 0; j < soundImg[i].length; j++)
                soundImg[i][j] = tmp.getSubimage(j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT,
                        SOUND_SIZE_DEFAULT);
    }

    public void update() {
        colIndex = 0;
        if (muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        if (mouseOver)
            colIndex = 1;
        if (mousePressed)
            colIndex = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(soundImg[rowIndex][colIndex], x, y, w, h, null);
    }

}
