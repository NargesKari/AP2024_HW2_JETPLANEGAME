package model.Gameitems;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animations.BonusAnimation;

public class Bonus extends Rectangle {
    private double xCenter;
    private String type;
    BonusAnimation bonusAnimation;

    public Bonus(Target target, String type) {
        super(50, 50);
        setX(target.getX() + 5);
        setY(target.getY() + 5);
        this.xCenter = this.getX();
        this.type = type;
        this.setFill(new ImagePattern(new Image(
                getClass().getResource("/IMAGES/" + type + ".png").toExternalForm())));
        bonusAnimation = new BonusAnimation(this);
        bonusAnimation.play();
    }

    public double getXCenter() {
        return xCenter;
    }

    public String getType() {
        return type;
    }
}
