package model;

import javafx.animation.Transition;
import model.Gameitems.Target;

import java.io.Serializable;
import java.util.ArrayList;

public class Game  implements Serializable {
    private int wave = 1;
    private int hp = 1000;
    private int totalKill = 0;
    private int countCluster = 0;
    private int countNuclear = 0;
    private int allShots = 1;
    private int successfulShots = 0;
    private boolean win;
    private ArrayList<Transition> targetsAnimation = new ArrayList<>();
    private ArrayList<Target> targets = new ArrayList<>();

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Target> targets) {
        this.targets = targets;
    }

    public int getTotalKill() {
        return totalKill;
    }

    public void addTOKills(int count) {
        this.totalKill += count;
    }

    public int getAllShots() {
        return allShots;
    }

    public void addTOAllShots() {
        this.allShots += 1;
    }

    public int getSuccessfulShots() {
        return successfulShots;
    }

    public void addTOSuccessfulShots() {
        this.successfulShots += 1;
    }

    public int getCountCluster() {
        return countCluster;
    }

    public void setCountCluster(int countCluster) {
        this.countCluster = countCluster;
    }

    public int getCountNuclear() {
        return countNuclear;
    }

    public void setCountNuclear(int countNuclear) {
        this.countNuclear = countNuclear;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setTargetsAnimation(ArrayList<Transition> targetsAnimation) {
        this.targetsAnimation = targetsAnimation;
    }

    public ArrayList<Transition> getTargetsAnimation() {
        return targetsAnimation;
    }
}
