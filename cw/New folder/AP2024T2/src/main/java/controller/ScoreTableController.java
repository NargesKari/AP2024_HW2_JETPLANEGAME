package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.User;
import view.ProfileMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreTableController {
    public static ArrayList<User> allUsers = User.getAllUsers();
    public Label username1;
    public Label username2;
    public Label username3;
    public Label username4;
    public Label username5;
    public Label username6;
    public Label username7;
    public Label username8;
    public Label username9;
    public Label username10;
    public Label score1;
    public Label score2;
    public Label score3;
    public Label score4;
    public Label score5;
    public Label score6;
    public Label score7;
    public Label score8;
    public Label score9;
    public Label score10;
    public Label wave1;
    public Label wave2;
    public Label wave3;
    public Label wave4;
    public Label wave5;
    public Label wave6;
    public Label wave7;
    public Label wave8;
    public Label wave9;
    public Label wave10;
    @FXML
    private ComboBox<String> comboBox;

    public void initialize() {
        sortByKill();
    }

    public void back(MouseEvent mouseEvent) {
        ProfileMenu.back();
    }

    @FXML
    public void sortType(ActionEvent actionEvent) {
        if (comboBox.getValue().equals("sort by kill")) {
            sortByKill();
        } else if (comboBox.getValue().equals("sort by difficulty")) {
            sortByDifficulty();
        } else if (comboBox.getValue().equals("sort by accuracy")) {
            sortByAccuracy();
        }
    }

    public void sortByKill() {
        allUsers.sort((u2, u1) -> {
            if (u1.getTotalKill() == u2.getTotalKill())
                return Integer.compare(u1.getLastGameWave(), u2.getLastGameWave());
            return Integer.compare(u1.getTotalKill(), u2.getTotalKill());
        });
        uploadNames("kill");
    }

    public void sortByDifficulty() {
        allUsers.sort((u2, u1) -> {
            if (u1.getDifficultyScore() == u2.getDifficultyScore())
                return Integer.compare(u1.getLastGameWave(), u2.getLastGameWave());
            return Integer.compare(u1.getDifficultyScore(), u2.getDifficultyScore());
        });
        uploadNames("difficulty");
    }

    public void sortByAccuracy() {
        allUsers.sort((u2, u1) -> {
            if (u1.getAccuracy() == u2.getAccuracy())
                return Integer.compare(u1.getLastGameWave(), u2.getLastGameWave());
            return Integer.compare(u1.getAccuracy(), u2.getAccuracy());
        });
        uploadNames("accuracy");
    }

    public void uploadNames(String scoreType) {
        List<Label> usernamesLabel = Arrays.asList(username1, username2, username3, username4, username5, username6,
                username7, username8, username9, username10);
        List<Label> scoreLabel = Arrays.asList(score1, score2, score3, score4, score5, score6, score7, score8, score9,
                score10);
        List<Label> waveLabel = Arrays.asList(wave1, wave2, wave3, wave4, wave5, wave6, wave7, wave8, wave9, wave10);
        User user;
        for (int i = 0; i < Math.min(10, allUsers.size()); i++) {
            user = allUsers.get(i);
            usernamesLabel.get(i).setText("Username: " + user.getUsername());
            waveLabel.get(i).setText("Last Wave:" + user.getLastGameWave());

            if (scoreType.equals("kill"))
                scoreLabel.get(i).setText("Score: " + user.getTotalKill());

            else if (scoreType.equals("difficulty"))
                scoreLabel.get(i).setText("Score: " + user.getDifficultyScore());

            else
                scoreLabel.get(i).setText("Score: " + user.getAccuracy());
        }
    }
}
