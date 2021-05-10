package algorithms.maze3D;


public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    public  abstract Maze3D generate(int depth, int row, int column);
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long start = System.currentTimeMillis();
        generate(depth, row, column);
        long finish = System.currentTimeMillis();
        return finish - start;
    }
}

