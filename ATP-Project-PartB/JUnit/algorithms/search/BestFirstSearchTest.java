package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    private BestFirstSearch bestFS = new BestFirstSearch();
    @Test
    void solve() throws Exception
    {
        boolean sign = false;
        try {
            bestFS.solve(null);
        }
        catch (RuntimeException e)
        {
            sign = true;
        }
        assertTrue(sign);
    }

    @Test
    void checkCompareState() {
        AState state1 = new MazeState("1$2",null ,10);
        AState state2 = new MazeState("2$1",null ,20);
        int comp = bestFS.compareState(state1, state2);
        assertTrue(comp<0);
    }

    @Test
    void getName() throws Exception
    {
        assertEquals("BEST_FS", bestFS.getName());
    }

    //Tests if the run time of a maze 1000x1000 is under a minute.
    @Test
    void RunTimeUnderMinute() throws Exception{
        Maze maze = new MyMazeGenerator().generate(1000,1000);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        long startTime = System.currentTimeMillis();
        bestFS.solve(searchableMaze);
        long endTime = System.currentTimeMillis();
        assertTrue((endTime-startTime)<=60000);
    }
}