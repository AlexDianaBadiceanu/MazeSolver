import java.util.*;

/**
 * Class responsible for building a random maze and finding the solution to such maze puzzle
 * 
 * This class implements two different solving algorithms.
 * The default one is a breadth first search similar to the methods usually applied to graphs, but addapted to our model.
 * It returns the shortest path if existent.
 * The second one is a recursive backtracking solution.
 *
 * 
 * @author (Alex Badiceanu) 
 * @version (9/27/2016)
 */
public class PuzzleSolver{
    //declare global variables that keep track of the target  
    public static int targetRow = -1;
    public static int targetCol = -1;

    //the matrix holding the maze
    public static String [][] maze = null;

    //the matrix holding the solution
    public static int [][] solution = null;

    /**
     * method called when the Solve! button is clicked by the user
     *@ params: array holds the maze 
     *         startRow & startCol are the coordinates chosen by the user as current location  
     *         endRow & endCol are the coordinates chosen by the user as target location
     *  
     *@ returns: true if puzzle has solution; false otherwise         
     */
    public static int[][] solve(String [][] array, int startRow, int endRow, int startCol, int endCol){
        //initialize all the elements of the solution matrix to 0;
        solution = new int [array.length][array[0].length];

        for(int i=0; i<solution.length; i++){
            for(int j=0; j<solution[0].length; j++){
                solution[i][j]=0;
            }
        }

        //assign the variables passed through the method to the global ones  
        targetRow = endRow;
        targetCol = endCol;
        maze = array;

        //call the iterative, breadth first algorithm
        boolean answer =solveBFS (startRow, startCol);

        //call the recursive, backtracking method 
        //boolean answer =solve (startRow, startCol);
        
        //if a solution was found, return true;
        //false otherwise
        if(answer)
        {
            return solution;
        }

        return null;
    }
    
    /**
     * method called to iteratively solve the problem using BFS
     * DEFAULT
     *@ params: row, col - initial position
     *  
     *@ returns: true if puzzle has solution; false otherwise         
     */
    public static boolean solveBFS(int givenRow, int givenCol)
    {
        //set the value of the original point to -1 so it will be recognizable
        solution[givenRow][givenCol] = -1;
        
        //create a queue to temporarily hold coordinates of neighbors
        LinkedList<String> myQ = new LinkedList<String>();
        
        //add the first element visited
        myQ.add(""+givenRow+" "+givenCol);
        
        //boolean holding the result of the trial
        boolean result = false;
        
        int row=0, col=0;
        
        //for each available neighbor, add it to the queue, recording its parent for later obtaining the path
        while(myQ.isEmpty()==false){
            String current = (String)myQ.removeLast();
             row = Integer.parseInt(current.substring(0, current.indexOf(" ")));
             col = Integer.parseInt(current.substring(current.indexOf(" ")+1));
             
            // if target was found - stop and recreate path
            if (row==targetRow&&col==targetCol){
                result = true;
                break;
            }
            else
            {

                //check if going up is part of the solution
                if(maze[row][col].charAt(0)=='0'&&solution[row-1][col]==0){
                    myQ.addFirst(""+(row-1)+" "+col);
                    solution[row-1][col]=2;

                }

                //check if going left is part of the solution
                if(maze[row][col].charAt(2)=='0'&&solution[row][col-1]==0){
                    myQ.addFirst(""+row+" "+(col-1));
                    solution[row][col-1]=4;
                }

                //check if going down is part of the solution
                if(maze[row][col].charAt(1)=='0'&&solution[row+1][col]==0){
                    myQ.addFirst(""+(row+1)+" "+col);
                    solution[row+1][col]=5;

                }

                //check if going right is part of the solution
                if(maze[row][col].charAt(3)=='0'&&solution[row][col+1]==0){
                    myQ.addFirst(""+row+" "+(col+1));
                    solution[row][col+1]=3;
                }

            }
            //solution[row][col]=-1;
        }
        
   
        
        //recreate the path backwards from target to origin using the information stored above
        if(result==true){
            
            while (solution[row][col]!=-1&&row>=0&&col>=0){
                if(solution[row][col]==2){
                    solution[row][col]=1;
                    row++; 
                }else if(solution[row][col]==3){
                    solution[row][col]=1;
                    col--;
                }else 
                if(solution[row][col]==4){
                    solution[row][col]=1;
                    col++;
                }else if(solution[row][col]==5){
                    solution[row][col]=1;
                    row--;
                }
                
                
                
            }
            return true;
        }
        return false;
     
    }
    
