import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;




// import KeyListener classes to interpret key clicks
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;


public class Screen extends JPanel implements KeyListener{

    // instance variables
    private Player player;
    private Level level;

    public Screen(){

        setFocusable(true);
        setLayout(null);

        // initialize variables
        player = new Player(70, Player.GROUND - 2, new BufferedImage(WIDTH, HEIGHT, 1));
        BufferedImage bushObs = new BufferedImage(492, 200, 1);
        bushObs.
        Obstacle[] obs = new Obstacle[] {
            new Obstacle(0, 0, new BufferedImage())
        };
        level = new Level(10, new Obstacle[] {}, player, null, 50, 70);

        // add Key listener
        addKeyListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        //Sets the size of the panel
            return new Dimension(800,600);
    }
   
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);




        // Put calls to draw in here
        player.draw(g);





                /* Example of drawing a string on the JPanel. See the project description
                   for how to change the font */
                g.drawString("This is how you draw a string at position (70, 300)", 70, 300);




    }
   
    // This will be called when someone presses a key
    public void keyPressed(KeyEvent e){
        // You can use this print to see what the keycodes are.
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == 38){   // up arrow
            player.jump();
        }


        repaint();
    }




    // animate objects
    public void animate(){
        while(true){
            //pause for .01 second
            try {
                Thread.sleep(10);    // 10 milliseconds
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
           
            // Add calls to your move methods here
            player.gravityEffect();
            repaint();
        }
    }


    // You must have method signatures for all methods that are
    // part of an interface. Just leave these here.
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

}
