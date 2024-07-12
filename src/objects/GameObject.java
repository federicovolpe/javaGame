package objects;

import java.awt.geom.Rectangle2D;

import main.Game;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.ObjectConstants.*;

import java.awt.Graphics;
import java.awt.Color;

public class GameObject {
    protected int x, y, objType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject (int x, int y, int objType){
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int)(height * Game.SCALE));
    }

    public void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSprite(objType)) {
                aniIndex = 0;
                if (objType == BARREL || objType == BOX) {
                    doAnimation = false;
                    active = false;
                } else if (objType == CANNON_LEFT || objType == CANNON_RIGHT)
                    doAnimation = false;
            }
        }
    }

    public void reset(){
        aniIndex = 0;
        aniTick = 0;
        active = true;
        doAnimation = objType != BARREL && objType != BOX && objType != CANNON_LEFT && objType != CANNON_RIGHT;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setAnimation(boolean doAnimation){
        this.doAnimation = doAnimation;
    }

    public int getObjType() {
        return objType;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
