package entities;

import java.awt.geom.Rectangle2D;
import main.Game;
import static utils.Constants.*;
import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

public abstract class Enemy extends Entity {

    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;
    

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, height, width);
        this.enemyType = enemyType;

        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = .5f * Game.SCALE;
    }

    // the first update is to put everyone to the ground
    protected void firstUpdateCheck(int[][] lvlData) {
        if (!isEntityOnFloor(hitbox, lvlData)) // entity is in the air
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y,
                hitbox.width, hitbox.width, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = getYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.getY() / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;
        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            if (isFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().getY() / Game.TILES_SIZE);
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                if (isSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int abs = (int) Math.abs((player.hitbox.x - hitbox.x));
        return abs <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int abs = (int) Math.abs((player.hitbox.x - hitbox.x));
        return abs <= attackDistance;
    }

    /**
     * makes the enemy change direction according to the player relative position
     */
    protected void turnsTwardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        if (player.hitbox.x < hitbox.x){
            walkDir = LEFT;
        }
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;
                switch (state) {
                    case ATTACK, HIT -> state = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }

    public void hurt(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0)
            newState(DEAD);
        else
            newState(HIT);
    }

    // the enemy checks if he has hit the player
    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox))
            player.changeHealth(- getEnemyDmg(enemyType));
        attackChecked = true;
    }

    /**
     * new fresh animation whenever the state is changed
     * 
     * @param state
     */
    protected void newState(int state) {
        this.state = state;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else if (walkDir == RIGHT)
            walkDir = LEFT;
    }

    public boolean isActive() {
        return active;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;

        newState(IDLE);
        active = true;
        airSpeed = 0;// stap falling
    }
}