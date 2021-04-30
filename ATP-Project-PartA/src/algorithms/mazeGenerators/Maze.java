package algorithms.mazeGenerators;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Maze {

    private int[][] maze;
    private int rows;
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

    public Maze(byte[] array_of_bytes) {
        rows = getIntFromByteArray(array_of_bytes, 0);
        columns = getIntFromByteArray(array_of_bytes, 4);
        startPosition = new Position(getIntFromByteArray(array_of_bytes, 8), getIntFromByteArray(array_of_bytes, 12));
        GoalPosition = new Position(getIntFromByteArray(array_of_bytes, 16), getIntFromByteArray(array_of_bytes, 20));
        maze = getMatrixFromByteArray(array_of_bytes, 24, rows, columns);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze1 = (Maze) o;
        //        return rows == maze1.rows && columns == maze1.columns && Arrays.equals(maze, maze1.maze) &&
        //                Objects.equals(startPosition, maze1.startPosition) && Objects.equals(GoalPosition, maze1.GoalPosition);
        return rows == maze1.rows && columns == maze1.columns && startPosition.getRowIndex() == maze1.startPosition.getRowIndex()
                && GoalPosition.getRowIndex() == maze1.GoalPosition.getRowIndex() && startPosition.getColumnIndex() == maze1.startPosition.getColumnIndex()
                && GoalPosition.getColumnIndex() == maze1.GoalPosition.getColumnIndex();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
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

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (startPosition.getRowIndex() == i && startPosition.getColumnIndex() == j)
                    System.out.print("S");
                else if (GoalPosition.getRowIndex() == i && GoalPosition.getColumnIndex() == j)
                    System.out.print("E");
                else
                    System.out.print(maze[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }


    public byte[] toByteArray() {
        int array_size = 28 + (rows * columns) / 8;
        byte[] array_of_bytes = new byte[array_size];
        copyIntToByteArray(getRows(), array_of_bytes, 0);
        copyIntToByteArray(getColumns(), array_of_bytes, 4);
        copyIntToByteArray(getStartPosition().getRowIndex(), array_of_bytes, 8);
        copyIntToByteArray(getStartPosition().getColumnIndex(), array_of_bytes, 12);
        copyIntToByteArray(getGoalPosition().getRowIndex(), array_of_bytes, 16);
        copyIntToByteArray(getGoalPosition().getColumnIndex(), array_of_bytes, 20);
        copyMatrixToByteArray(getMaze(), array_of_bytes, 24);
        return array_of_bytes;
    }

    private void copyIntToByteArray(int input, byte[] target, int startInx) {
        target[startInx] = (byte) ((input & 0xFF000000) >> 24);
        target[startInx + 1] = (byte) ((input & 0x00FF0000) >> 16);
        target[startInx + 2] = (byte) ((input & 0x0000FF00) >> 8);
        target[startInx + 3] = (byte) ((input & 0x000000FF) >> 0);
    }

    private int getIntFromByteArray(byte[] byteArray, int startInx) {
        return ((byteArray[startInx] & 0xFF) << 24) |
                ((byteArray[startInx + 1] & 0xFF) << 16) |
                ((byteArray[startInx + 2] & 0xFF) << 8) |
                ((byteArray[startInx + 3] & 0xFF) << 0);
    }


    private void copyMatrixToByteArray(int[][] maze, byte[] arrayOfBytes, int startIndex) {
        int localCounter = 0;
        int byteIndex = startIndex;
        arrayOfBytes[byteIndex] = 0x00;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (localCounter == 8) {
                    localCounter = 0;
                    byteIndex++;
                    arrayOfBytes[byteIndex] = 0x00;
                }
                if (maze[i][j] == 1) {
                    arrayOfBytes[byteIndex] |= 1 << localCounter;
                }
                localCounter++;
            }
        }
    }

    private int[][] getMatrixFromByteArray(byte[] arrayOfBytes, int startIndex, int rows, int columns) {
        int localCounter = 0;
        int byteIndex = startIndex;
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (localCounter == 8) {
                    localCounter = 0;
                    byteIndex++;
                }
                matrix[i][j] = ((arrayOfBytes[byteIndex] >> localCounter) & 1) == 1 ? 1 : 0;
                localCounter++;
            }
        }
        return matrix;
    }

}