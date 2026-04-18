package service;

import model.User;

import java.util.Random;

public class SleepAnalyzer {

    private final Random random = new Random();

    public int analyze(User user) {
        int h = user.getSleepHours();
        if (h <= 0) {
            h = 4 + random.nextInt(6);
            user.setSleepHours(h);
        }
        return h;
    }
}
