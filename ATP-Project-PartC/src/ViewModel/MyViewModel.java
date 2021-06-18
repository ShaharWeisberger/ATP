package ViewModel;

import Model.IModel;
import Model.MovementDirection;

import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the Model for it's changes
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public boolean isFinish(){
        return model.isFinish();
    }
    public Maze getMaze(){
        return model.getMaze();
    }

    public int getPlayerRow(){
        return model.getPlayerRow();
    }

    public int getPlayerCol(){
        return model.getPlayerCol();
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public void generateMaze(int rows, int cols){
        model.generateMaze(rows, cols);
    }

    public void updateMaze(Maze maze){ model.updateMaze(maze); }

    public IModel getModel(){
        return model;
    }

    public void movePlayer(KeyEvent keyEvent){
        MovementDirection direction;
        if (keyEvent==null){
            direction = MovementDirection.INPLACE;
        } else {
            switch (keyEvent.getCode()) {
                case UP -> direction = MovementDirection.UP;
                case DOWN -> direction = MovementDirection.DOWN;
                case LEFT -> direction = MovementDirection.LEFT;
                case RIGHT -> direction = MovementDirection.RIGHT;
                case PAGE_UP -> direction = MovementDirection.UPRIGHT;
                case HOME -> direction = MovementDirection.UPLEFT;
                case PAGE_DOWN -> direction = MovementDirection.DOWNRIGHT;
                case END -> direction = MovementDirection.DOWNLEFT;
                default -> {
                    // no need to move the player...
                    return;
                }
            }
        }
        model.updatePlayerLocation(direction);
    }

    public void solveMaze(){
        model.solveMaze();
    }
}

