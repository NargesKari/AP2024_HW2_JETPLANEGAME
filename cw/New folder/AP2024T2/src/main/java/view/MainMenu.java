package view;

import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;

public class MainMenu extends Application {
    private static Alert alert;
    private Pane pane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Main Menu");
        FXMLLoader fxmlLoader = new FXMLLoader();
        pane = fxmlLoader.load(Main.class.getResource("/FXML/MainMenu.fxml"));
        pane.setBackground(new Background(createBackgroundImage()));
        ImageView avatar = new ImageView(User.getLoggedInUser().getAvatar());
        avatar.setFitHeight(150);
        avatar.setFitWidth(150);
        pane.getChildren().add(avatar);
        pane.setEffect(User.getLoggedInUser().getSetting().getColour());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Main.class.getResource("/IMAGES/MainMenu.gif").toExternalForm(),
                800, 500, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    public void showUsername() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your Username");
        alert.setHeaderText("your username is " + User.getLoggedInUser().getUsername());
        alert.setContentText("Dear, if you have Alzheimer's, go to the doctor");
        alert.show();
    }


    public void startNewGame() {
        MainMenuController.setGame(true);
        try {
            new GameLauncher().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startLastGame() {
        if (!MainMenuController.setGame(false).isSuccessful()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(MainMenuController.setGame(false).getMessage());
            alert.setContentText("Start new Game!!");
            alert.show();
            return;
        }
        try {
            new GameLauncher().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToProfileMenu() {
        try {
            new ProfileMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToSettingMenu() {
        try {
            new SettingMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showScoreTable() {
        try {
            new ScoreTable().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        Main.stage.close();
    }
}
