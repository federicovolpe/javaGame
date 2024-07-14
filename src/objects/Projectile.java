package objects;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Projectiles.*;
import static utils.HelpMethods.isSolid;

public class Projectile {
  private final Rectangle2D.Float hitbox;
  private boolean active = true;
  private final int dir;

  public Projectile(int x, int y, int dir) {
    int xOffset = (int) (-3 * Game.SCALE);
    int yOffset = (int) (5 * Game.SCALE);
    if (dir == 1)
      xOffset = (int) (29 * Game.SCALE);

    hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
    this.dir = dir;
  }

  public void updatePos() {
    hitbox.x += dir * SPEED;
  }

  public static boolean isProjectileHittingLevel(Projectile p, int[][] lvldata){
    return isSolid(p.getHitbox().x + p.getHitbox().width /2, p.getHitbox().y + p.getHitbox().height/2, lvldata);
  }

  public Rectangle2D.Float getHitbox() {
    return hitbox;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
