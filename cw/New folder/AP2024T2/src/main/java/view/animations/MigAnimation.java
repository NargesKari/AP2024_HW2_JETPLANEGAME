package view.animations;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;
import model.Gameitems.Mig;
import model.Setting;
import model.User;
import view.GameLauncher;

public class MigAnimation extends Transition {
    private final Mig mig;
    private final Setting setting;
    private int duration = 12;
    private double time;
    private int direction;

    public MigAnimation(Mig mig) {
        this.mig = mig;
        this.setting = User.getLoggedInUser().getSetting();
        this.time = 12;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(duration));
        User.getLoggedInUser().getLastGame().getTargetsAnimation().add(this);
    }

    @Override
    protected void interpolate(double v) {
        if (setting.getDifficulty() == 2) {
            time = 9;
        } else if (setting.getDifficulty() == 3) {
            time = 6;
        }
        mig.setX(mig.getX() - 0.7);
        if (mig.getX() <= -100 && mig.getX() >= -101) {
            Timeline timeline = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(time), event -> {
                mig.setX(900);
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        } else if (mig.getX() >= 830)
            GameController.warningLabel.set("Mig is Coming!!");
        else if (mig.getX() <= 600)
            GameController.warningLabel.set("");
    }
}
