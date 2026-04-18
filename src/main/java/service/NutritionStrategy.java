package service;

import model.User;

public class NutritionStrategy {

    public String recommend(User user) {
        int h = user.getSleepHours();

        if (h < 5) {
            return "Недосып может усиливать тягу к сладкому и фастфуду. " +
                    "Старайтесь есть белки (яйца, мясо, бобы), овощи и пить воду.";
        } else if (h <= 8) {
            return "Поддерживайте баланс: 3–4 приёма пищи в день, овощи, фрукты, сложные углеводы, достаточно воды.";
        } else {
            return "Следите, чтобы не 'заедать' усталость: лёгкие перекусы, меньше сладкого вечером.";
        }
    }
}
