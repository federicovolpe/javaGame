package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel {

    // crezione di una classe di tipo mouse inputs per poter riferire ad un
    // unico oggetto
    private final MouseInputs mouseInputs = new MouseInputs(this);

    // variables that are useful
    private float xDelta = 100, yDelta = 100;// remembers the coordinates position of the object
    private Dimension panelDimension = new Dimension(1280, 800);
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 2;
    private int PlayerAction = ATTACK_JUMP_1;
    private int PlayerDirection = -1;
    private boolean moving = false;

    /**
     * adds all the keylisteners
     */
    public GamePanel() {
        importImg();
        loadAnimations();
        addKeyListener(new KeyboardInputs(this));

        // classe che consente di ricevere inputs dal mouse
        // come clicked pressed e released
        addMouseListener(mouseInputs);

        // classe che osserva i movimenti del mouse
        addMouseMotionListener(mouseInputs);
        setPanelSize();
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/resources/sprites.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method to set the size of the panel, if we do it on the gamewindow the border
     * interfere
     */
    private void setPanelSize() {
        setMinimumSize(panelDimension);
        setMaximumSize(panelDimension);
        setPreferredSize(panelDimension);
    }

    public void setDirection(int direction) {
        this.PlayerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * method that redraws a rectangle with a simple loop
     * 
     * @g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateAnimationTick();
        setAnimation();
        updatePos();
        g.drawImage(animations[PlayerAction][aniIndex], (int) xDelta, (int) yDelta, 128, 80, null);
    }

    private void setAnimation() {
        if (moving) {
            PlayerAction = RUNNING;
        } else {
            PlayerAction = IDLE;
        }
    }

    private void updatePos() {
        if (moving) {
            switch (PlayerDirection) {
                case LEFT:
                    xDelta -= 10;
                    break;
                case UP:
                    yDelta -= 10;
                    break;
                case RIGHT:
                    xDelta += 10;
                    break;
                case DOWN:
                    yDelta += 10;
                    break;
            }
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(PlayerAction)) {
                aniIndex = 0;
            }
        }

    }
}
