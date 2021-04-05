package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {

        ArrayList<Position> neighbors = new ArrayList<>();
        ArrayList<Position> unvisited = new ArrayList<>();

        int[][] maze = new int[rows][columns];

        //create a maze with walls only
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                maze[i][j] = 1;
            }
        }

        //random cell
        int x = new Random().nextInt(rows);
        int y = new Random().nextInt(columns);
        maze[x][y] = 0;

        if (x+1 < rows) neighbors.add(new Position(x+1,y));
        if (x-1 >= 0) neighbors.add(new Position(x-1,y));
        if (y+1 < columns) neighbors.add(new Position(x,y+1));
        if (y-1 >= 0) neighbors.add(new Position(x,y-1));

        if(rows==2 && columns==2){

            int goal1;
            int goal2;

            int random = new Random().nextInt(neighbors.size());
            maze[neighbors.get(random).getRowIndex()][neighbors.get(random).getColumnIndex()]=0;
            Position starty = new Position(neighbors.get(random).getRowIndex(),neighbors.get(random).getColumnIndex());
            if(starty.getRowIndex()==0)
                goal1=1;
            else goal1 =0;
            if(starty.getColumnIndex()==0)
                goal2=1;
            else goal2 =0;
            Position goaly = new Position(goal1,goal2);
            maze[starty.getRowIndex()][starty.getColumnIndex()]=0;
            maze[goaly.getRowIndex()][goaly.getColumnIndex()]=0;
            return new Maze(maze,rows,columns,starty,goaly);
        }

        while (!neighbors.isEmpty())
        {
            //random neighbor
            int randomNeighbor = new Random().nextInt(neighbors.size());
            Position pos = new Position(neighbors.get(randomNeighbor).getRowIndex(),neighbors.get(randomNeighbor).getColumnIndex());
            unvisited.clear();


            if (pos.getRowIndex() + 1 < rows && maze[pos.getRowIndex() + 1][pos.getColumnIndex()] == 0)
                unvisited.add(new Position(pos.getRowIndex() - 1, pos.getColumnIndex()));

            if (pos.getRowIndex() - 1 >= 0 && maze[pos.getRowIndex() - 1][pos.getColumnIndex()] == 0)
                unvisited.add(new Position(pos.getRowIndex() + 1, pos.getColumnIndex()));

            if (pos.getColumnIndex() + 1 < columns && maze[pos.getRowIndex()][pos.getColumnIndex() + 1] == 0)
                unvisited.add(new Position(pos.getRowIndex(), pos.getColumnIndex() - 1));

            if (pos.getColumnIndex() - 1 >= 0 && maze[pos.getRowIndex()][pos.getColumnIndex() - 1] == 0)
                unvisited.add(new Position(pos.getRowIndex(), pos.getColumnIndex() + 1));

            if (unvisited.size() == 1)
            {

                maze[pos.getRowIndex()][pos.getColumnIndex()] = 0;

                if (unvisited.get(0).getRowIndex() >= 0 && unvisited.get(0).getRowIndex() < rows && unvisited.get(0).getColumnIndex() >= 0 && unvisited.get(0).getColumnIndex() < columns)
                {
                    maze[unvisited.get(0).getRowIndex()][unvisited.get(0).getColumnIndex()] = 0;
                    // 3.2.2 Add the neighboring walls of the cell to the wall list.
                    if (unvisited.get(0).getRowIndex() + 1 < rows && maze[unvisited.get(0).getRowIndex() + 1][unvisited.get(0).getColumnIndex()] == 1)
                        neighbors.add(new Position(unvisited.get(0).getRowIndex() + 1, unvisited.get(0).getColumnIndex()));
                    if (unvisited.get(0).getRowIndex() - 1 >= 0 && maze[unvisited.get(0).getRowIndex() - 1][unvisited.get(0).getColumnIndex()] == 1)
                        neighbors.add(new Position(unvisited.get(0).getRowIndex() - 1, unvisited.get(0).getColumnIndex()));
                    if (unvisited.get(0).getColumnIndex() + 1 < columns && maze[unvisited.get(0).getRowIndex()][unvisited.get(0).getColumnIndex() + 1] == 1)
                        neighbors.add(new Position(unvisited.get(0).getRowIndex(), unvisited.get(0).getColumnIndex() + 1));
                    if (unvisited.get(0).getColumnIndex() - 1 >= 0 && maze[unvisited.get(0).getRowIndex()][unvisited.get(0).getColumnIndex() - 1] == 1)
                        neighbors.add(new Position(unvisited.get(0).getRowIndex(), unvisited.get(0).getColumnIndex() - 1));
                }
            }
            neighbors.remove(randomNeighbor);


        }
        //start pos
        int start1 = new Random().nextInt(rows);
        int start2 = new Random().nextInt(columns);
        while(maze[start1][start2] == 1)
        {
            start1 = new Random().nextInt(rows);
            start2 = new Random().nextInt(columns);
        }
        Position start = new Position(start1,start2);

        //goal pos
        int goal1 = new Random().nextInt(rows);
        int goal2 = new Random().nextInt(columns);
        while(maze[goal1][goal2] == 1 || start.getRowIndex()==goal1 ||start.getColumnIndex()==goal2)
        {
            goal1 = new Random().nextInt(rows);
            goal2 = new Random().nextInt(columns);
        }
        Position goal = new Position(goal1,goal2);
        return new Maze(maze,rows,columns,start,goal);
    }
}