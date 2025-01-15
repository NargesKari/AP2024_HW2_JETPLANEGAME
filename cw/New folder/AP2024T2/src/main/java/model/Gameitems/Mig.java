package model.Gameitems;

import controller.AvatarController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import view.animations.MigAnimation;

import java.util.Random;

public class Mig extends Target{
    public static final double WIDTH = 70;
    public static final double HEIGHT = 70;
    private static int numb;
    private MigAnimation animation;
    private int randomX;

    public Mig() {
        super(WIDTH, HEIGHT);
        super.killScore = 7;
        Random random = new Random();
        setY(random.nextInt(350));
        random = new Random();
        setFill(new ImagePattern(getRandomImage()));
        setX(900);
        super.circle = new DottedCircle(this);
        animation = new MigAnimation(this);
        animation.play();
    }

    private static Image getRandomImage() {
        Random random = new Random();
        numb = random.nextInt(3) + 1;
        return new Image(AvatarController.class.getResource("/IMAGES/mig" + numb
                + ".png").toExternalForm(), 150, 150, false,
                false);
    }
}
