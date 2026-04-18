package service;

import model.User;

import java.util.Random;

public class NutritionAnalyzer {

    private final Random random = new Random();

    public int analyze(User user) {
        int base = 3 + random.nextInt(6);
        int sleep = user.getSleepHours();
        if (sleep >= 7 && sleep <= 8) {
            base += 1;
        }
        if (base > 10) base = 10;
        return base;
    }

    public String levelText(int score) {
        if (score <= 3) return "Плохое";
        if (score <= 6) return "Среднее";
        return "Хорошее";
    }
}
