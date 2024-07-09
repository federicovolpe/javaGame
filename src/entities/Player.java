package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import static utils.Constants.PlayerConstants.*;
import utils.LoadSave;
import static utils.HelpMethods.*;
import java.awt.geom.Rectangle2D;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 7;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false, jumping = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f * Game.SCALE;
    private int[][] levelData;
    private float xDdrawOffset = 21 * Game.SCALE;
    private float yDdrawOffset = 4 * Game.SCALE;

    // jumping parameters
    private float airSpeed = 0f; // speed of the fall in a given moment
    private float gravity = utils.Constants.GRAVITY;
    private float jumpSpeed = -4.f * Game.SCALE;
    private float fallSpeedAfterCollision = .5f * Game.SCALE;
    private boolean inAir = false;

    // Status Bar
    private BufferedImage statusBarImage;
    private int statusbarWidth = (int) (192 * Game.SCALE);
    private int statusbarHeight = (int) (58 * Game.SCALE);
    private int statusbarX = (int) (10 * Game.SCALE);
    private int statusbarY = (int) (10 * Game.SCALE);

    private int heathBarWidth = (int) (150 * Game.SCALE);
    private int heathBarHeight = (int) (4 * Game.SCALE);
    private int heathBarXStart = (int) (34 * Game.SCALE);
    private int heathBarYStart = (int) (14 * Game.SCALE);

    private final int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = heathBarWidth;

    // changing directions
    private int flipX = 0;
    private int flipW = 1;

    // attack box
    private Rectangle2D.Float attackBox;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();
        updateAttackBox();
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (int) (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * heathBarWidth);
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[playerAction][aniIndex],
                (int) (hitbox.x - xDdrawOffset) - lvlOffset + flipX,
                (int) (hitbox.y - yDdrawOffset),
                width * flipW,
                height,
                null);
        drawHitbox(g, lvlOffset);
        drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImage, statusbarX, statusbarY, statusbarWidth, statusbarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(heathBarXStart + statusbarX, heathBarYStart + statusbarY, healthWidth, heathBarHeight);
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[7][8];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
            }
        }

        statusBarImage = LoadSave.getSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] levelData) {
        this.levelData = levelData;
        if (!isEntityOnFloor(hitbox, levelData))
            inAir = true;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if (inAir)
            if (airSpeed < 0)
                playerAction = JUMPING;
            else
                playerAction = FALLING;
        if (attacking)
            playerAction = ATTACK_1;
        if (startAni != playerAction)
            resetAnimation();
    }

    private void resetAnimation() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;
        if (jumping)
            jump();
        if (!inAir)
            if (!left && !right || right && left)
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir)
            if (!isEntityOnFloor(hitbox, levelData))
                inAir = true;

        if (inAir) { // if the player can move up or down
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updataeXPos(xSpeed);
            } else { // if we cannot move up or down(hitting the roof or hitting the floor)
                hitbox.y = getYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) // the player is going down and hitting something
                    resetInAir();
                else // the player is going up an hitting the roof
                    airSpeed = fallSpeedAfterCollision;
                updataeXPos(xSpeed);
            }
        } else
            updataeXPos(xSpeed);

        moving = true;
    }

    private void jump() {
        if (inAir) // if the player is already in the air do nothing
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updataeXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {// theres still space between the player and the wall
            hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0)
            currentHealth = 0;
        // game over
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isDown() {
        return down;
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

    public void setJump(boolean jumping) {
        this.jumping = jumping;
    }

    public void resetBooleans() {
        up = false;
        down = false;
        left = false;
        right = false;
    }
}
