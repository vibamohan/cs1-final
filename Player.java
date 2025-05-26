import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
    // Instance Variables
    GameImage player;
    int yVel = 0;

    // Constants
    public static final int GROUND = 300;
    public static final int PLAYER_HEIGHT = 50;
    public static final int GRAVITY = 3;
    public static final int JUMP_STRENGTH = -20;
    public static final int TERMINAL_VELOCITY = 10;

    public Player(int x, int y, BufferedImage look) {
        player = new GameImage(x, y, 30, PLAYER_HEIGHT, look);
    }

    // draw the player
    public void draw(Graphics g) {
        gravityEffect();

        // Prevent going below ground
        if (player.y + PLAYER_HEIGHT > GROUND) {
            player.y = GROUND - PLAYER_HEIGHT;
            yVel = 0;
        }

        player.y += yVel;

        // System.out.println("Y: " + player.y + " , yVel = " + yVel);

        g.setColor(new Color(255, 0, 0));
        g.fillRect(player.x, player.y, 50, PLAYER_HEIGHT);
        player.render(g);
    }

    // Move the player up
    public void jump() {
        if (player.y + PLAYER_HEIGHT >= GROUND) {
            yVel = JUMP_STRENGTH;
        }
    }

    public void gravityEffect() {
        if (player.y + PLAYER_HEIGHT < GROUND) {
            yVel += GRAVITY;
            if (yVel > TERMINAL_VELOCITY) {
                yVel = TERMINAL_VELOCITY;
            }
        }
    }

    public int[][] getLocationInfo() {
        return player.getLocationInfo();
    }
}
