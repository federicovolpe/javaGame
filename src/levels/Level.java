package levels;

public class Level {
    private int[][] lvlData;

    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public int getSpriteIndex(int i, int j) {
        return lvlData[i][j];
    }

    public int[][] getLvlData() {
        return lvlData;
    }
}
