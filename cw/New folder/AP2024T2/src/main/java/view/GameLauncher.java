package view;

import controller.AvatarController;
import controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Game;
import model.Gameitems.*;
import model.User;

import java.io.IOException;
import java.util.Random;

public class GameLauncher extends Application {
    public static Pane pane;
    public static Plane plane;
    private Game game;
    public static Stage littleStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static void showGameResult(Game game) throws IOException {
        Stage stage = new Stage();
        pane = FXMLLoader.load(Main.class.getResource("/FXML/Result.fxml"));
        pane.setMinHeight(250);
        pane.setMaxHeight(250);
        pane.setMinWidth(500);
        pane.setMaxWidth(500);
        pane.setBackground(new Background(GameController.createBackgroundImage()));
        setColor(User.getLoggedInUser().getSetting().getColour());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        littleStage = stage;
    }

    public static void setColor(ColorAdjust color) {
        pane.setEffect(color);
    }

    public static void openPauseMenu() {
        try {
            new PauseMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        pane = new Pane();
        GameController.gameIsFinished = false;
        game = User.getLoggedInUser().getLastGame();
        stage.setResizable(false);
        stage.centerOnScreen();
        Main.stage = stage;
        pane = FXMLLoader.load(GameLauncher.class.getResource("/FXML/Game.fxml"));
        pane.setBackground(new Background(createBackgroundImage()));
        pane.getChildren().add(plane = new Plane());
        setColor(User.getLoggedInUser().getSetting().getColour());
        plane.getPlaneAnimation().play();
        Ground ground = new Ground();
        pane.getChildren().add(ground);
        ground.toBack();
        setSize(pane);
        GameController.update(game, pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        plane.requestFocus();
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private BackgroundImage createBackgroundImage() {
        Random random = new Random();
        Image image = new Image(AvatarController.class.getResource("/IMAGES/sky" + (random.nextInt(3) + 1)
                + ".gif").toExternalForm(), 800, 500, false,
                false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    private void setSize(Pane pane) {
        pane.setMinHeight(500);
        pane.setMaxHeight(500);
        pane.setMinWidth(800);
        pane.setMaxWidth(800);
    }
}
