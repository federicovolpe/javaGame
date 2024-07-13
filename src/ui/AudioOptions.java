package ui;

import main.Game;
import java.awt.*;
import java.awt.event.MouseEvent;
import static utils.Constants.UI.PauseButtons.SOUND_SIZE;
import static utils.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utils.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

public class AudioOptions {
  private VolumeSlider volumeSlider;
  private SoundButton musicButton, sfxButton;
  private final Game game;

  public AudioOptions(Game game) {
    this.game = game;
    createSoundbuttons();
    createVolumeButton();
  }

  private void createVolumeButton() {
    int vX = (int) (309 * Game.SCALE);
    int vY = (int) (278 * Game.SCALE);
    volumeSlider = new VolumeSlider(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
  }
  public void createSoundbuttons() {
    int soundX = (int) (450 * Game.SCALE);
    int musicY = (int) (150 * Game.SCALE);
    int sfxY = (int) (186 * Game.SCALE);

    musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
    sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
  }

  public void update() {
    musicButton.update();
    sfxButton.update();
    volumeSlider.update();
  }

  public void draw(Graphics g) {
    musicButton.draw(g);
    sfxButton.draw(g);
    volumeSlider.draw(g);
  }

  public void mouseDragged(MouseEvent e) {
    if (volumeSlider.isMousePressed()) {
      float valueBefore = volumeSlider.getFloatValue();
      volumeSlider.changeX(e.getX());
      float valueAfter = volumeSlider .getFloatValue();
      if(valueBefore != valueAfter)
        game.getAudioPlayer().setVolume(valueAfter);
    }
  }

  public void mousePressed(MouseEvent e) {
    if (isIn(e, musicButton))
      musicButton.setMousePressed(true);
    else if (isIn(e, sfxButton))
      sfxButton.setMousePressed(true);
    else if (isIn(e, volumeSlider))
      volumeSlider.setMousePressed(true);
  }

  public void mouseReleased(MouseEvent e) {
    if (isIn(e, musicButton)) {
      if (musicButton.isMousePressed()) {
        musicButton.setMuted(!musicButton.isMuted());
        game.getAudioPlayer().toggleSongMute();
      }
    } else if (isIn(e, sfxButton)) {
      if (sfxButton.isMousePressed()){
        sfxButton.setMuted(!sfxButton.isMuted());
        game.getAudioPlayer().toggleEffectMute();
    }
    }

    // once pressed the buttons return to the unpressed sprite
    musicButton.resetBooleans();
    sfxButton.resetBooleans();
    volumeSlider.resetBools();
  }

  public void mouseMoved(MouseEvent e) {
    musicButton.setMouseOver(false);
    sfxButton.setMouseOver(false);
    volumeSlider.setMouseOver(false);

    if (isIn(e, musicButton))
      musicButton.setMouseOver(true);
    else if (isIn(e, sfxButton))
      sfxButton.setMouseOver(true);
    else if (isIn(e, volumeSlider))
      volumeSlider.setMouseOver(true);
  }

  private boolean isIn(MouseEvent e, PauseButton b) {
    return b.getBounds().contains(e.getX(), e.getY());
  }

}
