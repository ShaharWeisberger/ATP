package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    public  abstract Maze generate(int rows, int columns);

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long start = System.currentTimeMillis();
        generate(rows, columns);
        long finish = System.currentTimeMillis();
        return finish - start;
    }
}
