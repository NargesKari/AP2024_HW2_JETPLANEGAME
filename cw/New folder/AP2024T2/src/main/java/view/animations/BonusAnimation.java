package view.animations;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Game;
import model.Gameitems.Bonus;
import model.Gameitems.Plane;
import model.User;
import view.GameLauncher;
import view.Main;

public class BonusAnimation extends Transition {
    private final Plane plane;
    private final Pane pane;
    private final int duration = 100;
    private double xSpeed;
    private double ySpeed;
    private Bonus bonus;

    public BonusAnimation(Bonus bonus) {
        this.plane = GameLauncher.plane;
        this.pane = GameLauncher.pane;
        this.bonus = bonus;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(duration));
        this.xSpeed = 0.25;
        this.ySpeed = 0.5;
    }

    @Override
    protected void interpolate(double v) {
        double x = bonus.getX() - xSpeed;
        double y = bonus.getY() - ySpeed;
        if (x <= bonus.getXCenter() - 10 || x >= bonus.getXCenter() + 10) {
            x = bonus.getX();
            xSpeed = -xSpeed;
        }
        if (y < -90) {
            pane.getChildren().remove(bonus);
        }
        if (bonus.getBoundsInParent().intersects(plane.getBoundsInParent())) {
            Game game = User.getLoggedInUser().getLastGame();
            if (bonus.getType().equals("nuclearbonus")) {
                game.setCountNuclear(game.getCountNuclear() + 1);
                GameController.nuclearLabel.set("" + game.getCountNuclear());
            } else {
                game.setCountCluster(game.getCountCluster() + 1);
                GameController.clusterLabel.set("" + game.getCountCluster());
            }
            pane.getChildren().remove(bonus);
            this.stop();
        }
        bonus.setX(x);
        bonus.setY(y);
    }
}
