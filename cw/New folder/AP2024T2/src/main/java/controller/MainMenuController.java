package controller;

import model.Game;
import model.Result;
import model.User;

public class MainMenuController {
    public static Result setGame(boolean isNew) {
        if (isNew) {
            User.getLoggedInUser().setLastGame(new Game());
        } else {
            if (User.getLoggedInUser().isGuest() || User.getLoggedInUser().getLastGame() == null)
                return new Result(false, "you dont have unfinished game!");
        }
        return new Result(true, "");
    }


}
