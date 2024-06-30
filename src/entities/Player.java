package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

import static utils.Constants.PlayerConstants.*;
import utils.LoadSave;
import static utils.HelpMethods.CanMoveHere;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 2;
    private int PlayerAction = ATTACK_JUMP_1;
    // private int PlayerDirection = -1;
    private boolean moving = false, attacking = false; // jumping = false;
    private boolean left, up, right, down;
    private int playerSpeed = 5;
    private int[][] levelData;
    private float xDdrawOffset = 21 * Game.SCALE;
    private float yDdrawOffset = 4 * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[PlayerAction][aniIndex],
                (int) (hitbox.x - xDdrawOffset),
                (int) (hitbox.y - yDdrawOffset), width, height, null);
        drawHitbox(g);
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
            }
        }
    }

    public void loadLvlData(int[][] levelData) {
        this.levelData = levelData;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(PlayerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void updatePos() {
        moving = false;
        if (!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed = 0;

        if (left && !right)
            xSpeed = -playerSpeed;
        else if (right && !left)
            xSpeed = +playerSpeed;

        if (up && !down)
            ySpeed = -playerSpeed;
        else if (down && !up)
            ySpeed = +playerSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
        }
    }

    private void setAnimation() {
        int startAni = PlayerAction;

        if (moving)
            PlayerAction = RUNNING;
        else
            PlayerAction = IDLE;

        if (attacking)
            PlayerAction = ATTACK_1;

        if (startAni != PlayerAction)
            resetAnimation();
    }

    private void resetAnimation() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setUp(boolean direction) {
        this.up = direction;
    }

    public void setLeft(boolean direction) {
        this.left = direction;
    }

    public void setRight(boolean direction) {
        this.right = direction;
    }

    public void setDown(boolean direction) {
        this.down = direction;
    }

    public void resetBooleans() {
        up = false;
        down = false;
        left = false;
        right = false;
    }
}
