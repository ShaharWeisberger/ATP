package algorithms.mazeGenerators;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Maze implements java.io.Serializable{

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
        rows = getIntFrom2ByteArray(array_of_bytes, 2);
        columns = getIntFrom2ByteArray(array_of_bytes, 4);
        startPosition = new Position(getIntFrom2ByteArray(array_of_bytes, 6), getIntFrom2ByteArray(array_of_bytes, 8));
        GoalPosition = new Position(getIntFrom2ByteArray(array_of_bytes, 10), getIntFrom2ByteArray(array_of_bytes, 12));
        maze = getMatrixFromByteArray(array_of_bytes, 14, rows, columns);
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
        int array_size = 14 + rows * columns;
        byte[] array_of_bytes = new byte[array_size];
        copyIntTo2ByteArray(12, array_of_bytes, 0);
        copyIntTo2ByteArray(getRows(), array_of_bytes, 2);
        copyIntTo2ByteArray(getColumns(), array_of_bytes, 4);
        copyIntTo2ByteArray(getStartPosition().getRowIndex(), array_of_bytes, 6);
        copyIntTo2ByteArray(getStartPosition().getColumnIndex(), array_of_bytes, 8);
        copyIntTo2ByteArray(getGoalPosition().getRowIndex(), array_of_bytes, 10);
        copyIntTo2ByteArray(getGoalPosition().getColumnIndex(), array_of_bytes, 12);
        copyMatrixToByteArray(getMaze(), array_of_bytes, 14);
        System.out.println("array before compression : "+array_of_bytes.length);
        return array_of_bytes;
    }

    private void copyIntTo2ByteArray(int input, byte[] target, int startInx) {
        target[startInx] = (byte) ((input & 0x0000FF00) >> 8);
        target[startInx + 1] = (byte) ((input & 0x000000FF) >> 0);
    }

    private int getIntFrom2ByteArray(byte[] byteArray, int startInx) {
        return ((byteArray[startInx] & 0xFF) << 8) |
                ((byteArray[startInx +1] & 0xFF) << 0);
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
        int byteIndex = startIndex;
        arrayOfBytes[byteIndex] = 0x00;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                arrayOfBytes[byteIndex++] = (byte) maze[i][j];
            }
        }
    }

    private int[][] getMatrixFromByteArray(byte[] arrayOfBytes, int startIndex, int rows, int columns) {
        int byteIndex = startIndex;
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = arrayOfBytes[byteIndex++];
            }
        }
        return matrix;
    }

}