    /**
     * Method called to generate a random maze. Each element in the resulting matrix has
     * a string "xxxx", where x is either 1 or 0 if there is a wall. The order is Above,
     * Below, LEft and Right
     *@ params: numRow, numCol - the size of the maze
     *  
     *@ returns: the aboved described matrix         
     */
    public static String [][] getRandomMaze(int numRow, int numCol){
        //create the maze according to the specified size
        String [][] randomMaze = new String [numRow][numCol];
        Random r = new Random(31);

        //fill in the first row
        randomMaze[0][0]="1"+Math.abs(r.nextInt()%2)+"1"+ Math.abs(r.nextInt()%2);
        for(int i=1; i<numCol-1; i++){
            randomMaze[0][i]="1"+Math.abs(r.nextInt()%2)+randomMaze[0][i-1].charAt(3)+Math.abs(r.nextInt()%2);
        }
        randomMaze[0][numCol-1]="1"+Math.abs(r.nextInt()%2)+randomMaze[0][numCol-2].charAt(3)+"1";

        //fill in the middle rows
        for(int i=1; i<numRow-1; i++){
            randomMaze[i][0] = randomMaze[i-1][0].charAt(1)+""+Math.abs(r.nextInt()%2)+"1"+Math.abs(r.nextInt()%2);
            for(int j=1; j<numCol-1; j++)
            {
                randomMaze[i][j] = randomMaze[i-1][j].charAt(1)+""+Math.abs(r.nextInt()%2)+randomMaze[i][j-1].charAt(3)+""+Math.abs(r.nextInt()%2);
            }

            randomMaze[i][numCol-1]=randomMaze[i-1][numCol-1].charAt(1)+""+Math.abs(r.nextInt()%2)+""+randomMaze[i][numCol-2].charAt(3)+"1";
        }

        //fill in the last row
        randomMaze[numRow-1][0] = randomMaze[numRow-2][0].charAt(1)+"11"+Math.abs(r.nextInt()%2);
        for(int i=1; i<numCol-1; i++){
            randomMaze[numRow-1][i]=randomMaze[numRow-2][i].charAt(1)+"1"+randomMaze[numRow-1][i-1].charAt(3)+""+Math.abs(r.nextInt()%2);
        }
        randomMaze[numRow-1][numCol-1]=randomMaze[numRow-2][numCol-1].charAt(1)+"1"+ randomMaze[numRow-1][numCol-2].charAt(3)+"1";

        return randomMaze;
    }
    
     /**
     * method called to recursively solve the problem recursively using backtracking
     * NOT CURRENTLY IN USE, BUT FUNCTIONAL 
     *@ params: row, col - current coordinate of the algorithm
     *  
     *@ returns: true if puzzle has solution; false otherwise         
     */
    public static boolean solve (int row, int col){

        //if row and col are the target, return true
        if(row==targetRow&&col==targetCol){
            solution [row][col] = 1;
            return true;
        }

        //if it is not the target, we mark it as part of the solution 
        solution[row][col]=1;

        //check if going up is part of the solution
        if(maze[row][col].charAt(0)=='0'&&solution[row-1][col]==0){
            if(solve((row-1),col)==true){
                return true; 
            }
        }

        //check if going left is part of the solution
        if(maze[row][col].charAt(2)=='0'&&solution[row][col-1]==0){
            if(solve(row,(col-1))==true){
                return true; 
            }
        }

        //check if going down is part of the solution
        if(maze[row][col].charAt(1)=='0'&&solution[row+1][col]==0){
            if(solve((row+1),col)==true){
                return true ; 
            }
        }

        //check if going right is part of the solution
        if(maze[row][col].charAt(3)=='0'&&solution[row][col+1]==0){
            if(solve(row,(col+1))==true){
                return true; 
            }
        }

        //if solution was not found, this cell is not part of the solution
        solution[row][col]=0;
        return false;
    }
}

