package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;

public class ScoreTable extends Application {
    private Pane pane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        pane = new Pane();
        stage.setResizable(false);
        stage.centerOnScreen();
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        pane = fxmlLoader.load(Main.class.getResource("/FXML/ScoreTable.fxml"));
        pane.setBackground(new Background(createBackground()));
        pane.setEffect(User.getLoggedInUser().getSetting().getColour());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private BackgroundImage createBackground() {
        Image image = new Image(Main.class.getResource("/IMAGES/MainMenu.gif").toExternalForm(),
                800, 500, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }
}
