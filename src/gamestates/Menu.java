package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static main.Game.GAME_WIDTH;
import ui.MenuButton;
import utils.LoadSave;

public class Menu extends State implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundBoard, menuBackground;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        menuBackground = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
    }

    private void loadBackground() {
        backgroundBoard = LoadSave.getSpriteAtlas(LoadSave.MENU_BOARD);
        menuWidth = (int) (backgroundBoard.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundBoard.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (Game.SCALE * 45);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameStates.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameStates.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, GameStates.QUIT);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(menuBackground, 0, 0, GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundBoard, menuX, menuY, menuWidth, menuHeight, null);
        for (MenuButton menuButton : buttons)
            menuButton.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameStates.state = GameStates.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton menuButton : buttons)
            menuButton.setMouseOver(false);
        for (MenuButton menuButton : buttons) {
            if (isIn(e, menuButton)) {
                menuButton.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            if (isIn(e, menuButton)) {
                menuButton.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            if (isIn(e, menuButton)) {
                if (menuButton.getMousePressed()) {
                    menuButton.applyGameState();
                    break;
                }
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton menuButton : buttons)
            menuButton.resetBools();
    }

    @Override
    public void update() {
        for (MenuButton menuButton : buttons) {
            menuButton.update();
        }
    }

}
