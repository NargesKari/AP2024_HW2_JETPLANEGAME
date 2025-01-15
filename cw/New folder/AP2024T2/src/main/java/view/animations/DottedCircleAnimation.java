package view.animations;

import controller.GameController;
import controller.SettingMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Gameitems.DottedCircle;
import model.Gameitems.Plane;
import model.Gameitems.Target;
import model.User;
import view.GameLauncher;

public class DottedCircleAnimation extends Transition {
    private final DottedCircle circle;
    private final Target dad;
    private final Plane plane;
    private final int duration = 100;

    private final SettingMenuController helper = new SettingMenuController();

    public DottedCircleAnimation(DottedCircle circle) {
        User.getLoggedInUser().getLastGame().getTargetsAnimation().add(this);
        this.circle = circle;
        this.dad = circle.getDad();
        this.plane = GameLauncher.plane;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(duration));
    }

    @Override
    protected void interpolate(double v) {
        circle.setRadius(User.getLoggedInUser().getSetting().getDifficulty() * 100);
        circle.setCenterX(dad.getX() + 35);
        circle.setCenterY(dad.getY() + 35);
        if (circle.getBoundsInParent().intersects(plane.getBoundsInParent())) {
            GameController.planeAttacked();
            System.out.println(circle.getLayoutX()+"..."+ plane.getLayoutX());
        }
    }
}
