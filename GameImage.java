import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class GameImage {
    public final BufferedImage image;
    AffineTransform transformer = new AffineTransform();
    int x;
    int y;
    int rotation;
    int width;
    int height;
    int prevWidth;
    int prevHeight;


    public GameImage(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.rotation = 0;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.prevHeight = image.getHeight();
        this.prevWidth = image.getWidth();
    }


    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        transformer.translate(this.x, this.y);
        transformer.setToScale(width/prevWidth, width/prevHeight);


        transformer.rotate(rotation, image.getWidth() / 2.0, image.getHeight() / 2.0);
        g2.drawImage(this.image, this.x, this.y, null);
    }


    public int[][] getLocationInfo() {
        return new int[][] {{x, y}, {width, height}};
    }
}
