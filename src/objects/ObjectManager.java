package objects;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.ObjectConstants.*;
import static utils.Constants.Projectiles.*;
import static utils.HelpMethods.canCannonSeePlayer;
import static utils.HelpMethods.isProjectileHittingLevel;

public class ObjectManager {
  private final Playing playing;
  private BufferedImage[][] potionsImgs, containerImgs;
  private BufferedImage[] cannonsImgs;
  private BufferedImage spikeImg;
  private BufferedImage cannonBallImg;
  private List<Potion> potions;
  private List<GameContainer> containers;
  private List<Spike> spikes;
  private List<Cannon> cannons;
  private List<Projectile> projectiles = new ArrayList<>();

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
    spikeImg = LoadSave.getSpriteAtlas(LoadSave.TRAP_ATLAS);
    cannonsImgs = new BufferedImage[7];

    BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.CANNON_ATLAS);
    for (int i = 0; i < cannonsImgs.length; i++)
      cannonsImgs[i] = tmp.getSubimage(i * 40, 0, 40, 26);

    cannonBallImg = LoadSave.getSpriteAtlas(LoadSave.CANNON_BALL);
  }

  public void update(int[][] lvlData, Player player) {
    for (Potion p : potions)
      if (p.isActive())
        p.update();

    for (GameContainer c : containers)
      if (c.isActive())
        c.update();

    updateCannons(lvlData, player);
    updateProjectiles(lvlData, player);
  }

  private void updateProjectiles(int[][] lvlData, Player player) {
    for(Projectile p : projectiles){
      if(p.isActive()) p.updatePos();
      if(p.isActive() && p.getHitbox().intersects(player.getHitbox())) {
        player.changeHealth(-10);
        System.out.println("player colpito dal proiettile " + -10);
        p.setActive(false);
      }else if(isProjectileHittingLevel(p, lvlData)){
        p.setActive(false);
      }
    }
  }

  private void updateCannons(int[][] lvlData, Player player) {
    for (Cannon c : cannons) {
      if (!c.doAnimation)
        if (c.getTileY() == player.getTileY())
          if (isPlayerInRange(c, player))
            if (isPlayerInFrontOfCannon(c, player))
              if (canCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY()))
               c.setAnimation(true);
      c.update();
      if(c.getAniIndex() == 4 && c.getAniTick() == 0){
        shootCannon(c);
      }
    }
  }

  private void shootCannon(Cannon c) {
    // creating a new projectile
    int dir = 1;
    if(c.getObjType() == CANNON_LEFT)
      dir =  -1;
    projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));
  }

  private boolean isPlayerInFrontOfCannon(Cannon c, Player player) {
    if (c.getObjType() == CANNON_LEFT) {
      return c.getHitbox().x > player.getHitbox().x;
    } else return c.getHitbox().x < player.getHitbox().x;
  }

  // TODO copiato da enemy, refactoring?
  public boolean isPlayerInRange(Cannon c, Player p) {
    int abs = (int) Math.abs((p.getHitbox().x - c.getHitbox().x));
    return abs <= Game.TILES_SIZE * 5;
  }

  public void draw(Graphics g, int xLvlOffset) {
    drawPotions(g, xLvlOffset);
    drawContainers(g, xLvlOffset);
    drawCannons(g, xLvlOffset);
    drawTraps(g, xLvlOffset);
    drawProjectiles(g, xLvlOffset);
  }

  private void drawProjectiles(Graphics g, int xLvlOffset) {
    for(Projectile p : projectiles)
      if(p.isActive()) {
        g.drawImage(cannonBallImg,
            (int)(p.getHitbox().x - xLvlOffset),
            (int) p.getHitbox().y ,
            CANNON_BALL_WIDTH,
            CANNON_BALL_HEIGHT,null );
      }
  }

  private void drawCannons(Graphics g, int xlvlOffset) {
    for (Cannon c : cannons) {
      int x = (int) (c.getHitbox().x - xlvlOffset);
      int width = CANNON_WIDTH;
      if (c.getObjType() == CANNON_RIGHT) {
        x += width;
        width *= -1;
      }
      g.drawImage(cannonsImgs[c.getAniIndex()],
          x,
          (int) (c.getHitbox().y),
          width,
          CANNON_HEIGHT,
          null);
    }
  }

  public void checkSpikesTouched(Player p) {
    for (Spike s : spikes)
      if (s.getHitbox().intersects(p.getHitbox()))
        p.kill();
  }

  private void drawTraps(Graphics g, int xLvlOffset) {
    for (Spike s : spikes)
      g.drawImage(spikeImg,
          (int) (s.getHitbox().x - xLvlOffset),
          (int) (s.getHitbox().y - s.getyDrawOffset()),
          SPIKE_WIDTH,
          SPIKE_HEIGHT, null);
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
    spikes = newLevel.getSpikes();
    cannons = newLevel.getCannons();
    projectiles.clear();
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
              (int) (c.getHitbox().x + c.getHitbox().width / 2),
              (int) (c.getHitbox().y - c.getHitbox().height / 2),
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
    for (Cannon c : cannons)
      c.reset();
  }
}
