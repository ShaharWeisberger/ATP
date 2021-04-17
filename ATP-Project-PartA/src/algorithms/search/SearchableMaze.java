package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{
    private MazeState startState;
    private MazeState goalState;
    private Maze maze;

    public SearchableMaze(Maze maze1) {
        maze = maze1;
        startState = new MazeState(maze.getStartPosition().getKey(), null, 0); // aaa
        goalState = new MazeState(maze.getGoalPosition().getKey(), null, Integer.MAX_VALUE);
    }

    @Override
    public MazeState getStartState() {
        return startState;
    }

    @Override
    public AState getGoalState() {
        return goalState;
    }

    //Returns the cost of moving from one position to its nieghbour, -1 if ilegal move.
    private int moveCost(int fromRow, int fromColumn, int toRow, int toColumn) {
        if (toRow < 0 || toRow >= maze.getRows() || toColumn < 0 || toColumn >= maze.getColumns()
                || maze.getMaze()[toRow][toColumn] == 1) {
            return -1;
        }
        if (fromRow != toRow && fromColumn != toColumn) {
            if (maze.getMaze()[fromRow][toColumn] == 0 || maze.getMaze()[toRow][fromColumn] == 0) {
                return 15;
            }
            return -1;
        }
        return 10;
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        MazeState current = (MazeState) s;
        int x = current.GetPosition().getColumnIndex();
        int y = current.GetPosition().getRowIndex();
        //System.out.format("In getAllSuccessors x = %d y = %d  current = %s\n", x, y, s.getKey());

        ArrayList<AState> stateList = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j==0)
                    continue;
                int u = y+i;
                int v = x+j;
                int cost = moveCost(y, x, u, v);
                if (cost != -1){
                    Position position = new Position(u, v);
                    stateList.add(new MazeState(position.getKey(), current, cost + current.getCost()));
                    //System.out.format("In u = %d v = %d getAllSuccessors move = %s \n" ,u,v, position.getKey());
                }
            }
        }
        return stateList;
    }

}

