package objects;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.ObjectConstants.*;

public class ObjectManager {
  private final Playing playing;
  private BufferedImage[][] potionsImgs, containerImgs;
  private List<Potion> potions;
  private List<GameContainer> containers;

  public ObjectManager(Playing playing) {
    this.playing = playing;
    loadImgs();
  }

  private void loadImgs() {
    BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.POTION_ATLAS);
    potionsImgs = new BufferedImage[2][7];

    for (int i = 0; i < potionsImgs.length; i++) {
      for (int j = 0; j < potionsImgs[0].length; j++) {
        potionsImgs[i][j] = potionSprite.getSubimage(12 * j, 16 * i, 12, 16);
      }
    }

    BufferedImage containerSprite = LoadSave.getSpriteAtlas(LoadSave.CONTAINER_ATLAS);
    containerImgs = new BufferedImage[2][8];

    for (int i = 0; i < containerImgs.length; i++) {
      for (int j = 0; j < containerImgs[0].length; j++) {
        containerImgs[i][j] = containerSprite.getSubimage(40 * j, 30 * i, 40, 30);
      }
    }
  }

  public void update() {
    for (Potion p : potions)
      if (p.isActive())
        p.update();

    for (GameContainer c : containers)
      if (c.isActive())
        c.update();
  }

  public void draw(Graphics g, int xLvlOffset) {
    drawPotions(g, xLvlOffset);
    drawContainers(g, xLvlOffset);
  }

  private void drawContainers(Graphics g, int xLvlOffset) {
    for (GameContainer c : containers)
      if (c.isActive()) {
        int type = 0;
        if (c.getObjType() == BARREL)
          type = 1;
        g.drawImage(containerImgs[type][c.getAniIndex()],
            (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset),
            (int) (c.getHitbox().y - c.getyDrawOffset()),
            CONTAINER_WIDTH,
            CONTAINER_HEIGHT, null);
        c.drawHitbox(g, xLvlOffset);
      }
  }

  private void drawPotions(Graphics g, int xLvlOffset) {
    for (Potion p : potions)
      if (p.isActive()) {
        int type = 0;
        if (p.getObjType() == RED_POTION)
          type = 1;

        g.drawImage(potionsImgs[type][p.getAniIndex()],
            (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
            (int) (p.getHitbox().y - p.getyDrawOffset()),
            POTION_WIDTH,
            POTION_HEIGHT, null);
        p.drawHitbox(g, xLvlOffset);
      }

  }

  public void loadObjects(Level newLevel) {
    potions = new ArrayList<>(newLevel.getPotions());
    containers = new ArrayList<>(newLevel.getContainers());
  }

  public void checkObjectTouched(Rectangle2D.Float hitbox) {
    for (Potion p : potions)
      if (p.isActive())
        if (hitbox.intersects(p.getHitbox())) {
          p.setActive(false);
          applyEffectToPlayer(p);
        }
  }

  private void applyEffectToPlayer(Potion p) {
    if (p.getObjType() == RED_POTION)
      playing.getPlayer().changeHealth(RED_POTION_VALUE);
    else
      playing.getPlayer().changePower(BLUE_POTION_VALUE);
  }

  public void checkObjectHit(Rectangle2D.Float attackbox) {
    for (GameContainer c : containers) {
      if (c.isActive() && !c.doAnimation) {
        if (c.getHitbox().intersects(attackbox)) {
          c.setAnimation(true);
          int type = 0;
          if (c.getObjType() == BARREL)
            type = 1;
          potions.add(new Potion(
              (int) (c.getHitbox().x + c.getHitbox().width / 2 ),
              (int) (c.getHitbox().y - c.getHitbox().height/2),
              type));
          return;
        }
      }
    }
  }

  public void resetAllObject() {

    loadObjects(playing.getLevelManager().getCurrLevel());
    for (Potion p : potions)
      p.reset();
    for (GameContainer c : containers)
      c.reset();
  }
}
