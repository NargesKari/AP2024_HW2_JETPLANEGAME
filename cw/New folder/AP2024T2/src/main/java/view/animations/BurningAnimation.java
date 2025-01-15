package view.animations;

import controller.AvatarController;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Gameitems.Bonus;
import model.Gameitems.Bunker;
import model.Gameitems.Plane;
import model.Gameitems.Target;
import model.User;

public class BurningAnimation extends Transition {
    private final Target target;
    private final Pane pane;

    public BurningAnimation(Pane pane, Target target) {
        User.getLoggedInUser().getLastGame().getTargetsAnimation().add(this);
        this.pane = pane;
        this.target = target;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(1000));
        String bonusType = target.bonusType;
        if (!bonusType.equals("")) {
            pane.getChildren().add(new Bonus(target, bonusType));
        }
    }

    @Override
    protected void interpolate(double v) {
        if (0 <= v && v <= 0.5) {
            target.setFill(new ImagePattern(new Image(
                    BurningAnimation.class.getResource("/IMAGES/burning.gif").toExternalForm())));
        } else
            this.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    pane.getChildren().remove(target);
                    if (target.circle != null) {
                        pane.getChildren().remove(target.circle);
                        target.circle.animation.stop();
                    }
                }
            });
    }
}
