package entities;

import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;
import static utils.Constants.Directions.*;
import main.Game;

public abstract class Enemy extends Entity {

    private int aniIndex, enemyState, enemyType;
    private int aniTick, aniSpeed = 7;
    private boolean firstUpdate = true;
    private boolean inAir = false;
    private float fallSpeed = 7;
    private float gravity = utils.Constants.GRAVITY;
    private float walkSpeed = .3f * Game.SCALE;
    private int walkDir = LEFT;

    public Enemy(float x, float y, int height, int width, int enemyType) {
        super(x, y, height, width);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
                // attacking = false;
            }
        }
    }

    public void update(int[][] lvlData) {
        // updatePos();
        updateMove(lvlData);
        updateAnimationTick();
        // setAnimation();
    }

    public void updateMove(int[][] lvlData) {
        if (firstUpdate) {// just in the initial update
            if (!isEntityOnFloor(hitbox, lvlData)) // entity is in the air
                inAir = true;
            firstUpdate = false;
        }
        if (inAir) { // keep falling down
            if (CanMoveHere(hitbox.x, hitbox.y,
                    hitbox.width, hitbox.width, lvlData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = getYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        } else { // just touched the floor
            switch (enemyState) {
                case (IDLE):
                    enemyState = RUNNING;
                    break;
                case (RUNNING):
                    float xSpeed = 0;
                    if (walkDir == LEFT)
                        xSpeed = -walkSpeed;
                    else
                        xSpeed = walkSpeed;
                    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                        if (isFloor(hitbox, xSpeed, lvlData)) {
                            hitbox.x += xSpeed;
                            return;
                        }
                    changeWalkDir();
                default:
                    break;
            }
        }
    }

    private void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else if (walkDir == RIGHT)
            walkDir = LEFT;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

}
