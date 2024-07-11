package entities;

import gamestates.Playing;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import levels.Level;
import utils.LoadSave;

import static utils.Constants.EnemyConstants.*;

// all the code necessary for enemies to work
public class EnemyManager {

    private final Playing playing;
    private BufferedImage[][] crabbyArr;
    private List<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabs();
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (Crabby c : crabbies) {
            if (c.isActive()){
                c.update(lvlData, player);
                isAnyActive = true;
            }
        }
        if(!isAnyActive)
            playing.setLevelCompeted();
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                g.drawImage(crabbyArr[c.getState()][c.getAniIndex()],
                        (int) c.getHitbox().x - CRABBY_OFFSET_X - xLvlOffset + c.flipX(),
                        (int) c.getHitbox().y - CRABBY_OFFSET_Y,
                        CRABBY_WIDTH * c.flipW(),
                        CRABBY_HEIGHT,
                        null);

                c.drawHitbox(g, xLvlOffset);
                c.draw(g, xLvlOffset);
            }
        }
    }

    private void loadEnemyImages() {
        crabbyArr = new BufferedImage[5][9];
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for (int i = 0; i < crabbyArr.length; i++)
            for (int j = 0; j < crabbyArr[i].length; j++)
                crabbyArr[i][j] = tmp.getSubimage(j * CRABBY_WIDTH_D,
                        i * CRABBY_HEIGHT_D,
                        CRABBY_WIDTH_D,
                        CRABBY_HEIGHT_D);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies) {
            if (c.isActive() && !(c.getState() == DEAD))
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    return;
                }
        }
    }

    public void resetAllEnemies() {
        for(Crabby c : crabbies)
            c.resetEnemy();
    }
}
