package model.Gameitems;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Target extends Rectangle  implements Serializable {
    public int killScore = 0;
    public String bonusType="";
    public DottedCircle circle = null;
    public int direction=1;
    Target(double x, double y){
        super(x,y);
    }
}
