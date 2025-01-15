package view.animations;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Gameitems.Target;
import model.User;
import view.GameLauncher;

public class CarsAnimation extends Transition {
    private final Target car;
    private final Pane pane;
    private final int duration = 100;
    private double speed;
    private int direction;

    public CarsAnimation(Target car) {
        User.getLoggedInUser().getLastGame().getTargetsAnimation().add(this);
        this.car = car;
        this.pane = GameLauncher.pane;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(duration));
        direction=car.direction;
    }

    @Override
    protected void interpolate(double v) {
        speed = direction * 0.7 * User.getLoggedInUser().getSetting().getDifficulty();
        double x = car.getX() + speed;
        if (x >= 799 && direction > 0) x = -89;
        if (x <= -89 && direction < 0) x = 799;
        car.setX(x);
    }
}
