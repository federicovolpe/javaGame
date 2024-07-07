package entities;

import static utils.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity {

    private int aniIndex, enemyState, enemyType;
    private int aniTick, aniSpeed = 7;

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

    public void update() {
        // updatePos();
        updateAnimationTick();
        // setAnimation();
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

}
