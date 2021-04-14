package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

public class Maze3D {

    private  int[][][] maze3d;
    private int depth;
    private  int rows;
    private int columns;
    private Position3D startPosition;
    private Position3D GoalPosition;

    public Maze3D(int[][][] maze3d, int depth, int rows, int columns, Position3D startPosition, Position3D goalPosition) {
        this.maze3d = maze3d;
        this.depth = depth;
        this.rows = rows;
        this.columns = columns;
        this.startPosition = startPosition;
        this.GoalPosition = goalPosition;
    }

    public int[][][] getMap() {
        return maze3d;
    }

    public Position3D getStartPosition() {
        return startPosition;
    }

    public Position3D getGoalPosition() {
        return GoalPosition;
    }

    public int getDepth() {
        return depth;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void print(){

        for (int d = 0; d < depth; d++) {
            System.out.println("depth = " + d);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (startPosition.getDepthIndex() == d && startPosition.getRowIndex() == i && startPosition.getColumnIndex() == j)
                        System.out.print("S");
                    else if (GoalPosition.getDepthIndex() == d && GoalPosition.getRowIndex() == i && GoalPosition.getColumnIndex() == j)
                        System.out.print("E");
                    else
                        System.out.print(maze3d[d][i][j]);
                }
                System.out.println();
            }
        }
    }


}
