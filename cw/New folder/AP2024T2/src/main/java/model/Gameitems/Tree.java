package model.Gameitems;

import controller.AvatarController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Tree extends Target {
    public static final double WIDTH = 75;
    public static final double HEIGHT = 75;
    private static int numb;

    public Tree() {
        super(WIDTH, HEIGHT);
        Random random = new Random();
        setX(random.nextInt(700));
        random = new Random();
        setY(350+random.nextInt(70));
        setFill(new ImagePattern(getRandomImage()));
    }

    public static Image getRandomImage() {
        Random random = new Random();
        return new Image(AvatarController.class.getResource("/IMAGES/tree" + (random.nextInt(4) + 1)
                + ".png").toExternalForm(), 150, 150, false,
                false);
    }
}
