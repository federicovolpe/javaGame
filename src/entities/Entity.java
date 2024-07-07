package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

public abstract class Entity {
    protected float x, y;
    protected int height, width;
    protected Rectangle2D.Float hitbox;

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
        g.drawRect((int) hitbox.x - xLvlOffset,(int) hitbox.y,(int) hitbox.width,(int) hitbox.height);
    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D getHitbox() {
        return hitbox;
    }
}
