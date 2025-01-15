package view.animations;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Game;
import model.Gameitems.Cluster;
import model.Gameitems.Target;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.Arrays;

public class ReleasingCluster extends Transition {
    private final Game game;
    private final Pane pane;
    private final Cluster cluster;
    private final double[] speeds = new double[]{-0.7, -0.35, 0, 0.35, 0.7};
    private final int duration = 100;
    private ArrayList<Target> updateTargets;

    public ReleasingCluster(Pane pane, Game game, Cluster cluster) {
        this.pane = pane;
        this.game = game;
        this.cluster = cluster;
        this.setCycleDuration(Duration.millis(duration));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        for (int i = 0; i < 5; i++) {
            Rectangle shot = cluster.shots.get(i);
            shot.setX(shot.getX() - speeds[i]);
            double y = shot.getY() + 1;
            shot.setY(y);
            updateTargets = new ArrayList<>();
            updateTargets.addAll(game.getTargets());
            for (Target target : game.getTargets()) {
                if (target.getBoundsInParent().intersects(shot.getBoundsInParent())) {
                    User.getLoggedInUser().getLastGame().addTOSuccessfulShots();
                    Media media = new Media(Main.class.getResource("/MUSICS/bomb.mp3").toExternalForm());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();

                    game.addTOKills(target.killScore);
                    GameController.killLabel.set("" + game.getTotalKill());
                    BurningAnimation explosionAnimation = new BurningAnimation(pane, target);
                    explosionAnimation.play();
                    updateTargets.remove(target);
                }
            }
            game.setTargets(updateTargets);
            if (y >= 451) {
                Timeline timeline = new Timeline();
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
                    pane.getChildren().remove(shot);
                });
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();
                shot.setFill(new ImagePattern(new Image(
                        getClass().getResource("/IMAGES/exp1.gif").toExternalForm())));
                this.stop();
            }
        }
    }
}
