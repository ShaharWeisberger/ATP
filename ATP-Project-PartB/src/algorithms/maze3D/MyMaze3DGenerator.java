package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator {
    @Override
    public Maze3D generate(int depth, int row, int column) {

        ArrayList<Position3D> neighbors3D = new ArrayList<>();
        ArrayList<Position3D> unvisited3D = new ArrayList<>();

        int[][][] Maze3D = new int[depth][row][column];
        //create a maze with walls only
        for (int i = 0; i < depth; i++){
            for (int j = 0; j < row; j++){
                for (int k = 0; k < column; k++){
                    Maze3D[i][j][k] = 1;
                }
            }
        }
        //random cell
        int d = new Random().nextInt(depth);
        int x = new Random().nextInt(row);
        int y = new Random().nextInt(column);
        Maze3D[d][x][y] = 0;

        if (x+1 < row) neighbors3D.add(new Position3D(d,x+1,y));
        if (x-1 >= 0) neighbors3D.add(new Position3D(d,x-1,y));
        if (y+1 < column) neighbors3D.add(new Position3D(d,x,y+1));
        if (y-1 >= 0) neighbors3D.add(new Position3D(d,x,y-1));
        //3D neighbors
        if (d+1 < depth) neighbors3D.add(new Position3D(d+1,x,y));
        if (d-1 >= 0) neighbors3D.add(new Position3D(d-1,x,y));
        while (!neighbors3D.isEmpty())
        {
            //random neighbor
            int randomNeighbor = new Random().nextInt(neighbors3D.size());
            Position3D pos = new Position3D(neighbors3D.get(randomNeighbor).getDepthIndex(),
                    neighbors3D.get(randomNeighbor).getRowIndex(),neighbors3D.get(randomNeighbor).getColumnIndex());
            unvisited3D.clear();

            if (pos.getRowIndex() + 1 < row && Maze3D[pos.getDepthIndex()][pos.getRowIndex() + 1][pos.getColumnIndex()] == 0)
                unvisited3D.add(new Position3D(pos.getDepthIndex(),pos.getRowIndex() - 1, pos.getColumnIndex()));

            if (pos.getRowIndex() - 1 >= 0 && Maze3D[pos.getDepthIndex()][pos.getRowIndex() - 1][pos.getColumnIndex()] == 0)
                unvisited3D.add(new Position3D(pos.getDepthIndex(),pos.getRowIndex() + 1, pos.getColumnIndex()));

            if (pos.getColumnIndex() + 1 < column && Maze3D[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex() + 1] == 0)
                unvisited3D.add(new Position3D(pos.getDepthIndex(),pos.getRowIndex(), pos.getColumnIndex() - 1));

            if (pos.getColumnIndex() - 1 >= 0 && Maze3D[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex() - 1] == 0)
                unvisited3D.add(new Position3D(pos.getDepthIndex(),pos.getRowIndex(), pos.getColumnIndex() + 1));

            //3D

            if (pos.getDepthIndex() + 1 < depth && Maze3D[pos.getDepthIndex() + 1][pos.getRowIndex()][pos.getColumnIndex()] == 0)
                unvisited3D.add(new Position3D(pos.getDepthIndex() - 1,pos.getRowIndex(), pos.getColumnIndex()));

            if (pos.getDepthIndex() - 1 >= 0 && Maze3D[pos.getDepthIndex() - 1][pos.getRowIndex()][pos.getColumnIndex()] == 0)
                unvisited3D.add(new Position3D(pos.getDepthIndex() + 1,pos.getRowIndex(), pos.getColumnIndex()));

            if (unvisited3D.size() == 1)
            {
                Maze3D[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex()] = 0;

                if (unvisited3D.get(0).getRowIndex() >= 0 && unvisited3D.get(0).getRowIndex() < row && unvisited3D.get(0).getColumnIndex() >= 0
                        && unvisited3D.get(0).getColumnIndex() < column && unvisited3D.get(0).getDepthIndex() >= 0 && unvisited3D.get(0).getDepthIndex() < depth)
                {
                    Maze3D[unvisited3D.get(0).getDepthIndex()][unvisited3D.get(0).getRowIndex()][unvisited3D.get(0).getColumnIndex()] = 0;
                    // 3.2.2 Add the neighboring walls of the cell to the wall list.
                    if (unvisited3D.get(0).getRowIndex() + 1 < row && Maze3D[unvisited3D.get(0).getDepthIndex()][unvisited3D.get(0).getRowIndex() + 1][unvisited3D.get(0).getColumnIndex()] == 1)
                        neighbors3D.add(new Position3D(unvisited3D.get(0).getDepthIndex(),unvisited3D.get(0).getRowIndex() + 1, unvisited3D.get(0).getColumnIndex()));
                    if (unvisited3D.get(0).getRowIndex() - 1 >= 0 && Maze3D[unvisited3D.get(0).getDepthIndex()][unvisited3D.get(0).getRowIndex() - 1][unvisited3D.get(0).getColumnIndex()] == 1)
                        neighbors3D.add(new Position3D(unvisited3D.get(0).getDepthIndex(),unvisited3D.get(0).getRowIndex() - 1, unvisited3D.get(0).getColumnIndex()));

                    if (unvisited3D.get(0).getColumnIndex() + 1 < column && Maze3D[unvisited3D.get(0).getDepthIndex()][unvisited3D.get(0).getRowIndex()][unvisited3D.get(0).getColumnIndex() + 1] == 1)
                        neighbors3D.add(new Position3D(unvisited3D.get(0).getDepthIndex(),unvisited3D.get(0).getRowIndex(), unvisited3D.get(0).getColumnIndex() + 1));
                    if (unvisited3D.get(0).getColumnIndex() - 1 >= 0 && Maze3D[unvisited3D.get(0).getDepthIndex()][unvisited3D.get(0).getRowIndex()][unvisited3D.get(0).getColumnIndex() - 1] == 1)
                        neighbors3D.add(new Position3D(unvisited3D.get(0).getDepthIndex(),unvisited3D.get(0).getRowIndex(), unvisited3D.get(0).getColumnIndex() - 1));

                    //3D
                    if (unvisited3D.get(0).getDepthIndex() + 1 < depth && Maze3D[unvisited3D.get(0).getDepthIndex() + 1][unvisited3D.get(0).getRowIndex()][unvisited3D.get(0).getColumnIndex()] == 1)
                        neighbors3D.add(new Position3D(unvisited3D.get(0).getDepthIndex() + 1,unvisited3D.get(0).getRowIndex(), unvisited3D.get(0).getColumnIndex()));
                    if (unvisited3D.get(0).getDepthIndex() - 1 >= 0 && Maze3D[unvisited3D.get(0).getDepthIndex() - 1][unvisited3D.get(0).getRowIndex()][unvisited3D.get(0).getColumnIndex()] == 1)
                        neighbors3D.add(new Position3D(unvisited3D.get(0).getDepthIndex() - 1,unvisited3D.get(0).getRowIndex(), unvisited3D.get(0).getColumnIndex()));


                }
            }
            neighbors3D.remove(randomNeighbor);
        }


        //creates A list of possible start and goal Positions.
        List<Position3D> possible = new ArrayList<>();
        int k;
        for (k = 0 ; k< Maze3D.length ; k++)
        {
            int i;
            for (i = 0 ; i < Maze3D[0][0].length-1 ; i++)
            {
                if(Maze3D[k][0][i] == 0)
                    possible.add(new Position3D(k , 0 ,i));
            }
            for (i = 0 ; i < Maze3D[0].length ; i++)
            {
                if(Maze3D[k][i][0] == 0)
                    possible.add(new Position3D(k,i , 0));
            }

            for (i = 0 ; i < Maze3D[0][0].length ; i++)
            {
                if(Maze3D[k][Maze3D[0].length-1][i] == 0)
                    possible.add(new Position3D(k , Maze3D[0].length-1 , i));
            }

            for (i = 0 ; i < Maze3D[0].length-1; i++)
            {
                if(Maze3D[k][i][Maze3D[0][0].length-1] == 0)
                    possible.add(new Position3D(k,i, Maze3D[0][0].length-1));
            }
        }

        // Sets a start and goal Positions that are not neighbors.
        Random rd = new Random();
        int start2 = rd.nextInt(possible.size());
        Position3D start = possible.get(start2);
        //Maze3D = possible.get(start);
        possible.remove(start2);
        Position3D goalPos = null;
        boolean far_from_me = false;
        while (!far_from_me){
            int goal = rd.nextInt(possible.size());
            goalPos = possible.get(goal);
            if ((goalPos.getDepthIndex()+1 == start.getDepthIndex() && goalPos.getRowIndex() == start.getRowIndex() && goalPos.getColumnIndex() == start.getColumnIndex())
                    || (goalPos.getDepthIndex()-1 == start.getDepthIndex() && goalPos.getRowIndex() == start.getRowIndex() && goalPos.getColumnIndex() == start.getColumnIndex()) ||
                    (goalPos.getDepthIndex() == start.getDepthIndex() && goalPos.getRowIndex()+1 == start.getRowIndex() && goalPos.getColumnIndex() == start.getColumnIndex()) ||
                    (goalPos.getDepthIndex() == start.getDepthIndex() && goalPos.getRowIndex()-1 == start.getRowIndex() && goalPos.getColumnIndex() == start.getColumnIndex())
            || (goalPos.getDepthIndex() == start.getDepthIndex() && goalPos.getRowIndex() == start.getRowIndex() && goalPos.getColumnIndex()+1 == start.getColumnIndex())
            || (goalPos.getDepthIndex() == start.getDepthIndex() && goalPos.getRowIndex() == start.getRowIndex() && goalPos.getColumnIndex()-1 == start.getColumnIndex())){
                far_from_me = false;
            }
            else
                far_from_me = true;
        }
        return new Maze3D(Maze3D,depth,row,column,start,goalPos);
    }
}
