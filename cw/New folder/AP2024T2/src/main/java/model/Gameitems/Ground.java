package model.Gameitems;

import controller.AvatarController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Game;

import java.util.Random;

public class Ground extends Rectangle {
    public static final double WIDTH = 800;
    public static final double HEIGHT = 90;

    public Ground() {
        super(WIDTH, HEIGHT);
        setX(0);
        setY(410);
        setFill(new ImagePattern(getRandomImage()));
    }
    public static Image getRandomImage() {
        Random random = new Random();
        return new Image(AvatarController.class.getResource("/IMAGES/ground" + (random.nextInt(2) + 1)
                + ".png").toExternalForm(), 800, 50, false,
                false);
    }
}
