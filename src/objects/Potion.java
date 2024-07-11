package objects;

import main.Game;

public class Potion extends GameObject {
  private float hoverOffset;
  private int maxHoverOffset, hoverDir = 1;
  public Potion(int x, int y, int objTyoe) {
    super(x, y,objTyoe);
    doAnimation = true;
    initHitbox(7,14);
    xDdrawOffset = (int)(3* Game.SCALE);
    yDdrawOffset = (int)(2* Game.SCALE);
    maxHoverOffset = (int)(10* Game.SCALE);
  }

  public void update(){
    updateAnimationTick();
    updateHover();
  }

  private void updateHover() {
  hoverOffset += (0.5 * Game.SCALE * hoverDir);
  if(hoverOffset >= maxHoverOffset)
    hoverDir = -1;
  else if(hoverOffset <= 0)
    hoverDir = 1;

  hitbox.y = y + hoverOffset;
  }
}
