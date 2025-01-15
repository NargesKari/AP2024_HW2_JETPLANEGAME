package model.Gameitems;

import controller.AvatarController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.Random;

public class Building extends Target {
    public static final double WIDTH = 90;
    public static final double HEIGHT = 90;


    public Building() {
        super(WIDTH, HEIGHT);
        super.killScore=2;
        super.bonusType="nuclearbonus";
        Random random = new Random();
        setX(random.nextInt(700));
        random = new Random();
        setY(350+random.nextInt(70));
        setFill(new ImagePattern(getRandomImage()));
    }

    public static Image getRandomImage() {
        Random random = new Random();
        return new Image(AvatarController.class.getResource("/IMAGES/building" + (random.nextInt(5) + 1)
                + ".png").toExternalForm(), 150, 150, false,
                false);
    }
}
