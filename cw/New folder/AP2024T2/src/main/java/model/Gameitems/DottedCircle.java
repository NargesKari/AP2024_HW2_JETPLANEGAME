package model.Gameitems;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.User;
import view.animations.DottedCircleAnimation;

public class DottedCircle extends Circle {
    public DottedCircleAnimation animation;
    private Target dad;

    public DottedCircle(Target dad) {
        super(User.getLoggedInUser().getSetting().getDifficulty() * 70);
        this.setCenterX(dad.getX() + 35);
        this.setCenterY(dad.getY() + 35);
        this.dad = dad;
        this.setFill(new ImagePattern(new Image(getClass().
                getResource("/IMAGES/dottedCircle.png").toExternalForm())));
        animation = new DottedCircleAnimation(this);
        animation.play();
    }

    public Target getDad() {
        return dad;
    }
}
