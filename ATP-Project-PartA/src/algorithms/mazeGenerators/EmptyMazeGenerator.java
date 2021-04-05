package algorithms.mazeGenerators;

import java.util.Random;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        //maze
        int[][] EmptyMaze = new int[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                EmptyMaze[i][j] = 0;
            }
        }
        //start
        int start2 = new Random().nextInt(columns -2) + 1;
        Position start = new Position(0,start2);
        //goal
        int goal1 = rows -1 ;
        int goal2 = new Random().nextInt(columns -2) + 1;
        Position goal = new Position(goal1,goal2);

        //return maze
        return new Maze(EmptyMaze, rows, columns, start, goal);
    }
}
