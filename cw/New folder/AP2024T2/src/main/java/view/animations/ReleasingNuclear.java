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
import javafx.util.Duration;
import model.Game;
import model.Gameitems.Cluster;
import model.Gameitems.Nuclear;
import model.Gameitems.Target;
import model.User;
import view.GameLauncher;
import view.Main;

import java.util.ArrayList;

public class ReleasingNuclear extends Transition {
    private final Game game;
    private final Pane pane;
    private final Nuclear nuclear;
    private final double speed = 2;
    private final int duration = 100;
    private ArrayList<Target> updateTargets;

    public ReleasingNuclear(Pane pane, Game game, Nuclear nuclear) {
        User.getLoggedInUser().getLastGame().addTOAllShots();
        this.pane = pane;
        this.game = game;
        this.nuclear = nuclear;
        this.setCycleDuration(Duration.millis(duration));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = nuclear.getY() + speed;
        updateTargets = new ArrayList<>();
        updateTargets.addAll(game.getTargets());
        boolean isHit = false;
        for (Target target : game.getTargets()) {
            if (target.getBoundsInParent().intersects(nuclear.getBoundsInParent())) {
                if (!isHit) {
                    User.getLoggedInUser().getLastGame().addTOSuccessfulShots();
                    Media media = new Media(Main.class.getResource("/MUSICS/bomb.mp3").toExternalForm());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                }
                game.addTOKills(target.killScore);
                GameController.killLabel.set("" + game.getTotalKill());
                BurningAnimation explosionAnimation = new BurningAnimation(pane, target);
                explosionAnimation.play();
                isHit = true;
                updateTargets.remove(target);
            }
        }
        if (isHit && v > 0.5) {
            this.stop();
            pane.getChildren().remove(nuclear);
        }
        game.setTargets(updateTargets);
        if (y >= 350) {
            Timeline timeline = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
                pane.getChildren().remove(nuclear);
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            nuclear.setFill(new ImagePattern(new Image(
                    getClass().getResource("/IMAGES/exp1.gif").toExternalForm())));
            this.stop();
        }
        nuclear.setY(y);
    }
}
