package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {

        int[][] maze = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == columns - 1) {
                    maze[i][j] = 1;
                }
                else {
                    maze[i][j] = new Random().nextInt(2); // 0 to 1
                }
            }
        int start1 = 0;
        int start2 = new Random().nextInt(columns-2) + 1;
        Position start = new Position(start1, start2);

        int goal1 = rows - 1;
        int goal2 = new Random().nextInt(columns - 2) + 1;
        Position goal = new Position(goal1, goal2);

        // Making sure that there is at least one path.
        maze[start1][start2] = 0;
        maze[1][start2] = 0;
        int x = start2;
        while(x != goal2){
            x = x > goal2 ? x-1 : x+1;
            maze[1][x] = 0;
        }
        for (int i = 1; i <= goal1; i++){
            maze[i][goal2] = 0;
        }
        return new Maze(maze, rows, columns, start, goal);
    }
}