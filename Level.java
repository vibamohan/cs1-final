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
    public static int sf = 4;

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
        onField.add(new Obstacle(140, 250, 50*sf, 20*sf, possibleObstacles[0].obstacleImg.image));
        // System.out.println("obstacle scale: " + onField.get(0).obstacleImg.width);
    }  


    public void updateBG() {
        background.x--;
        if (background.x <= 0) {
            background.x = 500;
        }
        score++;
    }

    public void spawnObstacle() {
        System.out.println("SPAWNING");
        int newX;
        boolean buildNew = false;
        if (onField.size() == 0) {
            newX = 140;
            buildNew = true;
        } else {
            newX = onField.get(onField.size() - 1).obstacleImg.x + (int)(Math.random() * 10 + 1); 
            if (newX - onField.get(onField.size() - 1).obstacleImg.x > minObstacleGap) {
                buildNew = true;
            }
        }

        if (buildNew) {
            Obstacle chosenObstacle = possibleObstacles[(int) (Math.random() * possibleObstacles.length)];
            Obstacle obst = new Obstacle(newX, 300, chosenObstacle.obstacleImg.width, chosenObstacle.obstacleImg.height, chosenObstacle.obstacleImg.image);
            onField.add(obst);
        }
    }

    public void cleanAndMoveObstacles() {
        System.out.println("CLEANING + MOVING");
        for (int cur = 0; cur < onField.size(); cur++) {
            Obstacle i = onField.get(cur);
            i.obstacleImg.x -= 1;
            System.out.println("I + " + i.obstacleImg.x);
            if (i.obstacleImg.x <= 0) {
                onField.remove(cur);
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

}
