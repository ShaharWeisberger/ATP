package View;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Utilities {
    private TextField textField_mazeRows = new TextField();
    private TextField textField_mazeColumns = new TextField();
    private MyViewModel viewModel;
    private String playerImageFile = playerImageFile = "./resources/images/PlayerRound.jpg";

    public Utilities(MyViewModel viewModel1) {
        this.viewModel = viewModel1;

    }

    public String GetPlayerImageFileName(){
        return playerImageFile;
    }

    public void ActivateNewMaze(ActionEvent actionEvent) {
        try {

            Stage stage = new Stage();
            stage.setTitle("New Maze");

            VBox layout = new VBox();
            layout.setPadding(new Insets(10, 50, 50, 50));
            layout.setSpacing(10);
            HBox hBox1 = new HBox();
            HBox hBox2 = new HBox();
            HBox hBox3 = new HBox();

            Text text1 = new Text("Maze rows:      ");
            Text text2 = new Text("Maze columns: ");
            textField_mazeRows = new TextField();
            textField_mazeColumns = new TextField();
            textField_mazeRows.setText("50");
            textField_mazeColumns.setText("50");
            hBox1.getChildren().add(text1);
            hBox1.getChildren().add(textField_mazeRows);
            hBox2.getChildren().add(text2);
            hBox2.getChildren().add(textField_mazeColumns);

            layout.getChildren().add(hBox1);
            layout.getChildren().add(hBox2);

            Button button1 = new Button("Generate");
            Button button2 = new Button("Cancel");
            hBox3.getChildren().add(button1);
            hBox3.getChildren().add(button2);
            hBox3.setSpacing(50);
            layout.getChildren().add(hBox3);

            button1.setOnAction(new EventHandler() {

                @Override
                public void handle(Event event) {
                    try {
                        int rows = Integer.valueOf(textField_mazeRows.getText());
                        int cols = Integer.valueOf(textField_mazeColumns.getText());
                        if (rows>0&&rows<=100&&cols>0&&cols<=100) {
                            viewModel.generateMaze(rows, cols);
                            stage.close();
                        } else {
                            createAlert();
                        }
                    } catch (Exception e) {
                        createAlert();
                    }
                }
            });

            button2.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    stage.close();
                }
            });


            //     layout.getChildren().add(linesTextArea);
            stage.setScene(new Scene(layout, 300, 180));
            stage.show();

        } catch (Exception e) {
            System.out.println("Failed to create new maze");
        }
    }

    private void createAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Wrong column or rows number");
        alert.show();
    }


    public void ActivateProperties(ActionEvent actionEvent) {
        try {
            final ToggleGroup group = new ToggleGroup();

            Stage stage = new Stage();
            stage.setTitle("Player Icon Selection");
            VBox vBox = new VBox();
            HBox hBox = new HBox();

            vBox.setSpacing(20);
            vBox.setPadding(new Insets(10,10,10,10));
            stage.setScene(new Scene(hBox, 300, 200));

            RadioButton rb1 = new RadioButton("Ball");
            rb1.setUserData("BallPlayer.png");
            rb1.setToggleGroup(group);

            RadioButton rb2 = new RadioButton("Circle");
            rb2.setToggleGroup(group);
            rb2.setUserData("PlayerRound.jpg");

            RadioButton rb3 = new RadioButton("Triangle");
            rb3.setUserData("PlayerTriangle.png");

            vBox.getChildren().add(rb1);
            vBox.getChildren().add(rb2);
            vBox.getChildren().add(rb3);
            ImageView imageView = new ImageView();

            rb3.setToggleGroup(group);
            VBox vBox2 = new VBox();

            vBox.setSpacing(20);
            vBox2.getChildren().add(imageView);
            vBox2.setPadding(new Insets(35,10,10,10));

            hBox.getChildren().add(vBox);

            hBox.getChildren().add(vBox2);

            group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> ov,
                                    Toggle old_toggle, Toggle new_toggle) {

                    Image image = null;
                    try {
                        playerImageFile = "./resources/images/" + group.getSelectedToggle().getUserData().toString();
                        image = new Image(new FileInputStream(playerImageFile),50,50,true,true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageView.setImage(image);
                    viewModel.movePlayer(null);
                }
            });

            if (playerImageFile.contains(rb1.getUserData().toString())){
                rb1.setSelected(true);
                rb1.requestFocus();
            }
            else {
                if (playerImageFile.contains(rb2.getUserData().toString())){
                    rb2.setSelected(true);
                    rb2.requestFocus();
                } else {
                    rb3.setSelected(true);
                    rb3.requestFocus();
                }
            }
            stage.show();

        } catch (Exception e) {
            System.out.println("Properties failed");
        }
    }

    public void ActivateHelp(ActionEvent actionEvent){
        try {
            Stage stage = new Stage();
            stage.setTitle("Maze Help");

            VBox layout= new VBox();
            TextArea linesTextArea = new TextArea();
            InnerShadow is = new InnerShadow();
            is.setOffsetX(4.0f);
            is.setOffsetY(4.0f);

            Text t = new Text();
            t.setEffect(is);
            t.setX(20);
            t.setY(100);
            t.setText("Help");
            t.setFill(Color.YELLOW);
            t.setFont(Font.font(null, FontWeight.BOLD, 80));
            layout.getChildren().add(t);

            layout.getChildren().add(linesTextArea);
            stage.setScene(new Scene(layout, 450, 450));
            linesTextArea.setMinWidth(400);
            linesTextArea.clear();
            linesTextArea.appendText("File->New: Create new maze\n");
            linesTextArea.appendText("File->Load: Load maze from file\n");
            linesTextArea.appendText("File->Save: Save maze in file\n\n");
            linesTextArea.appendText("Properties->Player: Choose player icon\n\n");
            linesTextArea.appendText("Help: Show maze help\n\n");
            linesTextArea.appendText("Exit: Exit maze");

            stage.show();
        }
        catch (Exception e) {
            System.out.println("Failed to run help");
        }
    }

    public void ActivateOpenFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);

        //viewModel.generateMaze(100, 100);
        //Maze dummyMaze = new Maze();
        byte[] savedMazeBytes = new byte[0];

        try {
            InputStream in = new MyDecompressorInputStream(new FileInputStream(chosen));
            //savedMazeBytes = new byte[viewModel.getMaze().toByteArray().length];
            savedMazeBytes = new byte[10000];

            in.read(savedMazeBytes);
            in.close();
            viewModel.updateMaze(new Maze(savedMazeBytes));
        } catch (Exception e) {
            System.out.println("read file failed");
        }
    }

    public void ActivateSaveFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showSaveDialog(null);

        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(chosen));
            out.write(viewModel.getMaze().toByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("save file failed");
            //      var8.printStackTrace();
        }
    }

    public void ActivateShowAbout(ActionEvent actionEvent) {
        try {
            VBox vbox1 = new VBox();
            Image image1 = new Image(new FileInputStream("./resources/images/" + "shrek" + ".png"),
                    50,50,false, false);
            ImageView imageView1 = new ImageView(image1);
            Image image2 = new Image(new FileInputStream("./resources/images/" + "Donkey" + ".jpg"),
                    50,50,false, false);
            ImageView imageView2 = new ImageView(image2);
            Label l1 = new Label("Shahar Weisberger");
            Label l2 = new Label("Raz Ego");
            VBox vBox2 = new VBox();
            vBox2.getChildren().addAll(imageView1, imageView2);
            vbox1.setSpacing(30);
            vbox1.setPadding(new Insets(20,20,20,20));
            vbox1.getChildren().addAll(l1, l2);
            HBox hBox = new HBox();
            hBox.getChildren().addAll(vbox1,vBox2);
            Scene scene = new Scene(hBox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("About");
            stage.show();
        }
        catch (Exception e) {
            System.out.println("about failed");
        }
    }

    public void playSound(String soundName){
        File f;
        switch (soundName){
            case "Finish":
                f = new File("resources/sounds/finishSound.wav");
                break;
            case "Wall":
                f = new File("resources/sounds/warningBeep.wav");
                break;
            default:
                return;
        }
        String s = f.toURI().toString();
        System.out.println(s);
        Media m = new Media(f.toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
    }
}
