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
import model.Gameitems.Bullet;
import model.Gameitems.Target;
import model.User;
import view.GameLauncher;
import view.Main;

import java.util.ArrayList;

public class ShootBullet extends Transition {
    private final Pane pane;
    private final Bullet bullet;
    private final double speed = 1;
    private final int duration = 100;
    private Game game;
    private ArrayList<Target> updateTargets;

    public ShootBullet(Bullet bullet) {
        User.getLoggedInUser().getLastGame().addTOAllShots();
        this.pane = GameLauncher.pane;
        this.game = User.getLoggedInUser().getLastGame();
        this.bullet = bullet;
        this.setCycleDuration(Duration.millis(duration));
        this.setCycleCount(-1);
        this.game = User.getLoggedInUser().getLastGame();
    }

    @Override
    protected void interpolate(double v) {
        double y = bullet.getY() + speed;
        if (y >= 450) {
            Timeline timeline = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
                pane.getChildren().remove(bullet);
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            bullet.setFill(new ImagePattern(new Image(
                    getClass().getResource("/IMAGES/exp1.gif").toExternalForm())));
            this.stop();

        }
        bullet.setY(y);
        updateTargets = new ArrayList<>();
        updateTargets.addAll(game.getTargets());
        for (Target target : game.getTargets()) {
            if (target.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                User.getLoggedInUser().getLastGame().addTOSuccessfulShots();
                Media media = new Media(Main.class.getResource("/MUSICS/littleShot.mp3").toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                game.addTOKills(target.killScore);
                GameController.killLabel.set("" + game.getTotalKill());
                BurningAnimation explosionAnimation = new BurningAnimation(pane, target);
                explosionAnimation.play();
                pane.getChildren().remove(bullet);
                this.stop();
                updateTargets.remove(target);
            }
        }
        game.setTargets(updateTargets);
    }

}
