package model.Gameitems;

import controller.AvatarController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import view.animations.PlaneAnimation;

import java.util.Random;

public class Plane extends Circle {
    public static final double WIDTH = 90;
    public static final double HEIGHT = 90;
    public boolean isBurning=false;
    private PlaneAnimation planeAnimation;

    public Plane() {
        super(45);
        setCenterY(200);
        setFill(new ImagePattern(getRandomImage()));
        this.planeAnimation = new PlaneAnimation(this);
    }

    public static Image getRandomImage() {
        Random random = new Random();
        return new Image(AvatarController.class.getResource("/IMAGES/plain" + (random.nextInt(3) + 1)
                + ".png").toExternalForm(), 150, 150, false,
                false);
    }

    public PlaneAnimation getPlaneAnimation() {
        return planeAnimation;
    }

}
