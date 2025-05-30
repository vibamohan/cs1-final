import javax.imageio.ImageIO;
// import javax.management.timer.Timer;
// import javax.management.timer.TimerNotification;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Screen extends JPanel implements KeyListener {

    // instance variables
    private Player player;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level curLevel;
    private Timer timer = new Timer(3000, null);

    public Screen() throws IOException {

        setFocusable(true);
        setLayout(null);

        // initialize variables
        player = new Player(70, Player.GROUND - 2, new BufferedImage(WIDTH, HEIGHT, 1));
        Obstacle[] obs1 = new Obstacle[] {
                new Obstacle(0, 0, ObstacleProperties.L1_FLOWER,
                        ImageIO.read(new File("assets/images/bushl1.png"))),
                new Obstacle(0, 0, ObstacleProperties.L1_FLOWER,
                        ImageIO.read(new File("assets/images/bushl1.png"))),
                new Obstacle(0, 0, ObstacleProperties.L1_FLOWER,
                        ImageIO.read(new File("assets/images/bushl1.png")))
        };
        level1 = new Level(3, obs1, new GameImage(0, 0, 1600, 350, ImageIO.read(new File("assets/images/bgl1.png"))),
                player, new Obstacle(300, 300, ObstacleProperties.L1_FLOWER,
                        ImageIO.read(new File("assets/images/goco.png"))),
                500, 240, 1000);
        level2 = new Level(3, obs1, new GameImage(0, 0, 1600, 350, ImageIO.read(new File("assets/images/bgl1.png"))),
                player, null, 300, 240, 3000);
        level3 = new Level(3, obs1, new GameImage(0, 0, 1600, 350, ImageIO.read(new File("assets/images/bgl1.png"))),
                player, null, 300, 240, 3000);
        curLevel = level1;

        // add Key listener
        addKeyListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        // Sets the size of the panel
        return new Dimension(800, 350);
    }

    @Override
    public void paint(Graphics g) {
        // Put calls to draw in here
        super.paintComponent(g);
        curLevel.displayScene(g);
        player.draw(g);

    }

    // This will be called when someone presses a key
    public void keyPressed(KeyEvent e) {

        System.out.println(e.getKeyCode());

        // When return is pressed, update levels !
        if (e.getKeyCode() == 10) /* return */ {
            curLevel = level1;
            /* progress the levels */
            if (level1.levelComplete()) {
                curLevel = level2;
                if (level2.levelComplete()) {
                    curLevel = level3;
                    if (level3.levelComplete()) {
                        curLevel = null;
                    }
                }
            }
        }

        if (e.getKeyCode() == 38) { // up arrow
            player.jump();
        }

        if (e.getKeyCode() == 37 && curLevel.levelComplete()) {
            player.moveLeft();
        }

        if (e.getKeyCode() == 39 && curLevel.levelComplete()) {
            player.moveRight();
        }

        repaint();
    }

    // animate objects
    public void animate() {
        while (true) {
            try {
                Thread.sleep(20); // 10 milliseconds
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            if (curLevel.resetting) {
                System.out.println("This reset logic has ran");

                JOptionPane.showMessageDialog(this, "Level resetting due to collision with obstacle",
                        "Reset Notification", JOptionPane.INFORMATION_MESSAGE);
                curLevel.resetting = false;
            }

            player.gravityEffect();
            curLevel.updateBG();
            curLevel.cleanAndMoveObstacles();
            curLevel.spawnObstacle();
            curLevel.spawnGoal();
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}
