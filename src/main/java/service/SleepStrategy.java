package service;

import model.User;

public class SleepStrategy {

    public String recommend(User user) {
        int h = user.getSleepHours();
        if (h < 6) {
            return "Вы спите мало. Постарайтесь спать 7–8 часов, ложиться и вставать в одно и то же время.";
        } else if (h <= 8) {
            return "Сон в норме. Продолжайте в том же духе!";
        } else {
            return "Вы спите больше 8 часов. Возможно, стоит добавить больше дневной активности и спорта.";
        }
    }
}
