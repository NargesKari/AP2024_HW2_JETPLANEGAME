package model.Gameitems;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {
    public static final double WIDTH = 10;
    public static final double HEIGHT = 10;

    public Bullet(double x, double y) {
        super(WIDTH, HEIGHT);
        setX(x + Plane.WIDTH / 2);
        setY(y + Plane.HEIGHT / 2 - 2);
        this.setFill(new ImagePattern(
                new Image(Cluster.class.getResource("/IMAGES/bullet.png").toExternalForm())));
    }
}
