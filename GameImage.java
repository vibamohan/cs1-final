import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

import javax.imageio.ImageTypeSpecifier;


public class GameImage {
    public Image image;
    int x;
    int y;
    int width;
    int height;

    public GameImage(int x, int y, int w, int h, Image image) {
        this.x = x;
        this.y = y;
        this.image = image.getScaledInstance(w, h, 1);
        this.width = w;
        this.height = h;
    }


    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.image, this.x, this.y, null);
    }

    public int[][] getLocationInfo() {
        return new int[][] {{x, y}, {width, height}};
    }
}
