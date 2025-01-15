package model.Gameitems;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.ReleasingCluster;

import java.util.ArrayList;

public class Cluster extends Rectangle {
    public static final double WIDTH = 20;
    public static final double HEIGHT = 20;
    public ArrayList<Rectangle> shots = new ArrayList<>();

    public Cluster(Plane plane) {
        super(WIDTH, HEIGHT);
        for (int i = 0; i < 5; i++) {
            shots.add(new Rectangle(10,10));
            shots.get(i).setX(plane.getCenterX());
            shots.get(i).setY(plane.getCenterY());
            shots.get(i).setFill(new ImagePattern(
                    new Image(Cluster.class.getResource("/IMAGES/cluster.png").toExternalForm())));
        }
    }
}
