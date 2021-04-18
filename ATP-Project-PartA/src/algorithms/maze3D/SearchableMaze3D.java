package algorithms.maze3D;
import algorithms.search.AState;
import algorithms.search.ISearchable;


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
    private int moveCost(int toDepth, int toRow, int toColumn ) {

        if (toRow < 0 || toRow >= maze.getRows() || toColumn < 0 || toColumn >= maze.getColumns() || toDepth < 0 || toDepth >= maze.getDepth()
                || maze.getMaze3d()[toDepth][toRow][toColumn] == 1) {
            return -1;
        }
        else
            {
            return 10;
        }
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        Maze3DState current = (Maze3DState) s;
        int z = current.GetPosition().getDepthIndex();
        int y = current.GetPosition().getRowIndex();
        int x = current.GetPosition().getColumnIndex();

        //System.out.format("In getAllSuccessors x = %d y = %d  current = %s\n", x, y, s.getKey());
        ArrayList<AState> stateList = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i == 0 && j == 0 && k == 0)
                        continue;
                    if (((i==1 || i==-1) && j==0 && k==0) || (i==0 && (j==1 || j==-1) && k==0) || (i==0 && j==0 && (k==1 || k==-1))) {
                        int u = z + i;
                        int v = y + j;
                        int w = x + k;
                        int cost = moveCost(u, v, w);
                        if (cost != -1) {
                            Position3D position3D = new Position3D(u, v, w);
                            stateList.add(new Maze3DState(position3D.getKey(), current, cost + current.getCost()));
                            //System.out.format("In u = %d v = %d getAllSuccessors move = %s \n" ,u,v, position.getKey());
                        }
                    }
                }
            }
        }
        //System.out.println(" stateList = "+ stateList.toString());
        return stateList;
    }
}


