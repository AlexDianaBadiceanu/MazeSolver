import javax.swing.*;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
/**
 * Class displaying the welcome message
 * 
 * @author (Alex Badiceanu) 
 * @version (09/28/2016)
 */
public class WelcomeWindow{
    public static void makeFrame(){
        //Create a new Window Frame
        JFrame frame = new JFrame("Maze Solver"); 

        //make the program exit when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set the size of the window
        frame.setSize(1500,1000);

        //set a layout to the frame
        frame.setLayout(new BorderLayout());

        //center the window to the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
       
        //create a label that will act as backgorund and assign it a layout as well
        JLabel empty = new JLabel();
        empty.setPreferredSize(new Dimension(260,260));
        JLabel background = null;
        try{
           background=new JLabel(new ImageIcon("Welcome.png"));
        } catch (Exception e) {
            System.out.println ("The background image was not found in the folder");
          
        }
        frame.add(background);
        FlowLayout labelLayout = new FlowLayout(background.CENTER);
        labelLayout.setVgap(330);
        labelLayout.setHgap(50);
        background.setLayout(labelLayout);

        //create the Start button
        ImageIcon startPicture = new ImageIcon("start.png");
        ImageIcon changedPicture = new ImageIcon("start2.png");
       
        //position the text centered in the button; set the font
        JLabel start = new JLabel("Start!",startPicture, JLabel.LEFT);
        start.setSize(200,200);
        start.setVerticalTextPosition(JLabel.CENTER);
        start.setHorizontalTextPosition(JLabel.CENTER);
        start.setFont(new Font("Calibri", Font.BOLD, 75));
        start.setForeground(Color.white);

        //create the welcome label and set its text
        JLabel textLabel = new JLabel("<html>Solve the <br> maze puzzle</html>");
        textLabel.setSize(200,200);
        textLabel.setVerticalTextPosition(JLabel.CENTER);
        textLabel.setHorizontalTextPosition(JLabel.CENTER);
        textLabel.setFont(new Font("Calibri", Font.BOLD, 83));
        textLabel.setForeground(new Color (6, 66, 163));

        //assign a mouse listener to the start button and override the methods in the interface
        start.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    start.setIcon(changedPicture);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    start.setIcon(startPicture);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    frame.setVisible(false);
                    MazeWindow.makeFrame();
                }
            });

        //add the text and start button to the frame
        background.add(textLabel);
        background.add(empty);
        background.add(start);

        //set the frame visible
        frame.setVisible(true);

    }
}
