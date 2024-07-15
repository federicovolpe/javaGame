package objects;

import main.Game;

public class Tree extends GameObject {
  public Tree(int x, int y, int objType) {
    super(x, y, objType);
    yDrawOffset = Game.TILES_SIZE;
  }
}
