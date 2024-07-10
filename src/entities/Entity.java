package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Game;

public abstract class Entity {
    protected float x, y;
    protected int height, width;
    protected Rectangle2D.Float hitbox;

    protected int aniTick, aniIndex, state;
    protected float airSpeed;
    protected boolean inAir = false;

    protected int maxHealth;
    protected int currentHealth;

    protected Rectangle2D.Float attackBox;
    
    protected float walkSpeed;

    public Entity(float x, float y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * method for the debugging of the hitbox
     * 
     * @param g
     */
    protected void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int)(height * Game.SCALE));
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    protected void drawAttackBox(Graphics g, int lvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int getState(){
        return state;
    }
    
    public int getAniIndex() {
        return aniIndex;
    }
}
