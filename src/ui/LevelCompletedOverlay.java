package ui;

import gamestates.GameStates;
import gamestates.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utils.Constants.UI.URMButtons.*;
import utils.LoadSave;

public class LevelCompletedOverlay {

    private Playing playing;
    private UrmButtons menu, next;
    private BufferedImage img;
    private int bgx, bgy, bgw, bgh;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initImg() {
        img = LoadSave.getSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgw = (int) (img.getWidth() * Game.SCALE);
        bgh = (int) (img.getHeight() * Game.SCALE);
        bgx = Game.GAME_WIDTH / 2 - bgw / 2;
        bgy = (int) (75 * Game.SCALE);
    }

    private void initButtons() {
        int menux = (int) (330 * Game.SCALE);
        int nextx = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new UrmButtons(nextx, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtons(menux, y, URM_SIZE, URM_SIZE, 2);
    }

    public void update() {
        next.update();
        menu.update();
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgx, bgy, bgw, bgh, null);
        next.draw(g);
        menu.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);
        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(next, e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()){
                playing.resetAll();
                GameStates.state = GameStates.MENU;
            }
        } else if (isIn(next, e))
            if (next.isMousePressed())
                playing.loadNextLevel();
        menu.resetBools();
        next.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(next, e))
            next.setMousePressed(true);
    }

    private boolean isIn(UrmButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
