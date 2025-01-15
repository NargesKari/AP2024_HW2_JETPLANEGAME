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

public class Truck extends Target {
    public static final double WIDTH = 90;
    public static final double HEIGHT = 90;
    private static int numb;
    private CarsAnimation carsAnimation;

    public Truck() {
        super(WIDTH, HEIGHT);
        super.killScore = 3;
        Random random = new Random();
        setY(360 + random.nextInt(70));
        random = new Random();
        int x = random.nextInt(870);
        setX(x - 80);
        setFill(new ImagePattern(getRandomImage()));
        if (numb > 3) super.direction = -1;
        carsAnimation = new CarsAnimation(this);
        carsAnimation.play();
    }

    public static Image getRandomImage() {
        Random random = new Random();
        numb = random.nextInt(6) + 1;
        return new Image(AvatarController.class.getResource("/IMAGES/truck" + numb
                + ".png").toExternalForm(), 150, 150, false,
                false);
    }

}
