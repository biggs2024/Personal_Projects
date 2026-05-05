package Maze_Solver;

public class Maze_Test {
    public static void main(String[] args) {
        /** Creates Maze and generates start and end points */
        Maze maze = new Maze(5, 5);
        maze.placeStartAndEnd();
        
        System.out.println("Start Point: (" + maze.getStartColumn() + ", " + maze.getStartRow() + ")");
        System.out.println("End Point: (" + maze.getEndColumn() + ", " + maze.getEndRow() + ")");
        System.out.println();
        
        /** Prints Maze before path is generated */
        Maze_Input_Output.printMaze(maze);
        System.out.println();
        
        /** Carves Path utilizing random DFS */
        maze.generateMaze(maze.getStartColumn(), maze.getStartRow());
        
        /** Prints Maze after path is generated */
        Maze_Input_Output.printMaze(maze);
        System.out.println();
        
        /** Solves Maze and prints solution path */
        maze.solveFromStart();
        Maze_Input_Output.printMazeWithPath(maze);

        Maze_Input_Output.fileCreation(maze);
        Maze_Input_Output.fileLoading();
    }
}
