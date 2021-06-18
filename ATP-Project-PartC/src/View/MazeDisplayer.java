package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;



import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends ResizableCanvas {
    private MyViewModel model;
    private Maze maze;
    // player position:
    private int playerRow = 0;
    private int playerCol = 0;
    private int previousPlayerRow = -1;
    private int previousPlayerCol = -1;
    private double cellHeight;
    private double cellWidth;
    private int rows;
    private int cols;
    private double canvasHeight;
    private double canvasWidth;
    private GraphicsContext graphicsContext;
    private IMazeBehaviour mazeBehaviour;

    public MazeDisplayer() {
        graphicsContext = getGraphicsContext2D();
    }

    public void SetMazeBehaviour(IMazeBehaviour mazeBehaviour1){
        mazeBehaviour = mazeBehaviour1;
    }
    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        if (maze==null){
            return;
        }
        if (model.isFinish()) {
            //KKK
            try {
                Image finish = new Image(new FileInputStream("./resources/images/Trophy.jpeg"));
                double imageSize = canvasWidth / 2;
                graphicsContext.drawImage(finish, canvasWidth / 2 - imageSize / 2, canvasHeight / 2 - imageSize / 2, imageSize, imageSize);
            } catch (FileNotFoundException e) {
                System.out.println("There is no Finish image file.");
            }
            System.out.println("Finish");
        } else {
            // "cover"" previous player drawing
            if (this.previousPlayerCol!=-1) {
                drawCell(this.previousPlayerRow, this.previousPlayerCol);
            }
            this.playerRow = row;
            this.playerCol = col;
            drawPlayer();
            this.previousPlayerRow = row;
            this.previousPlayerCol = col;
        }
    }


    public void drawMaze(MyViewModel model) {
        previousPlayerRow = -1;
        previousPlayerCol = -1;
        this.model = model;
        this.maze = model.getMaze();
        draw();
    }

    @Override
    public void draw() {
        if (maze != null) {
            canvasHeight = getHeight();
            canvasWidth = getWidth();
            rows = maze.getRows();
            cols = maze.getColumns();

            cellHeight = canvasHeight / (rows);
            cellWidth = canvasWidth / (cols);

            if (cellHeight<cellWidth){
                cellWidth = cellHeight;
            } else {
                cellHeight = cellWidth;
            }

            if (cellHeight>30){
                cellHeight = 30;
                cellWidth = 30;
            }
            graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWater(graphicsContext, cellHeight, cellWidth, rows, cols);
        //    previousPlayerRow = -1;
         //    drawPlayer();
        }
    }

    private Position getCellCenter(AState state) {
        String[] arrS = state.getKey().split("\\$");
        return new Position((int) (Integer.parseInt(arrS[0]) * cellWidth + cellWidth / 2),
                (int) (Integer.parseInt(arrS[1]) * cellHeight + cellHeight / 2));
    }

    private Position getCellCenter(int col, int row) {
        return new Position((int)(col * cellWidth + cellWidth / 2),
                (int)(row * cellHeight + cellHeight / 2));
    }

    public void drawSolution(Solution solution) {
        ArrayList<AState> arrayList = solution.getSolutionPath();
        graphicsContext.beginPath();
        Position pos = getCellCenter(arrayList.get(0));
        graphicsContext.moveTo(pos.getColumnIndex(), pos.getRowIndex());
        for (int i = 1; i < arrayList.size(); i++) {
            pos = getCellCenter(arrayList.get(i));
            graphicsContext.lineTo(pos.getColumnIndex(), pos.getRowIndex());
        }
        graphicsContext.setStroke(Color.RED);
        graphicsContext.stroke();
    }

    private void drawPlayer() {

        if (previousPlayerRow!=-1){
            graphicsContext.beginPath();
            Position pos = getCellCenter(previousPlayerRow,previousPlayerCol);
            graphicsContext.moveTo(pos.getColumnIndex(),pos.getRowIndex());
            pos = getCellCenter(getPlayerCol(), getPlayerRow());
            graphicsContext.lineTo(pos.getRowIndex(), pos.getColumnIndex());
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.stroke();
        }

        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        mazeBehaviour.DrawPlayerIcon(graphicsContext,x, y, cellWidth, cellHeight);

    }

    private void drawMazeWater(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        Image Water = null;
        String waterId = "";
        String path = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                drawCell(i, j);
            }
        }
        System.out.println("Target col: " + maze.getGoalPosition().getColumnIndex()+
                " Taregt Row: "+  maze.getGoalPosition().getRowIndex());

        drawTarget(maze.getGoalPosition().getColumnIndex(),maze.getGoalPosition().getRowIndex());
    }

    private void drawTarget(int i, int j) {
        try {
            Image goal = new Image(new FileInputStream("./resources/images/" + "targetFlag" + ".png"));
            graphicsContext.drawImage(goal, maze.getGoalPosition().getColumnIndex() * cellWidth,
                    maze.getGoalPosition().getRowIndex() * cellHeight, cellWidth * 1.5, cellHeight * 1.5);
        } catch (FileNotFoundException e) {
            System.out.println("There is no Target image file");
        }
    }


    private void drawCell(int i, int j) {
        Image Water = null;
        String waterId = "";
        String prefix = "./resources/images/";
        String suffix = ".png";
        String path = "";
        double x = j * cellWidth;
        double y = i * cellHeight;
        if (maze.getMaze()[i][j] == 1) {
            //if it is a wall:
            if (i == 0) {
                if (maze.getMaze()[i + 1][j] == 0)
                    waterId = "0000";
                else
                    waterId = "1000";
            } else if (j == 0) {
                if (maze.getMaze()[i][j + 1] == 0)
                    waterId = "0000";
                else
                    waterId = "0100";
            } else if (i == rows - 1) {
                if (maze.getMaze()[i - 1][j] == 0)
                    waterId = "0000";
                else
                    waterId = "0010";
            } else if (j == cols - 1) {
                if (maze.getMaze()[i][j - 1] == 0)
                    waterId = "0000";
                else
                    waterId = "0001";
            } else {
                waterId = String.valueOf(maze.getMaze()[i + 1][j]) + String.valueOf(maze.getMaze()[i][j + 1]) + String.valueOf(maze.getMaze()[i - 1][j]) + String.valueOf(maze.getMaze()[i][j - 1]);
            }
        } else {
            waterId = "Grass";
        }
        try {
            path = prefix + waterId + suffix;
            Water = new Image(new FileInputStream(path));
            //graphicsContext.clearRect(x, y, cellWidth, cellHeight);
            graphicsContext.drawImage(Water, x, y, cellWidth, cellHeight);
            //graphicsContext.stroke();

        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }
    }
}

