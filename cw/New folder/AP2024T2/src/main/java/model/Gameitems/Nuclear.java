package model.Gameitems;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Nuclear extends Target {
    public static final double WIDTH = 300;
    public static final double HEIGHT = 300;

    public Nuclear(Plane plane) {
        super(WIDTH, HEIGHT);
        setX(plane.getCenterX() + Plane.WIDTH / 2);
        setY(plane.getCenterY() + Plane.HEIGHT / 2 - 2);
        this.setFill(new ImagePattern(
                new Image(Cluster.class.getResource("/IMAGES/nuclear.png").toExternalForm())));
    }
}

