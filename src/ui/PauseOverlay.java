package ui;

import static utils.Constants.UI.PauseButtons.SOUND_SIZE;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import gamestates.GameStates;
import main.Game;
import utils.LoadSave;

public class PauseOverlay {

    private BufferedImage background;
    private int bX, bY, bH, bW;
    private SoundButton musicButton, sfxButton;

    public void createSoundbuttons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (150 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);

        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    public PauseOverlay() {
        background = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BOARD);
        createSoundbuttons();
        bW = (int) (background.getWidth() * Game.SCALE);
        bH = (int) (background.getHeight() * Game.SCALE);
        bX = Game.GAME_WIDTH / 2 - bW / 2;
        bY = (int) (25 * Game.SCALE);

    }

    public void update() {
        musicButton.update();
        sfxButton.update();
    }

    public void draw(Graphics g) {
        g.drawImage(background, bX, bY, bW, bH, null);

        musicButton.draw(g);
        sfxButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sfxButton))
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());

        // once pressed the buttons return to the unpressed sprite
        musicButton.resetBooleans();
        sfxButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}