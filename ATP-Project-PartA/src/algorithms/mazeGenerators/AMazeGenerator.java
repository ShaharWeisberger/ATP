package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public long measureAlgorithmTimeMillis(int row, int column) {
        long startTime = System.currentTimeMillis();
        generate(row,column);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
