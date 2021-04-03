package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    Maze myMaze = new Maze();

    @Override
    public Maze generate(int row, int column) {
        return null;
    }

    public EmptyMazeGenerator(Maze myMaze) {
        this.myMaze = myMaze;
    }
}
