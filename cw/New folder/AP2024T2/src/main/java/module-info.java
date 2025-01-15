module AP2024T2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    exports controller;
    opens controller to javafx.fxml;
    exports view;
    opens view to javafx.fxml;

}