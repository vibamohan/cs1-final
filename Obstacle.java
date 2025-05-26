import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;




class Obstacle {
    GameImage obstacleImg;


    public Obstacle(int x, int y, BufferedImage picture) {
        this.obstacleImg = new GameImage(x, y, picture);
    }


    boolean isCollliding(Player p) {
        int[][] locationInfo = p.getLocationInfo();
        return (overlap(obstacleImg.x, obstacleImg.width,  locationInfo[0][0], locationInfo[1][0])
             || overlap(obstacleImg.y, obstacleImg.height, locationInfo[0][1], locationInfo[1][1]));
    }


    static boolean overlap(int dimLocation, int dimSize, int playerDim, int playerDimSize) {
        if (dimLocation < playerDim && (dimLocation + dimSize) >= playerDim) {
            return true;
        } else {
            return false;
        }
    }

    void render(Graphics g) {
        obstacleImg.render(g);
    }

}


