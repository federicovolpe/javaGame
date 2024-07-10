package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Game;
import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;

public class Crabby extends Enemy {

    
    private int attackBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(22 , 19 );
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y,82, 19);
        attackBoxOffsetX = (int) (Game.SCALE * 30);
    }

    public void update(int[][] lvlData, Player player) {
        // updatePos();
        updateBehaviour(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
        // setAnimation();
    }

    public void draw(Graphics g, int xLvlOffset) {
        g.setColor(Color.GREEN);
        drawAttackBox(g, xLvlOffset);
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    public void updateBehaviour(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir) { // keep falling down
            updateInAir(lvlData);
        } else { // just touched the floor
            switch (state) {
                case (IDLE):
                    newState(RUNNING);
                    break;
                case (RUNNING):
                    if (canSeePlayer(lvlData, player)) {
                        turnsTwardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkPlayerHit(attackBox, player);

                    break;
                case HIT:
                    break;
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