package model.Gameitems;

import controller.AvatarController;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.User;
import view.animations.CarsAnimation;

import java.util.Random;

public class Tank extends Target {
    public static final double WIDTH = 90;
    public static final double HEIGHT = 90;
    private static int numb;
    private CarsAnimation carsAnimation;
    private int randomX;

    public Tank(boolean hasCircle) {
        super(WIDTH, HEIGHT);
        super.killScore = 4;
        Random random = new Random();
        setY(360 + random.nextInt(70));
        random = new Random();
        this.randomX = random.nextInt(870);
        setFill(new ImagePattern(getRandomImage()));
        setX(randomX-80);
        if (numb < 8) super.direction = -1;
        if (hasCircle) super.circle = new DottedCircle(this);
        carsAnimation = new CarsAnimation(this);
        carsAnimation.play();
    }

    private static Image getRandomImage() {
        Random random = new Random();
        numb = random.nextInt(11) + 1;
        return new Image(AvatarController.class.getResource("/IMAGES/tank" + numb
                + ".png").toExternalForm(), 150, 150, false,
                false);
    }
}
