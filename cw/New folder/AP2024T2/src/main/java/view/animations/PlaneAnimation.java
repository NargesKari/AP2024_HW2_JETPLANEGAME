package view.animations;

import controller.AvatarController;
import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Game;
import model.Gameitems.Plane;
import model.Gameitems.Tank;
import model.Gameitems.Target;
import model.User;
import view.GameLauncher;

import java.util.ArrayList;

public class PlaneAnimation extends Transition {
    private final Plane plane;
    private final Pane pane;
    private final int duration = 100;
    private double speed;
    private Game game;

    public PlaneAnimation(Plane plane) {
        this.plane = plane;
        this.pane = GameLauncher.pane;
        speed = 1;
        game = User.getLoggedInUser().getLastGame();
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(duration));
    }

    @Override
    protected void interpolate(double v) {
        double x = plane.getCenterX() + speed;
        if (x >= 800) x = -100;
        plane.setCenterX(x);
        plane.setOnKeyPressed(event -> {
            System.out.println(event.toString());
            if (event.getCode().equals(User.getLoggedInUser().getSetting().getUp())) {
                plane.setCenterY(Math.max(plane.getCenterY() - 10, 10));
            } else if (event.getCode().equals(User.getLoggedInUser().getSetting().getDown())) {
                plane.setCenterY(Math.min(plane.getCenterY() + 10, 400));
            } else if (event.getCode().equals(User.getLoggedInUser().getSetting().getLeft())) {
                plane.setCenterX(plane.getCenterX() - 10);
            } else if (event.getCode().equals(User.getLoggedInUser().getSetting().getRight())) {
                plane.setCenterX(plane.getCenterX() + 10);
            } else if (event.getCode().equals(User.getLoggedInUser().getSetting().getShoot())) {
                GameController.shoot(plane);
            } else if (event.getCode().equals(User.getLoggedInUser().getSetting().getNuclearRelease())) {
                GameController.nuclear(plane);
            } else if (event.getCode().equals(User.getLoggedInUser().getSetting().getClusterRelease())) {
                GameController.cluster(plane);
            } else if (event.getCode().equals(User.getLoggedInUser().getSetting().getFreeze())) {
                GameController.freeze();
            } else if (event.getCode().equals(KeyCode.P)) {
                GameController.endWave();
            } else if (event.getCode().equals(KeyCode.G)) {
                game.setCountNuclear(game.getCountNuclear() + 1);
                GameController.nuclearLabel.set("" + game.getCountNuclear());
            } else if (event.getCode().equals(KeyCode.CONTROL)) {
                game.setCountCluster(game.getCountCluster() + 1);
                GameController.clusterLabel.set("" + game.getCountCluster());
            } else if (event.getCode().equals(KeyCode.T)) {
                ArrayList<Target> targets = game.getTargets();
                Target tank = new Tank(true);
                targets.add(tank);
                pane.getChildren().add(tank);
                if (tank.circle != null) pane.getChildren().add(tank.circle);
            } else if (event.getCode().equals(KeyCode.H)) {
                game.setHp(game.getHp() + 100);
                GameController.hpLabel.set("" + game.getHp());
            }
        });
        if (GameController.freezeProgress.get() < 1)
            GameController.freezeProgress.set(GameController.freezeProgress.get() + 0.0005);
        if (game.getTargets().size() == 0 && !GameController.gameIsFinished)
            GameController.endWave();
        if (plane.isBurning) {
            Rectangle fire = new Rectangle(80, 150);
            fire.setFill(new ImagePattern(new Image(PlaneAnimation.class.getResource("/IMAGES/fire.gif")
                    .toExternalForm())));
            fire.setX(plane.getCenterY());
            fire.setY(plane.getCenterY());
            pane.getChildren().add(fire);
            this.stop();
        }
    }

}
