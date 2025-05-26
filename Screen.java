import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;




// import KeyListener classes to interpret key clicks
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyEvent;


public class Screen extends JPanel implements KeyListener{

    // instance variables
    private Player player;
    private Level level;

    public Screen() throws IOException{

        setFocusable(true);
        setLayout(null);

        // initialize variables
        player = new Player(70, Player.GROUND - 2, new BufferedImage(WIDTH, HEIGHT, 1));
        Obstacle[] obs = new Obstacle[] {
            new Obstacle(0, 0, 50*Level.sf, 20*Level.sf,
                ImageIO.read(new File("assets/images/bushl1.png"))),
            new Obstacle(0, 0, 50*Level.sf, 20*Level.sf,
                ImageIO.read(new File("assets/images/bushl1.png"))),
            new Obstacle(0, 0, 50*Level.sf, 20*Level.sf,
                ImageIO.read(new File("assets/images/bushl1.png")))
        };
        level = new Level(40, obs, new GameImage(0, 0, 1600, 350, ImageIO.read(new File("assets/images/bgl1.png"))), player, null, 300, 240);

        // add Key listener
        addKeyListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(800,350);
    }
   
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        // Put calls to draw in here
        level.displayScene(g);
        player.draw(g);

    }
   
    // This will be called when someone presses a key
    public void keyPressed(KeyEvent e){
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == 38){   // up arrow
            player.jump();
        }

        repaint();
    }




    // animate objects
    public void animate(){
        while(true){
            try {
                Thread.sleep(20);    // 10 milliseconds
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
           
            player.gravityEffect();
            level.updateBG();
            level.cleanAndMoveObstacles();
            level.spawnObstacle();
            repaint();
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

}
