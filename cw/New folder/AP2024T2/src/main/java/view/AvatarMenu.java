package view;

import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;

public class AvatarMenu extends Application {
    private static ImageView avatar;
    private Pane pane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    public static void back() {
        try {
            new ProfileMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Avatar Menu");
        stage.setResizable(false);
        stage.centerOnScreen();
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        pane = fxmlLoader.load(Main.class.getResource("/FXML/AvatarMenu.fxml"));
        pane.setBackground(new Background(createBackgroundImage()));
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

    public static void showSuccessAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("Your Avatar Changed SuccessFully");
        alert.setContentText("See it in Main Menu");
        alert.show();
    }
}
