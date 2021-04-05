package algorithms.mazeGenerators;

public class Maze {

    private  int[][] maze;
    private  int rows;
    private int columns;
    private Position startPosition;
    private Position GoalPosition;

    public Maze(int[][] maze, int rows, int columns, Position startPosition, Position goalPosition) {
        this.maze = maze;
        this.rows = rows;
        this.columns = columns;
        this.startPosition = startPosition;
        GoalPosition = goalPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return GoalPosition;
    }

    public int[][] getMaze() {
        return maze;
    }

    public void print(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (startPosition.getRowIndex()==i && startPosition.getColumnIndex()==j)
                    System.out.print("S");
                else if (GoalPosition.getRowIndex()==i && GoalPosition.getColumnIndex()==j)
                    System.out.print("E");
                else
                    System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}
