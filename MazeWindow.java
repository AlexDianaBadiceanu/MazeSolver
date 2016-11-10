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
 * Class displaying the maze and allowing the user to ask for a solution
 * 
 * @author (Alex Badiceanu) 
 * @version (09/28/2016)
 */
public class MazeWindow{
    //declare global variables for the start end end coordinates
    public static int startRow=-1;
    public static int startColumn=-1;
    public static int endRow=-1;
    public static int endColumn=-1;

    public static JFrame frame;
    //global final variables holding the size of the maze
    public static final int ROW = 40;
    public static final int COL = 50;

    //The maze built by the PuzzleSolver class
    public static String [][] mS = null;

    /**
     * Helper method, rebooting the page upon not finding a path
     */
    public static int reboot(){
        frame.setVisible(false);
        MazeWindow.makeFrame();
        
        //reinitialize origin and target
        startRow=-1;
        startColumn=-1;
        endRow=-1;
        endColumn=-1;
        return 1;
    }

    public static void makeFrame(){
        //Create a new Window Frame
        frame = new JFrame("Maze Solver");   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500,1000);

        //center it to the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        //set a Border for it
        frame.setLayout(new BorderLayout());

        //Set the background image using a label
        JLabel background=new JLabel(new ImageIcon("Maze.png"));
        frame.add(background);
        FlowLayout labelLayout = new FlowLayout();
        labelLayout.setVgap(60);
        labelLayout.setHgap(800);
        background.setLayout(labelLayout);

        //get the randomly generated maze
        mS = PuzzleSolver.getRandomMaze(ROW, COL);

        //Construct the labels based on walls specificities
        JLabel [][] maze = new JLabel [ROW][COL];
        for( int i=0; i<ROW; i++){
            for( int j=0; j<COL; j++){
                maze[i][j]=new JLabel("   ");
                maze[i][j].setPreferredSize(new Dimension(10,10));
                int a = mS[i][j].charAt(0)-48;
                int b = mS[i][j].charAt(2)-48;
                int c = mS[i][j].charAt(1)-48;
                int d = mS[i][j].charAt(3)-48;
                maze[i][j].setBorder(BorderFactory.createMatteBorder(a,b,c,d, Color.BLACK));
                maze[i][j].setText(""+j+" "+i);
                maze[i][j].setForeground(new Color (230,230,230));
                maze[i][j].setBackground(new Color (230,230,230));

                //Set a mouse listener to every cell in the maze so that the user can select its locations
                //override the methods in the interface
                maze[i][j].addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            Object event = e.getSource();
                            JLabel clicked = (JLabel)event;
                            String text = clicked.getText();
                            //if no label was selected before, the first one is the start point
                            if(startColumn==-1&&startRow==-1){   
                                startColumn = Integer.parseInt(text.substring(0,text.indexOf(" ")));
                                startRow = Integer.parseInt(text.substring(text.indexOf(" ")+1));

                                maze[startRow][startColumn].setOpaque(true);
                                maze[startRow][startColumn].setBackground(new Color(8,104,3));
                                maze[startRow][startColumn].setForeground(new Color(8,104,3));
                            }//if only one label was selected, the second one is the end point
                            else if(endColumn==-1&&endRow==-1){
                                endColumn = Integer.parseInt(text.substring(0,text.indexOf(" ")));
                                endRow = Integer.parseInt(text.substring(text.indexOf(" ")+1));

                                maze[endRow][endColumn].setOpaque(true);
                                maze[endRow][endColumn].setBackground(new Color(178,3,35));
                                maze[endRow][endColumn].setForeground(new Color(178,3,35));
                            }
                        }
                    });

            }
        }

        //Create a panel that holds both the maze and the Solve button
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(ROW, COL));
        panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        for(int i=0; i<ROW; i++){
            for(int j=0; j<COL; j++){
                panel.add( maze[i][j], BorderLayout.CENTER);
            }
        }

        //get the images for the solve and retry buttons
        ImageIcon solvePicture = new ImageIcon("solve.png");
        ImageIcon changedSPicture = new ImageIcon("solve2.png");

        JLabel solve = new JLabel("Solve!",solvePicture, JLabel.CENTER);
        solve.setSize(200,200);
        solve.setFont(new Font("Calibri", Font.BOLD, 75));
        solve.setVerticalTextPosition(JLabel.CENTER);
        solve.setHorizontalTextPosition(JLabel.CENTER);

        JLabel retry = new JLabel("Retry!",solvePicture, JLabel.CENTER);
        retry.setSize(200,200);
        retry.setFont(new Font("Calibri", Font.BOLD, 75));
        retry.setVerticalTextPosition(JLabel.CENTER);
        retry.setHorizontalTextPosition(JLabel.CENTER);


        //add a mouse listener to the solve button and override the methods in the interface
        solve.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    solve.setIcon(changedSPicture);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    solve.setIcon(solvePicture);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    //get the solution
                    int [][] sol = PuzzleSolver.solve(mS, startRow, endRow, startColumn, endColumn);

                    //if solution found, make it visible on the grid
                    if(sol!=null){
                        for(int i=0; i<sol.length; i++){
                            for(int j=0; j<sol[0].length; j++){
                                if(sol[i][j]==1){
                                    maze[i][j].setOpaque(true);
                                    maze[i][j].setBackground(new Color(1,121,196));
                                    maze[i][j].setForeground(new Color(1,121,196));

                                }
                            }
                        }
                        maze[startRow][startColumn].setOpaque(true);
                        maze[startRow][startColumn].setBackground(new Color(1,121,196));
                        maze[startRow][startColumn].setForeground(new Color(1,121,196));
                    }//if no solution found, display informative message
                    else
                    {
                        JFrame f = new JFrame("Maze Solver"); 
                        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
                        frame.setDefaultCloseOperation(reboot());

                        f.setLayout(new BorderLayout());
                        f.setSize(500,500);
                        JLabel b=new JLabel("      There is no path");
                        b.setFont(new Font("Calibri", Font.BOLD, 50));
                        f.add(b, BorderLayout.CENTER);

                        f.setVisible(true);

                       
                    }

                }
            });

            
        //add a mouse listener to the retry button and override the methods in the interface
        retry.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    retry.setIcon(changedSPicture);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    retry.setIcon(solvePicture);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    reboot();
                }
            });

            
        //add all elements to the frame
        JPanel P = new JPanel();
        JLabel empty = new JLabel();
        empty.setPreferredSize(new Dimension(50,50));
        P.add(solve);
        P.add(empty);
        P.add(retry);
        background.add(P);
        background.add(panel);

        frame.setVisible(true);

    }
}
