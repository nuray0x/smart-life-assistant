package service;

import model.User;

import java.util.List;
import java.util.Random;

public class HabitAnalyzer {

    private final Random random = new Random();

    private static final String[] DEFAULT_HABITS = {
            "Drink water",
            "Read a book",
            "Meditate",
            "Exercise"
    };

    public String suggestToday(User user) {
        List<String> habits = user.getHabits();
        if (habits.isEmpty()) {
            int idx = random.nextInt(DEFAULT_HABITS.length);
            return DEFAULT_HABITS[idx];
        } else {
            int idx = random.nextInt(habits.size());
            return habits.get(idx);
        }
    }
}
