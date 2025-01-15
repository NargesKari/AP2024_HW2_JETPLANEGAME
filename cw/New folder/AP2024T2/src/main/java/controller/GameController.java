package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.Game;
import model.Gameitems.*;
import model.User;
import view.GameLauncher;
import view.Main;
import view.ProfileMenu;
import view.animations.ReleasingCluster;
import view.animations.ReleasingNuclear;
import view.animations.ShootBullet;

import java.util.ArrayList;
import java.util.Collections;

public class GameController {
    public static StringProperty warningLabel = new SimpleStringProperty("");
    public static StringProperty killLabel = new SimpleStringProperty("0");
    public static StringProperty waveLabel = new SimpleStringProperty("wave: ");
    public static StringProperty nuclearLabel = new SimpleStringProperty("0");
    public static StringProperty clusterLabel = new SimpleStringProperty("0");
    public static StringProperty hpLabel = new SimpleStringProperty("0");
    public static StringProperty endWaveLabel = new SimpleStringProperty("");
    public static DoubleProperty freezeProgress = new SimpleDoubleProperty(0.0);
    public static boolean gameIsFinished;
    private static Pane pane;
    private static Game game;
    public Label warning;
    public Label kill;
    public Label wave;
    public Label endWave;
    public Label nuclearCount;
    public Label clusterCount;
    public Label accuracy;
    public Label hp;
    public ProgressBar freezeBar;

    public static void shoot(Plane plane) {
        Pane pane = GameLauncher.pane;
        Bullet bullet = new Bullet(plane.getCenterX(), plane.getCenterY());
        int planeIndex = pane.getChildren().indexOf(plane);
        pane.getChildren().add(planeIndex, bullet);
        ShootBullet shootBullet = new ShootBullet(bullet);
        shootBullet.play();
    }

    public static void nuclear(Plane plane) {
        if (game.getCountNuclear() <= 0)
            return;
        Pane pane = GameLauncher.pane;
        Nuclear nuclear = new Nuclear(plane);
        int planeIndex = pane.getChildren().indexOf(plane);
        pane.getChildren().add(planeIndex, nuclear);
        game.setCountNuclear(game.getCountNuclear() - 1);
        GameController.nuclearLabel.set("" + game.getCountNuclear());
        ReleasingNuclear releasingNuclear = new ReleasingNuclear(pane, game, nuclear);
        releasingNuclear.play();
    }

    public static void cluster(Plane plane) {
        if (game.getCountCluster() <= 0)
            return;
        Pane pane = GameLauncher.pane;
        Cluster cluster = new Cluster(plane);
        for (int i = 0; i < 5; i++) {
            pane.getChildren().add(cluster.shots.get(i));
            game.addTOAllShots();
        }
        game.setCountCluster(game.getCountCluster() - 1);
        GameController.clusterLabel.set("" + game.getCountCluster());
        ReleasingCluster releasingCluster = new ReleasingCluster(pane, game, cluster);
        releasingCluster.play();
    }

