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

    public int[][][] getMaze3d() {
        return maze3d;
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
        System.out.println("{");
        for(int depth = 0; depth < maze3d.length; depth++){
            for(int row = 0; row < maze3d[0].length; row++) {
                System.out.print("{ ");
                for (int col = 0; col < maze3d[0][0].length; col++) {
                    if (depth == startPosition.getDepthIndex() && row == startPosition.getRowIndex() && col == startPosition.getColumnIndex()) // if the position is the start - mark with S
                        System.out.print("S ");
                    else {
                        if (depth == GoalPosition.getDepthIndex() && row == GoalPosition.getRowIndex() && col == GoalPosition.getColumnIndex()) // if the position is the goal - mark with E
                            System.out.print("E ");
                        else
                            System.out.print(maze3d[depth][row][col] + " ");
                    }
                }
                System.out.println("}");
            }
            if(depth < maze3d.length - 1) {
                System.out.print("---");
                for (int i = 0; i < maze3d[0][0].length; i++)
                    System.out.print("--");
                System.out.println();
            }
        }
        System.out.println("}");
    }


}

