package view;

import controller.SettingMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {
    public static Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    public static void getData() {
        try {
            FileInputStream fileIn = new FileInputStream("data.txt");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            ArrayList<User> allUsers = (ArrayList<User>) objIn.readObject();
            int savedData2 = (int) objIn.readObject();
            System.out.println("Saved data 1: ");
            User.setAllUsers(allUsers);
            objIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setData() {
        String fileName = "data.txt";
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            ArrayList<User> allUsers = User.getAllUsers();
            objOut.writeObject(allUsers);
            objOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        Pane pane = FXMLLoader.load(Main.class.getResource("/FXML/IntroStage.fxml"));
        pane.setBackground(new Background(createBackgroundImage()));
        ImageView logo = new ImageView(new Image(Main.class.getResource("/IMAGES/SUTLogo.png").toExternalForm()));
        logo.setX(370);
        logo.setY(130);
        pane.getChildren().add(logo);
        Scene scene = new Scene(pane);
        stage.setTitle("Jet Plane");
        stage.getIcons().add(new Image(Main.class.getResource("/IMAGES/avatar1.png").toExternalForm()));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        SettingMenuController.playMusic();
        stage.show();
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2.6), event -> {
            try {
                new SigningMenu().start(Main.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Main.class.getResource("/IMAGES/IntroBackground.gif").toExternalForm(),
                800, 500, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

}