    public static void freeze() {
        if (freezeProgress.get() < 1.0)
            return;
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.2);
        GameLauncher.setColor(colorAdjust);
        game.getTargetsAnimation().forEach(Animation::stop);
        freezeProgress.set(0.0);
    }

    public static void update(Game game, Pane pane) {
        GameController.pane = pane;
        if (game.getTargets().size() == 0) {
            if (game.getWave() == 1)
                setWave(game, 3, 3, 1, 1, 1, 0, 0);
            else if (game.getWave() == 2)
                setWave(game, 3, 2, 2, 1, 3, 2, 0);
            else
                setWave(game, 4, 3, 1, 2, 2, 3, 1);
        }
        game.getTargets().forEach(target -> {
            pane.getChildren().add(target);
            if (target.circle != null)
                pane.getChildren().add(target.circle);
        });
    }

    public static void setWave(Game game, int tanks, int trucks, int building, int trees, int bunker, int circledTank,
                               int migs) {
        ArrayList<Target> targets = new ArrayList<>();
        for (int i = 0; i < tanks; i++) {
            targets.add(new Tank(false));
        }
        for (int i = 0; i < trucks; i++) {
            targets.add(new Truck());
        }
        for (int i = 0; i < building; i++) {
            targets.add(new Building());
        }
        for (int i = 0; i < bunker; i++) {
            targets.add(new Bunker());
        }
        for (int i = 0; i < trees; i++) {
            targets.add(new Tree());
        }
        for (int i = 0; i < circledTank; i++) {
            targets.add(new Tank(true));
        }
        for (int i = 0; i < migs; i++) {
            Mig mig = new Mig();
            pane.getChildren().add(mig);
            pane.getChildren().add(mig.circle);
        }
        Collections.sort(targets, (r1, r2) -> Double.compare(r1.getY(), r2.getY()));
        game.setTargets(targets);
    }

    public static void endWave() {
        GameLauncher.setColor(User.getLoggedInUser().getSetting().getColour());
        pane = GameLauncher.pane;
        if (game.getWave() < 3) {
            game.setWave((game.getWave() + 1));
            waveLabel.set("Wave: " + game.getWave());
            endWaveLabel.set("Your accuracy in last wave was "
                    + ((int) Math.floor((double) (game.getSuccessfulShots() * 100) / game.getAllShots())));
            Timeline timeline = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), event -> {
                endWaveLabel.set("");
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            game.getTargets().forEach(target -> {
                pane.getChildren().remove(target);
                if (target.circle != null)
                    pane.getChildren().remove(target.circle);
            });
            game.setTargets(new ArrayList<>());
            update(game, pane);
        } else endGame();
    }

    public static void saveGameResult() {
        User user = User.getLoggedInUser();
        user.setTotalKill(user.getTotalKill() + game.getTotalKill());
        user.setLastGameWave(game.getWave());
        user.setDifficultyScore((game.getTotalKill() * user.getSetting().getDifficulty()) + user.getDifficultyScore());
        user.setAllShots(user.getAllShots() + game.getAllShots() - 1);
        user.setSuccessfulShots(user.getSuccessfulShots() + game.getSuccessfulShots());
        user.setAccuracy((int) (Math.floor((double) (user.getSuccessfulShots() * 100) / user.getAllShots())));
    }

    public static void endGame() {
        gameIsFinished = true;
        game.getTargetsAnimation().forEach(Animation::stop);
        game.getTargets().forEach(target -> {
            if (target.circle != null)
                target.circle.animation.stop();
        });
        GameLauncher.setColor(User.getLoggedInUser().getSetting().getColour());
        game.setWin(game.getHp() != 0);
        saveGameResult();
        try {
            GameLauncher.showGameResult(game);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static BackgroundImage createBackgroundImage() {
        String result = game.isWin() ? "winner" : "lose";
        Image image = new Image(Main.class.getResource("/IMAGES/" + result + "Logo.gif").toExternalForm(),
                500, 250, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    public static void planeAttacked() {
        if (gameIsFinished)
            return;
        game.setHp(game.getHp() - 1);
        hpLabel.set("" + game.getHp());
        if (game.getHp() == 0) {
            GameLauncher.plane.isBurning = true;
            endGame();
        }

    }

    public void initialize() {
        game = User.getLoggedInUser().getLastGame();
        kill.setText("" + game.getTotalKill());
        if (!gameIsFinished) {
            killLabel.set("" + game.getTotalKill());
            waveLabel.set("Wave: " + game.getWave());
            nuclearLabel.set("" + game.getCountNuclear());
            clusterLabel.set("" + game.getCountCluster());
            hpLabel.set("" + game.getHp());
            warningLabel.set("");
            freezeProgress.set(0.0);
            warning.textProperty().bind(warningLabel);
            kill.textProperty().bind(killLabel);
            wave.textProperty().bind(waveLabel);
            nuclearCount.textProperty().bind(nuclearLabel);
            clusterCount.textProperty().bind(clusterLabel);
            hp.textProperty().bind(hpLabel);
            endWave.textProperty().bind(endWaveLabel);
            freezeBar.progressProperty().bind(freezeProgress);
            playAnimation();
        }
        try {
            accuracy.setText("" + (int) Math.floor((double) (game.getSuccessfulShots() * 100) / game.getAllShots()));
        } catch (Exception e) {
            System.out.println(e.getMessage() + "here");
        }
    }

    public void back(MouseEvent mouseEvent) {
        GameLauncher.littleStage.close();
        if (gameIsFinished) User.getLoggedInUser().setLastGame(null);
        ProfileMenu.back();
    }

    public void pause(MouseEvent mouseEvent) {
        game.getTargetsAnimation().forEach(Animation::stop);
        game.getTargets().forEach(target -> {
            if (target.circle != null)
                target.circle.animation.stop();
        });
        GameLauncher.plane.getPlaneAnimation().stop();
        GameLauncher.openPauseMenu();
    }

    public void playAnimation() {
        try {
            game.getTargetsAnimation().forEach(Animation::play);
            GameLauncher.plane.getPlaneAnimation().play();
            game.getTargets().forEach(target -> {
                if (target.circle != null)
                    target.circle.animation.play();
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
