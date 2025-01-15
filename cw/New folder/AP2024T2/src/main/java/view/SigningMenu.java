package view;

import controller.MainMenuController;
import controller.SigningMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Result;

public class SigningMenu extends Application {
    private static Result result;
    private static Alert alert;
    public TextField username;
    @FXML
    private PasswordField password;
    private Pane pane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.centerOnScreen();
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        pane = fxmlLoader.load(Main.class.getResource("/FXML/SigningMenu.fxml"));
        pane.setBackground(new Background(createBackgroundImage()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Signing Menu");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Main.class.getResource("/IMAGES/SigningMenuBackground.png").toExternalForm(),
                800, 500, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    public void register(MouseEvent mouseEvent) {
        result = SigningMenuController.register(username.getText(), password.getText(), false);
        if (result.isSuccessful()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registered Successfully");
            alert.setHeaderText(result.getMessage());
            alert.setContentText("Happy new account! login and Play :)");
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Failed");
            alert.setHeaderText(result.getMessage());
            alert.setContentText("ops! Try again :(");
            alert.show();
        }
    }

    public void login(MouseEvent mouseEvent) {
        result = SigningMenuController.login(username.getText(), password.getText());
        if (!result.isSuccessful()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(result.getMessage());
            alert.setContentText("ops! Try again :(");
            alert.show();
            return;
        }
        try {
            new MainMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginAsGuest(MouseEvent mouseEvent) {
        SigningMenuController.register("<Guest>", "", true);
        SigningMenuController.login("<Guest>", "");
        try {
            new MainMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}