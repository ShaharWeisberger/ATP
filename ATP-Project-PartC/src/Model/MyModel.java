package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;


public class MyModel extends Observable implements IModel, Serializable {
    private Maze maze;
    private int playerRow;
    private int playerCol;
    private Solution solution;
    private MyMazeGenerator generator;
    private boolean finish;

    public MyModel() {
        generator = new MyMazeGenerator();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        finish = false;
        maze = generator.generate(rows, cols);
        setChanged();
        notifyObservers("maze generated");
        movePlayer(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());

        // start position:
    }

    @Override
    public void updateMaze(Maze maz) {
        finish = false;
        maze = maz;
        setChanged();
        notifyObservers("maze generated");
        movePlayer(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());

        // start position:
    }
    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {
        if (maze==null){
            return;
        }
        int newRow = playerRow;
        int newColumn = playerCol;
        if(isFinish()){
            return;
        }
        switch (direction) {
            case UP -> {
                newRow = playerRow -1;
            }
            case DOWN -> {
                newRow = playerRow + 1;
            }
            case LEFT -> {
                newColumn = playerCol - 1;
            }
            case RIGHT -> {
                newColumn = playerCol +1;
            }
            case UPRIGHT -> {
                newRow = playerRow -1;
                newColumn = playerCol +1;
            }
            case UPLEFT -> {
                newRow = playerRow -1;
                newColumn = playerCol - 1;
            }
            case DOWNRIGHT -> {
                newRow = playerRow + 1;
                newColumn = playerCol + 1;
            }
            case DOWNLEFT -> {
                newRow = playerRow + 1;
                newColumn = playerCol - 1;
            }
            case INPLACE -> {
                newRow = playerRow;
                newColumn = playerCol;
            }
        }
        if (newRow >= 0 && newRow < maze.getRows() && newColumn >= 0 && newColumn < maze.getColumns()
                && !isWall(newRow, newColumn)){
            movePlayer(newRow, newColumn);
        }
        else{
            //wallSound();
            setChanged();
            notifyObservers("Wall");
        }
    }

    private void wallSound(){

        File f = new File("resources/sounds/warningBeep.wav");
        String s = f.toURI().toString();
        System.out.println(s);
        Media m = new Media(f.toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
        //Media sound = new Media(getClass().getResource("warningBeep.wav").toExternalForm());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();
        //AudioClip audioClip = new AudioClip(this.getClass().getResource("warningBeep.wav").toString());
        //audioClip.play();


    }

    private boolean isWall(int row, int col){
        return maze.getMaze()[row][col] ==1;
    }
    public boolean isFinish(){
        return finish;
    }

    private void movePlayer(int row, int col){
        this.playerRow = row;
        this.playerCol = col;
        if (row == maze.getGoalPosition().getRowIndex() && col == maze.getGoalPosition().getColumnIndex()){
            finish = true;
            setChanged();
            notifyObservers("Finish");
        }
            setChanged();
            notifyObservers("player moved");
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void solveMaze() {
        //solve the maze
        if (maze!=null) {
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            ISearchingAlgorithm searcher = new BestFirstSearch();
            solution = searcher.solve(searchableMaze);
            setChanged();
            notifyObservers("maze solved");
        }
    }

    @Override
    public Solution getSolution() {
        return solution;
    }
}

