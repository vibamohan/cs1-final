import java.awt.Graphics;
import java.awt.Image;

// TODO: Fill with real properties 
enum ObstacleProperties {
    L1_FLOWER(120, 60, 250),
    CACTUS(100, 100, 250 ),
    SNOWMAN(55, 110, 210);

    public int width;
    public int height;
    public int y;

    ObstacleProperties(int width, int height, int y) {
        this.width = width;
        this.height = height;
        this.y = y;
    }
}

class Obstacle {
    GameImage obstacleImg;
    ObstacleProperties dimensions;

    public Obstacle(int x, ObstacleProperties props, Image picture) {
        this.obstacleImg = new GameImage(x, props.y, props.width, props.height, picture);
        this.dimensions = props;
    }

    boolean isColliding(Player p) {
        int[][] locationInfo = p.getLocationInfo();
        return (overlap(obstacleImg.x, obstacleImg.width, locationInfo[0][0], locationInfo[1][0])
                && overlap(obstacleImg.y, obstacleImg.height, locationInfo[0][1], locationInfo[1][1]));
    }

    static boolean overlap(int dimLocation, int dimSize, int playerDim, int playerDimSize) {
        return dimLocation < playerDim + playerDimSize && dimLocation + dimSize > playerDim;
    }

    void render(Graphics g) {
        obstacleImg.render(g);
    }
}
