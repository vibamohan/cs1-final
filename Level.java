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

    public Level(int speed, Obstacle[] obstacles, Player player, Obstacle goal, int minGap, int maxGap) {
        this.speed = speed - 10;
        this.possibleObstacles = obstacles;
        this.player = player;
        this.goal = goal;
        this.onField = new ArrayList<>();
        this.minObstacleGap = minGap;
        this.maxObstacleGap = maxGap;
        this.obstacleMoveSpeed = speed;
    }  


    public void updateBG() {
        background.x += 10;
        if (background.x == 0) {
            background.x = -100;
        }
    }

    public void spawnObstacle() {
        int newX = onField.get(onField.size() - 1).obstacleImg.x + (int)(Math.random() * 10 + 1); 
        if (newX > minObstacleGap) {
            Obstacle chosenObstacle = possibleObstacles[(int) (Math.random() * possibleObstacles.length)];
            Obstacle obst = new Obstacle(newX, player.GROUND - chosenObstacle.obstacleImg.height, chosenObstacle.obstacleImg.image);
            onField.add(obst);
        }
    }

    public void cleanAndMoveObstacles() {
        for (int cur = 0; cur < onField.size(); cur++) {
            Obstacle i = onField.get(cur);
            i.obstacleImg.x -= obstacleMoveSpeed;
            if (i.obstacleImg.x <= 0) {
                onField.remove(cur);
            }
        }
    }

    public void displayScene(Graphics g) {
        background.render(g);
        for (Obstacle i : onField) {
            i.render(g);
        }
    }

}
