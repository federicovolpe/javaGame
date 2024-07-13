package entities;

import Audio.AudioPlayer;
import gamestates.Playing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;

import static utils.Constants.*;
import static utils.Constants.ObjectConstants.getSprite;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

import utils.LoadSave;

public class Player extends Entity {

  private BufferedImage[][] animations;
  private boolean moving = false, attacking = false, jumping = false;
  private boolean left, right;
  private int[][] levelData;
  private final float xDdrawOffset = 21 * Game.SCALE;
  private final float yDdrawOffset = 4 * Game.SCALE;

  // jumping parameters
  private final float jumpSpeed = -4.f * Game.SCALE;
  private final float fallSpeedAfterCollision = .5f * Game.SCALE;
  private boolean inAir = false;

  // Status Bar
  private BufferedImage statusBarImage;
  private final int statusbarWidth = (int) (192 * Game.SCALE);
  private final int statusbarHeight = (int) (58 * Game.SCALE);
  private final int statusbarX = (int) (10 * Game.SCALE);
  private final int statusbarY = (int) (10 * Game.SCALE);

  private final int heathBarWidth = (int) (150 * Game.SCALE);
  private final int heathBarHeight = (int) (4 * Game.SCALE);
  private final int heathBarXStart = (int) (34 * Game.SCALE);
  private final int heathBarYStart = (int) (14 * Game.SCALE);
  /* power
  private final int powerBarWidth = (int) (150 * Game.SCALE);
  private final int powerBarHeight = (int) (4 * Game.SCALE);
  private final int powerBarXStart = (int) (34 * Game.SCALE);
  private final int powerBarYStart = (int) (14 * Game.SCALE);*/
  private final int maxPower = 100;
  private int currentPower = maxPower;

  private int healthWidth = heathBarWidth;

  private boolean attackChecked;
  private final Playing playing;

  // changing directions
  private int flipX = 0;
  private int flipW = 1;
  private int tileY = 0;

  public Player(float x, float y, int width, int height, Playing playing) {
    super(x, y, height, width);
    this.playing = playing;
    this.state = IDLE;
    this.maxHealth = 100;
    this.currentHealth = maxHealth;
    this.walkSpeed = 2.0f * Game.SCALE;
    loadAnimations();
    initHitbox(20, 27);
    initAttackBox();
  }

  public void setSpawn(Point spawn) {
    this.x = spawn.x;
    this.y = spawn.y;
    hitbox.x = x;
    hitbox.y = y;
  }

  private void initAttackBox() {
    attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
  }

  public void update() {
    updateHealthBar();
    if (currentHealth <= 0) {
      if (state != DEAD) {
        state = DEAD;
        aniTick = 0;
        aniIndex = 0;
        playing.setPlayerDying(true);
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
      } else if (aniIndex == getSprite(DEAD) - 1 && aniTick >= ANI_SPEED - 1) { // is on the last sprite of the animation game overrr≈
        playing.setGameOver(true);
        playing.getGame().getAudioPlayer().stopSong();
        playing.getGame().getAudioPlayer().playSong(AudioPlayer.GAMEOVER);
      } else
        updateAnimationTick();
      //playing.setGameOver(true);
      return;
    }

    updateAttackBox();
    updatePos();
    if (moving) {
      checkPotionTouched();
      checkSpikesTouched();
      tileY = (int) (hitbox.y / Game.TILES_SIZE);
    }
    if (attacking)
      checkAttack();

    updateAnimationTick();
    setAnimation();
  }

  private void checkSpikesTouched() {
    playing.checkSpikesTouched(this);
  }

  private void checkPotionTouched() {
    playing.checkPotionTouched(hitbox);
  }

  private void checkAttack() {
    if (attackChecked || aniIndex != 1)
      return;
    attackChecked = true;
    playing.checkEnemyHit(attackBox);
    playing.checkObjectAttacked(attackBox);
    playing.getGame().getAudioPlayer().playAttackSound();
  }

  private void updateAttackBox() {
    if (right) {
      attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
    } else if (left) {
      attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
    }
    attackBox.y = hitbox.y + (int) (Game.SCALE * 10);
  }

  private void updateHealthBar() {
    healthWidth = (int) ((currentHealth / (float) maxHealth) * heathBarWidth);
  }

  public void render(Graphics g, int lvlOffset) {
    System.out.println("player inizia l-animazione " + state);
    g.drawImage(animations[state][aniIndex],
        (int) (hitbox.x - xDdrawOffset) - lvlOffset + flipX,
        (int) (hitbox.y - yDdrawOffset),
        width * flipW,
        height,
        null);
    drawHitbox(g, lvlOffset);
    drawAttackBox(g, lvlOffset);
    drawUI(g);
  }

