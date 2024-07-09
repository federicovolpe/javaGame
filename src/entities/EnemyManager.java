package entities;

import static utils.Constants.EnemyConstants.CRABBY_HEIGHT;
import static utils.Constants.EnemyConstants.CRABBY_HEIGHT_D;
import static utils.Constants.EnemyConstants.CRABBY_OFFSET_X;
import static utils.Constants.EnemyConstants.CRABBY_OFFSET_Y;
import static utils.Constants.EnemyConstants.CRABBY_WIDTH;
import static utils.Constants.EnemyConstants.CRABBY_WIDTH_D;

import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import gamestates.Playing;
import utils.LoadSave;

// all the code necessary for enemies to work
public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private List<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        crabbies = LoadSave.getCrabs();
    }

    public void update(int[][] lvlData, Player player) {
        for (Crabby c : crabbies) {
            if (c.isActive())
                c.update(lvlData, player);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()],
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
            if (c.isActive())
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
