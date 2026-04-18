package model;

import java.io.Serializable;
import java.util.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private int sleepHours;
    private List<String> habits = new ArrayList<>();
    private Map<String, String> schedule = new LinkedHashMap<>();

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.sleepHours = 8;

        schedule.put("Monday", "Math 10:00–11:00");
        schedule.put("Tuesday", "English 12:00–13:00");
        schedule.put("Wednesday", "Gym 16:00–17:00");
        schedule.put("Thursday", "Programming 14:00–15:00");
        schedule.put("Friday", "Free");

        habits.add("Drink water");
        habits.add("Read a book");
        habits.add("Meditate");
        habits.add("Exercise");
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchedule(Map<String, String> schedule) {
        this.schedule = new LinkedHashMap<>(schedule);
    }

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public List<String> getHabits() {
        return habits;
    }

    public void addHabit(String habit) {
        habits.add(habit);
    }

    public void removeHabit(int index) {
        if (index >= 0 && index < habits.size()) {
            habits.remove(index);
        }
    }

    public Map<String, String> getSchedule() {
        return schedule;
    }

    public void setSchedule(String day, String activity) {
        schedule.put(day, activity);
    }

    public void setId(String id) {
        this.id = id;
    }
}
