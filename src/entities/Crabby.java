package entities;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Directions.*;

import main.Game;

public class Crabby extends Enemy {

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

    public void update(int[][] lvlData, Player player) {
        // updatePos();
        updateMove(lvlData, player);
        updateAnimationTick();
        // setAnimation();
    }

    public void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir) { // keep falling down
            updateInAir(lvlData);
        } else { // just touched the floor
            switch (enemyState) {
                case (IDLE):
                    newState(RUNNING);
                    break;
                case (RUNNING):
                    if (canSeePlayer(lvlData, player))
                        turnsTwardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(ATTACK);
                    move(lvlData);
                default:
                    break;
            }
        }
    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }
}