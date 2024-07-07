package entities;

import static utils.Constants.EnemyConstants.CRABBY_HEIGHT;
import static utils.Constants.EnemyConstants.CRABBY_HEIGHT_D;
import static utils.Constants.EnemyConstants.CRABBY_WIDTH;
import static utils.Constants.EnemyConstants.CRABBY_WIDTH_D;

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
        System.out.println("how many crabs ? " + crabbies.size());
    }

    public void update() {
        for (Crabby c : crabbies) {
            c.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies) {
            g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()],
                    (int) c.getHitbox().getX() - xLvlOffset,
                    (int) c.getHitbox().getY(),
                    CRABBY_WIDTH,
                    CRABBY_HEIGHT,
                    null);
        }
    }

    private void loadEnemyImages() {
        crabbyArr = new BufferedImage[5][9];
        BufferedImage tmp = LoadSave.getSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for (int i = 0; i < crabbyArr.length; i++) {
            for (int j = 0; j < crabbyArr[i].length; j++) {
                crabbyArr[i][j] = tmp.getSubimage(j * CRABBY_WIDTH_D,
                        i * CRABBY_HEIGHT_D,
                        CRABBY_WIDTH_D,
                        CRABBY_HEIGHT_D);
            }
        }
    }
}
