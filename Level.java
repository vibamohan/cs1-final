import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Level {
    int speed;
    Obstacle[] possibleObstacles;
    Player player;
    Obstacle goal;
    GameImage background;
    List<Obstacle> onField;
    int minObstacleGap;
    int maxObstacleGap;
    int obstacleMoveSpeed = 0;
    public int score = 0;
    public int LEVEL_COMPLETE_THRESHOLD;
    public int SPAWN_THRESHOLD = 100;
    public boolean scrolling = true;
    public boolean horizontalControl = false;
    public boolean resetting = false;
    public Font lifeFont = new Font("Serif", Font.BOLD, 24);
    public Sound sound = new Sound();
    

    public Level(
            int speed,
            Obstacle[] obstacles,
            GameImage bg,
            GameImage playerImg,
            Player player,
            Obstacle goal,
            int minGap,
            int maxGap,
            int threshold) {
        player.lives = 3;
        this.speed = speed - 2;
        this.possibleObstacles = obstacles;
        this.player = player;
        this.goal = goal;
        this.onField = new ArrayList<>();
        this.minObstacleGap = minGap;
        this.maxObstacleGap = maxGap;
        this.obstacleMoveSpeed = speed;
        this.background = bg;
        LEVEL_COMPLETE_THRESHOLD = threshold;
        player.player = playerImg;
        // System.out.println("obstacle scale: " + onField.get(0).obstacleImg.width);
    }

    public void updateBG() {
        // System.out.println("UPDATE BG W " + background.x);
        if (scrolling) {
            // System.out.println("UpdatingBG RN");
            background.x -= speed;
            if (background.x <= -800) {
                background.x = 0;
            }
            score++;
        }
    }

    public void spawnObstacle() {
        // System.out.println("SPAWNING");
        if (!scrolling)
            return;
        boolean buildNew = onField.size() == 0 || 500 - onField.get(onField.size() - 1).obstacleImg.x > minObstacleGap;

        if (buildNew) {
            Obstacle chosenObstacle = possibleObstacles[(int) (Math.random() * possibleObstacles.length)];
            Obstacle obst = new Obstacle(
                    500 + (int) (Math.random() * 30 + 1),
                    chosenObstacle.dimensions,
                    chosenObstacle.obstacleImg.image);
            onField.add(obst);
        }
    }

    public void cleanAndMoveObstacles() {
        // System.out.println("CLEANING + MOVING");
        if (!scrolling)
            return;
        for (int cur = 0; cur < onField.size(); cur++) {
            Obstacle i = onField.get(cur);
            i.obstacleImg.x -= obstacleMoveSpeed;
            // System.out.println("I + " + i.obstacleImg.x);
            if (i.obstacleImg.x <= -200) {
                onField.remove(cur);
            }
        }

        // collision detection
        for (int i = 0; i < onField.size(); i++) {
            Obstacle cur = onField.get(i);
            if (cur.isColliding(player)) {
                // System.out.println("COLLISION HAPPENED! ");
                // int prevSize = player.collisions.size();
                if (!player.collisions.contains(cur)) {
                    sound.playSound(SoundPath.CRASH_REGULAR.path);
                    System.out.println("life -- ");
                    player.lives--;
                }
                player.collisions.add(cur);
                if (player.lives == 0) {
                    sound.playSound(SoundPath.LEVEL_LOSE.path);
                    this.reset();
                } 
            }
        }

        spawnGoal();
    }

    public void displayScene(Graphics g) {
        background.render(g);
        synchronized (onField) {
            for (int i = 0; i < onField.size(); i++) {
                onField.get(i).render(g);
            }
        }

        String toDraw = "";
        switch (player.lives) {
            case 0: 
                toDraw = "❌❌❌";
            case 1:
                toDraw = "❌❌❤️";
                break;
            case 2:
                toDraw = "❌❤️❤️";
                break;
            case 3: 
                toDraw = "❤️❤️❤️";
                break;
            default: 
                toDraw = "❌❌❌";
        }

        g.setFont(lifeFont);
        g.setColor(Color.red);
        g.drawString(toDraw, 50, 50);

        g.setColor(Color.PINK);
        g.drawString("Score: " + String.valueOf(this.score), 200, 50);

    }

    public void reset() {
        System.out.println("GAME RESET ");
        resetting = true;
        
        onField.clear();
        player.collisions.clear();
        player.player.y = Player.GROUND - 2;

        background.x = 0;
        player.lives = 3;
        this.score = 0;
        
    }

    public void spawnGoal() {
        // System.out.println("spawned!");
        if (levelComplete()) {
            // System.out.println("LEVEL COMPLETE");
            onField.add(goal);
            horizontalControl = true;
            scrolling = false;
        }
    }

    public boolean levelComplete() {
        return this.score >= LEVEL_COMPLETE_THRESHOLD;
    }
}
