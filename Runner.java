import java.io.IOException;

import javax.swing.JFrame;


public class Runner {
 
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Serenade of Hours");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create panel and add it to the frame
        Screen panel = new Screen();
       
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
       
        panel.animate();
       
    }
}