  private void drawUI(Graphics g) {
    g.drawImage(statusBarImage, statusbarX, statusbarY, statusbarWidth, statusbarHeight, null);
    g.setColor(Color.RED);
    g.fillRect(heathBarXStart + statusbarX, heathBarYStart + statusbarY, healthWidth, heathBarHeight);
  }

  private void loadAnimations() {
    BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

    animations = new BufferedImage[7][8];
    for (int i = 0; i < animations.length; i++) {
      for (int j = 0; j < animations[i].length; j++) {
        animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
      }
    }

    statusBarImage = LoadSave.getSpriteAtlas(LoadSave.STATUS_BAR);
  }

  public void loadLvlData(int[][] levelData) {
    this.levelData = levelData;
    if (!isEntityOnFloor(hitbox, levelData))
      inAir = true;
  }

  private void updateAnimationTick() {
    aniTick++;
    if (aniTick >= ANI_SPEED) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex >= GetSpriteAmount(state)) {
        aniIndex = 0;
        attacking = false;
        attackChecked = false;
      }
    }
  }

  private void setAnimation() {
    int startAni = state;

    if (moving)
      state = RUNNING;
    else
      state = IDLE;
    if (inAir){
      if (airSpeed < 0)
        state = JUMPING;
      else
        state = FALLING;}
    if (attacking) {
      state = ATTACK_1;
      if (startAni != ATTACK_1) {
        System.out.println("animatio attack !!!!" + ATTACK_1);
        aniIndex = 1;
        aniTick = 0;
        return;
      }
    }
    if (startAni != state)
      resetAnimation();
  }

  private void resetAnimation() {
    aniTick = 0;
    aniIndex = 0;
  }

  private void updatePos() {
    moving = false;
    if (jumping)
      jump();
    if (!inAir)
      if (!left && !right || right && left)
        return;

    float xSpeed = 0;

    if (left) {
      xSpeed -= walkSpeed;
      flipX = width;
      flipW = -1;
    }
    if (right) {
      xSpeed += walkSpeed;
      flipX = 0;
      flipW = 1;
    }

    if (!inAir)
      if (!isEntityOnFloor(hitbox, levelData))
        inAir = true;

    if (inAir) { // if the player can move up or down
      if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
        hitbox.y += airSpeed;
        airSpeed += GRAVITY;
        updataeXPos(xSpeed);
      } else { // if we cannot move up or down(hitting the roof or hitting the floor)
        hitbox.y = getYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
        if (airSpeed > 0) // the player is going down and hitting something
          resetInAir();
        else // the player is going up an hitting the roof
          airSpeed = fallSpeedAfterCollision;
        updataeXPos(xSpeed);
      }
    } else
      updataeXPos(xSpeed);

    moving = true;
  }

  private void jump() {
    if (inAir) // if the player is already in the air do nothing
      return;
    inAir = true;
    airSpeed = jumpSpeed;
    playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
  }

  private void resetInAir() {
    inAir = false;
    airSpeed = 0;
  }

  private void updataeXPos(float xSpeed) {
    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
      hitbox.x += xSpeed;
    } else {// theres still space between the player and the wall
      hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
    }
  }

  public void changeHealth(int value) {
    currentHealth += value;

    if (currentHealth <= 0)
      currentHealth = 0;
      // game over
    else if (currentHealth >= maxHealth)
      currentHealth = maxHealth;
  }

  public void changePower(int value) {
    System.out.println("increasing power by: " + value);
    currentPower += value;

    if (currentPower <= 0)
      currentPower = 0;
    else if (currentPower >= maxPower)
      currentPower = maxPower;
  }

  public void setAttacking(boolean attacking) {
    this.attacking = attacking;
  }

  public void setLeft(boolean direction) {
    this.left = direction;
  }

  public void setRight(boolean direction) {
    this.right = direction;
  }

  public void setJump(boolean jumping) {
    this.jumping = jumping;
  }

  public void resetBooleans() {
    left = false;
    right = false;
  }

  public void resetAll() {
    resetBooleans();
    inAir = false;
    attacking = false;
    moving = false;
    state = IDLE;
    currentHealth = maxHealth;

    hitbox.x = x;
    hitbox.y = y;
    if (!isEntityOnFloor(hitbox, levelData))
      inAir = true;
  }

  public void kill() {
    currentHealth = 0;
  }

  public int getTileY() {
    return tileY;
  }
}
