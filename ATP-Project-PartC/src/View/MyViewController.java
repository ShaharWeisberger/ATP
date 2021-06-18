package View;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable, Observer {
    public MyViewModel viewModel;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public Label playerRow;
    public Label playerCol;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();


    //private Button b;
    private Stage stage;
    private Utilities utilities;
    @FXML
    private Button solveMazeButton ;

    public void setStage(Stage stage) {

        this.stage = stage;
        ChangeListener listener = new ChangeListener(){
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println("changed");
                mazeDisplayer.resize(stage.getWidth()-200,stage.getHeight()-100);
            }
        };
        this.stage.setMinWidth(400);
        this.stage.setMinHeight(300);
        this.stage.widthProperty().addListener(listener);
        this.stage.heightProperty().addListener(listener);
        mazeDisplayer.setWidth(stage.getWidth()-200);
        mazeDisplayer.setHeight(stage.getHeight()-100);
        mazeDisplayer.setFocusTraversable(true);

    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        utilities = new Utilities(viewModel);
        this.viewModel.addObserver(this);
        solveMazeButton.setVisible(false);
    }



    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void generateMaze(ActionEvent actionEvent) {
        utilities.ActivateNewMaze(actionEvent);
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        viewModel.solveMaze();
    }


    public void showAbout(ActionEvent actionEvent) {
        utilities.ActivateShowAbout(actionEvent);
    }

    public void exitGame(ActionEvent actionEvent){
        stage.close();
    }
    public void properties(ActionEvent actionEvent){
        utilities.ActivateProperties(actionEvent);
    }
    public void showHelp(ActionEvent actionEvent){
        utilities.ActivateHelp(actionEvent);
    }

    public void openFile(ActionEvent actionEvent) {
        utilities.ActivateOpenFile(actionEvent);
    }
    public void newMaze(ActionEvent actionEvent) {
        utilities.ActivateNewMaze(actionEvent);
    }
    public void saveFile(ActionEvent actionEvent) {
        utilities.ActivateSaveFile(actionEvent);
    }
    public void keyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            case "Wall" -> utilities.playSound("Wall");
            case "Finish" -> utilities.playSound("Finish");
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void mazeSolved() {
        mazeDisplayer.drawSolution(viewModel.getSolution());
        solveMazeButton.setVisible(false);
    }

    private void playerMoved() {
        setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        double x = stage.getWidth()-80 > stage.getHeight()?stage.getHeight():stage.getWidth()*.9;
        mazeDisplayer.setWidth(x);
        mazeDisplayer.setWidth(x);

        mazeDisplayer.SetMazeBehaviour(new IMazeBehaviour() {
            @Override
            public void DrawPlayerIcon(GraphicsContext graphicsContext, double x, double y, double cellWidth, double cellHeight) {
                Image playerImage = null;
                try {
                    playerImage = new Image(new FileInputStream(utilities.GetPlayerImageFileName()),
                         50,50,true,true );

                } catch (FileNotFoundException e) {
                    System.out.println("There is no player image file");
                }
                if (playerImage == null)
                    graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                else
                    graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
            }
        });
        mazeDisplayer.drawMaze(viewModel);
        solveMazeButton.setVisible(true);
    }
}

