import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
import java.applet.*;
import java.net.*;

public class Screen extends JPanel implements KeyListener {

    // instance variables
    private Player player;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level curLevel;

    public Screen() throws IOException {

        setFocusable(true);
        setLayout(null);

        // initialize variables
        player = new Player(70, Player.GROUND - 2, new BufferedImage(WIDTH, HEIGHT, 1));
        Obstacle[] obs1 = new Obstacle[] {
                new Obstacle(0, ObstacleProperties.L1_FLOWER,
                        ImageIO.read(new File("assets/images/bushl1.png"))),
                new Obstacle(0, ObstacleProperties.L1_FLOWER,
                        ImageIO.read(new File("assets/images/bushl1.png"))),
                new Obstacle(0, ObstacleProperties.L1_FLOWER,
                        ImageIO.read(new File("assets/images/bushl1.png")))
        };

        Obstacle[] obs2 = new Obstacle[] {
                new Obstacle(0, ObstacleProperties.CACTUS,
                        ImageIO.read(new File("assets/images/cactil2.png"))),
                new Obstacle(0, ObstacleProperties.CACTUS,
                        ImageIO.read(new File("assets/images/cactil2.png"))),
                new Obstacle(0, ObstacleProperties.CACTUS,
                        ImageIO.read(new File("assets/images/cactil2.png")))
        };

        Obstacle[] obs3 = new Obstacle[] {
                new Obstacle(0, ObstacleProperties.SNOWMAN,
                        ImageIO.read(new File("assets/images/snowmen.png"))),
                new Obstacle(0, ObstacleProperties.SNOWMAN,
                        ImageIO.read(new File("assets/images/snowmen.png"))),
                new Obstacle(0, ObstacleProperties.SNOWMAN,
                        ImageIO.read(new File("assets/images/snowmen.png")))
        };

        Obstacle goal = new Obstacle(300, ObstacleProperties.L1_FLOWER,
                ImageIO.read(new File("assets/images/goco.png")));

        level1 = new Level(3, obs1, new GameImage(0, 0, 1600, 350, ImageIO.read(new File("assets/images/bgl1.png"))),
                new GameImage(player.player.x, 400, 100, 100, ImageIO.read(new File("assets/images/playerbunny.png"))),
                player, goal, 500, 240, 1000);

        level2 = new Level(3, obs2, new GameImage(0, 0, 1600, 350, ImageIO.read(new File("assets/images/bgl22.png"))),
                new GameImage(player.player.x, player.player.y, 100, 100,
                        ImageIO.read(new File("assets/images/playerbunny.png"))),
                player, goal, 500, 240, 1000);

        level3 = new Level(3, obs3, new GameImage(0, 0, 1600, 350, ImageIO.read(new File("assets/images/bgl33.png"))),
                new GameImage(player.player.x, player.player.y, 100, 100,
                        ImageIO.read(new File("assets/images/playerbunny.png"))),
                player, goal, 500, 240, 1000);

        curLevel = level3;

        JOptionPane.showMessageDialog(this,
                "This is an endless runner \n Use the up and down arrow keys to move around the game, and left and right when the goal is reached \n Avoid the obstacles. You have 3 lives a level and they reset when a level is reset or progressed \n Help these animals get to their destinations and have fun!",
                "README Instructions", JOptionPane.INFORMATION_MESSAGE);

        // add Key listener
        addKeyListener(this);

    }

    @Override
    public Dimension getPreferredSize() {
        // Sets the size of the panel
        return new Dimension(800, 350);
    }

    @Override
    public void paintComponent(Graphics g) {
        // Put calls to draw in here
        super.paintComponent(g);
        curLevel.displayScene(g);
        player.draw(g);

    }

    // This will be called when someone presses a key
    public void keyPressed(KeyEvent e) {

        System.out.println(e.getKeyCode());

        Level levelBefore = curLevel;
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

        if (curLevel != levelBefore) {
            JOptionPane.showMessageDialog(this, "You have unlocked the next level!",
                    "Progression Notification", JOptionPane.INFORMATION_MESSAGE);
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

                JOptionPane.showMessageDialog(this,
                        "No more hearts remaining! You have collided with obstacles 3 times..",
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
