import java.awt.Graphics;
import java.awt.Image;

// TODO: Fill with real properties 
enum ObstacleProperties {
    L1_FLOWER(120, 60),
    L1_PINEAPPLE(1, 2);

    public int width;
    public int height;

    ObstacleProperties(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

class Obstacle {
    GameImage obstacleImg;
    ObstacleProperties dimensions;

    public Obstacle(int x, int y, ObstacleProperties props, Image picture) {
        this.obstacleImg = new GameImage(x, y, props.width, props.height, picture);
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
