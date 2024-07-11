package objects;

import main.Game;

import static utils.Constants.ObjectConstants.BARREL;
import static utils.Constants.ObjectConstants.BOX;

public class GameContainer extends GameObject{

  public GameContainer(int x, int y, int objType) {
    super(x, y, objType);
    createHitbox();
  }

  private void createHitbox() {
    if(objType == BOX){
      initHitbox(25,18);
      xDdrawOffset = (int)(7* Game.SCALE);
      yDdrawOffset = (int)(12* Game.SCALE);
    }else {
      initHitbox(23,25);
      xDdrawOffset = (int)(8* Game.SCALE);
      yDdrawOffset = (int)(5* Game.SCALE);
    }
    hitbox.y += yDdrawOffset + (int)(Game.SCALE * 2);
    hitbox.x += xDdrawOffset /2;
  }

  public void update() {
    if(doAnimation)
      updateAnimationTick();
  }
}
