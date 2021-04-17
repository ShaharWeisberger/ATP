package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import algorithms.search.MazeState;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {

    private Maze3DState startState;
    private Maze3DState goalState;
    private Maze3D maze;

    public SearchableMaze3D(Maze3D maze1) {
        maze = maze1;
        startState = new Maze3DState(maze.getStartPosition().getKey(), null, 0); // aaa
        goalState = new Maze3DState(maze.getGoalPosition().getKey(), null, Integer.MAX_VALUE);
    }

    @Override
    public Maze3DState getStartState() {
        return startState;
    }

    @Override
    public AState getGoalState() {
        return goalState;
    }

    //Returns the cost of moving from one position to its nieghbour, -1 if ilegal move.
    private int moveCost(int fromRow, int fromColumn, int toRow, int toColumn, int fromDepth, int toDepth) {

        if (toRow < 0 || toRow >= maze.getRows() || toColumn < 0 || toColumn >= maze.getColumns() || toDepth<0 || toDepth >= maze.getDepth()
                || maze.getMaze3d()[toRow][toColumn][toDepth] == 1) {
            return -1;
        }
        if (fromRow != toRow && fromColumn != toColumn && fromDepth != toDepth) {
            if((maze.getMaze3d()[fromRow][fromColumn][toDepth] == 0 && (maze.getMaze3d()[toRow][fromColumn][toDepth] == 0 || maze.getMaze3d()[fromRow][toColumn][toDepth] == 0))
            || (maze.getMaze3d()[toRow][fromColumn][fromDepth] == 0 && (maze.getMaze3d()[toRow][fromColumn][toDepth] == 0 || maze.getMaze3d()[toRow][toColumn][fromDepth] == 0))
                    ||(maze.getMaze3d()[fromRow][toColumn][fromDepth] == 0 && (maze.getMaze3d()[toRow][toColumn][fromDepth] == 0 || maze.getMaze3d()[fromRow][toColumn][toDepth] == 0)))
           // if (((maze.getMaze3d()[fromRow][toColumn][fromDepth] == 0 || maze.getMaze3d()[toRow][fromColumn][fromDepth] == 0) && fromDepth ==toDepth) ||
           //         (fromDepth != toDepth && (maze.getMaze3d()[fromRow][fromColumn][toDepth] == 0 && maze.getMaze3d()[toRow][fromColumn][toDepth] == 0)||
            //                maze.getMaze3d()[fromRow][fromColumn][toDepth] == 0) )
            {
                return 15;
            }
            else{
                return -1;
            }
        }
        if (fromRow != toRow && fromColumn != toColumn) {
            if (maze.getMaze3d()[toRow][fromColumn][fromDepth] == 0 || maze.getMaze3d()[fromRow][toColumn][fromDepth] == 0)
                return 15;
            else {
                return -1;
            }
        }
        if (fromRow != toRow && fromDepth != toDepth){
            if (maze.getMaze3d()[toRow][fromColumn][fromDepth] == 0 || maze.getMaze3d()[fromRow][fromColumn][toDepth] == 0)
                return 15;
            else {
                return -1;
            }
        }
        if (fromDepth != toDepth && fromColumn != toColumn) {
            if (maze.getMaze3d()[fromRow][fromColumn][toDepth] == 0 || maze.getMaze3d()[fromRow][toColumn][fromDepth] == 0)
                return 15;
            else {
                return -1;
            }
        }
        return 10;
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        Maze3DState current = (Maze3DState) s;
        int x = current.GetPosition().getColumnIndex();
        int y = current.GetPosition().getRowIndex();
        int z = current.GetPosition().getDepthIndex();
        //System.out.format("In getAllSuccessors x = %d y = %d  current = %s\n", x, y, s.getKey());

        ArrayList<AState> stateList = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i == 0 && j == 0 && k == 0)
                        continue;
                    int u = y + i;
                    int v = x + j;
                    int w = z + k;
                    int cost = moveCost(y, x, u, v, z, w);
                    if (cost != -1) {
                        Position3D position = new Position3D(u, v, w);
                        stateList.add(new Maze3DState(position.getKey(), current, cost + current.getCost()));
                        //System.out.format("In u = %d v = %d getAllSuccessors move = %s \n" ,u,v, position.getKey());
                    }
                }
            }
        }
        return stateList;
    }
}


