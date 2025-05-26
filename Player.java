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
    public static final int GRAVITY = 1;
    public static final int JUMP_STRENGTH = -20;
    public static final int TERMINAL_VELOCITY = 10;

    public Player(int x, int y, BufferedImage look) {
        player = new GameImage(x, y, look);
    }

    // draw the player
    public void draw(Graphics g) {
        gravityEffect();

        player.y += yVel;

        // Prevent going below ground
        if (player.y + PLAYER_HEIGHT > GROUND) {
            player.y = GROUND - PLAYER_HEIGHT;
            yVel = 0;
        }

        System.out.println("Y: " + player.y + " , yVel = " + yVel);

        g.setColor(new Color(255, 0, 0));
        g.fillRect(player.x, player.y, 50, PLAYER_HEIGHT);
        player.render(g);
    }

    // Move the player up
    public void jump() {
        System.out.println("JUMP");
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
