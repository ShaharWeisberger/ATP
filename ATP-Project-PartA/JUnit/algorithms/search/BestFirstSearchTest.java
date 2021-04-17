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
    public void solve() throws Exception
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
    public void getName() throws Exception
    {
        assertEquals("BEST_FS", bestFS.getName());
    }

    @Test
    public void name() {
    }

    @Test
    void compareState() {
    }

    @Test
    public void goalStateNutNull() throws Exception{
        assertEquals(null, bestFS.getGoalState());
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