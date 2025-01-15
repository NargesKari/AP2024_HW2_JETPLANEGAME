package controller;

import model.Result;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigningMenuController {
    public static Matcher getMatcher(String pattern, String input) {
        return Pattern.compile(pattern).matcher(input);
    }

    public static Result register(String username, String password, Boolean type) {
        if (!type) {
            if (User.getUserWithName(username) != null) return new Result(false, "username already exists");
            if (!getMatcher("\\w+", username).matches()) return new Result(false, "invalid username format");
            if (!getMatcher("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\S+", password).matches())
                return new Result(false, "invalid password format");
        }
        User user = new User(username, password, type);
        return new Result(true, "register successful");
    }

    public static Result login(String username, String password) {
        User user = User.getUserWithName(username);
        if (user == null) return new Result(false, "user doesn't exist");
        if (!user.getPassword().equals(password)) return new Result(false, "password doesn't match");
        User.setLoggedInUser(user);
        return new Result(true, "login successful");
    }
}
