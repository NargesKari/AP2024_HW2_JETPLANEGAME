package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import view.AvatarMenu;

import java.io.File;
import java.util.Random;

public class AvatarController {
    public Label dragLabel;
    @FXML
    private Button uploadButton;

    public static Image getRandomImage() {
        Random random = new Random();
        return new Image(AvatarController.class.getResource("/IMAGES/avatar" + (random.nextInt(3) + 1)
                + ".png").toExternalForm(), 150, 150, false,
                false);
    }

    @FXML
    private void initialize() {
        dragLabel.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            } else {
                event.consume();
            }
        });
        dragLabel.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {
                event.getDragboard().getFiles().forEach(file -> {
                    AvatarMenu.showSuccessAlert();
                    User.getLoggedInUser().setAvatar(new Image(file.getPath()));
                });
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("");
            Stage stage = (Stage) uploadButton.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                User.getLoggedInUser().setAvatar(new Image(selectedFile.getPath()));
                AvatarMenu.showSuccessAlert();
            }
        });
    }

    public void back(MouseEvent mouseEvent) {
        AvatarMenu.back();
    }

    public void setAvatar1(MouseEvent mouseEvent) {
        User.getLoggedInUser().setAvatar(new Image(AvatarController.class.getResource("/IMAGES/avatar1.png")
                .toExternalForm()));
        AvatarMenu.showSuccessAlert();
    }

    public void setAvatar2(MouseEvent mouseEvent) {
        User.getLoggedInUser().setAvatar(new Image(AvatarController.class.getResource("/IMAGES/avatar2.png")
                .toExternalForm()));
        AvatarMenu.showSuccessAlert();
    }

    public void setAvatar3(MouseEvent mouseEvent) {
        User.getLoggedInUser().setAvatar(new Image(AvatarController.class.getResource("/IMAGES/avatar3.png")
                .toExternalForm()));
        AvatarMenu.showSuccessAlert();
    }
}
