import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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

    public Level(int speed, Obstacle[] obstacles, GameImage bg, Player player, Obstacle goal, int minGap, int maxGap) {
        this.speed = speed - 10;
        this.possibleObstacles = obstacles;
        this.player = player;
        this.goal = goal;
        this.onField = new ArrayList<>();
        this.minObstacleGap = minGap;
        this.maxObstacleGap = maxGap;
        this.obstacleMoveSpeed = speed;
        this.background = bg;
        onField.add(new Obstacle(140, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(new Obstacle(140 + 200, 250,ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(new Obstacle(140 + 200 + 200, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        // System.out.println("obstacle scale: " + onField.get(0).obstacleImg.width);
    }  


    public void updateBG() {
        System.out.println("UPDATE BG W " + background.x);
        background.x-=1;
        if (background.x <= -800) {
            background.x = 0;
        }
        score++;
    }

    public void spawnObstacle() {
        System.out.println("SPAWNING");
        boolean buildNew = false;
        if (onField.size() == 0) {
            buildNew = true;
        } else {
            if (500 - onField.get(onField.size() - 1).obstacleImg.x > minObstacleGap) {
                buildNew = true;
            }
        }

        if (buildNew) {
            Obstacle chosenObstacle = possibleObstacles[(int) (Math.random() * possibleObstacles.length)];
            Obstacle obst = new Obstacle(500+ (int) (Math.random() * 30 + 1), OBSTACLE_HEIGHT, ObstacleProperties.L1_FLOWER,chosenObstacle.obstacleImg.image);
            onField.add(obst);
        }
    }

    public void cleanAndMoveObstacles() {
        System.out.println("CLEANING + MOVING");
        for (int cur = 0; cur < onField.size(); cur++) {
            Obstacle i = onField.get(cur);
            i.obstacleImg.x -= 2;
            System.out.println("I + " + i.obstacleImg.x);
            if (i.obstacleImg.x <= 0) {
                onField.remove(cur);
            }
        }

        // collision detection
        for (Obstacle i : onField) {
            if (i.isColliding(player)) {
                System.out.println("COLLISION HAPPENED! ");
                int prevSize = player.collisions.size();
                player.collisions.add(i);
                if (prevSize != player.collisions.size()) {
                    player.lives--;
                    this.reset();
                }
                if (player.lives == 0) {
                    System.out.println("EXIT WITH STATUS LIVES = 0");
                }
            }
        }
    }

    public void displayScene(Graphics g) {
        System.out.println("DISPLAYING");
        background.render(g);
        for (Obstacle i : onField) {
            System.out.println("I: " + i);
            i.render(g);
        }
    }

    public void reset() {
        onField = new ArrayList<>();
        onField.add(new Obstacle(140, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(new Obstacle(140 + 400, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        onField.add(new Obstacle(140 + 400 + 400, 250, ObstacleProperties.L1_FLOWER, possibleObstacles[0].obstacleImg.image));
        player.collisions.clear();
        background.x = 0;
    }

}
