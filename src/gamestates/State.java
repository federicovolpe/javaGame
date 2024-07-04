package gamestates;

import java.awt.event.MouseEvent;
import ui.MenuButton;
import main.Game;

public class State {
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    // if the mouse of the player is in the sprite of the button
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
