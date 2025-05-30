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
    public static final int OBSTACLE_HEIGHT = 250;
    public int LEVEL_COMPLETE_THRESHOLD;
    public int SPAWN_THRESHOLD = 100;
    public boolean scrolling = true;
    public boolean horizontalControl = false;
    public boolean resetting = false;

    public Level(
            int speed,
            Obstacle[] obstacles,
            GameImage bg,
            Player player,
            Obstacle goal,
            int minGap,
            int maxGap,
            int threshold) {
        this.speed = speed - 2;
        this.possibleObstacles = obstacles;
        this.player = player;
        this.goal = goal;
        this.onField = new ArrayList<>();
        this.minObstacleGap = minGap;
        this.maxObstacleGap = maxGap;
        this.obstacleMoveSpeed = speed;
        this.background = bg;
        onField.add(
                new Obstacle(
                        140, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(
                new Obstacle(
                        140 + 200, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(
                new Obstacle(
                        140 + 200 + 200,
                        250,
                        ObstacleProperties.L1_FLOWER,
                        possibleObstacles[0].obstacleImg.image));
        LEVEL_COMPLETE_THRESHOLD = threshold;
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
                    OBSTACLE_HEIGHT,
                    ObstacleProperties.L1_FLOWER,
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
                    player.lives--;
                    this.reset();
                }
                player.collisions.add(cur);
                if (player.lives == 0) {
                    // System.out.println("EXIT WITH STATUS LIVES = 0");
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
    }

    public void reset() {
        resetting = true;
        
        onField.clear();
        onField.add(
                new Obstacle(
                        140, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(
                new Obstacle(
                        140 + 400, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(
                new Obstacle(
                        140 + 400 + 400,
                        250,
                        ObstacleProperties.L1_FLOWER,
                        possibleObstacles[0].obstacleImg.image));
        player.collisions.clear();
        background.x = 0;
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
