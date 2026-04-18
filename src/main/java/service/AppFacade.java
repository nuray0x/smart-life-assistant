package service;

import model.User;
import model.UserManager;

public class AppFacade {

    private final UserManager userManager = UserManager.getInstance();
    private final DailyReportService reportService = new DailyReportService();
    private final SleepAnalyzer sleepAnalyzer = new SleepAnalyzer();
    private final NutritionAnalyzer nutritionAnalyzer = new NutritionAnalyzer();
    private final SleepStrategy sleepStrategy = new SleepStrategy();
    private final NutritionStrategy nutritionStrategy = new NutritionStrategy();

    public User getCurrentUser() {
        return userManager.getCurrentUser();
    }

    public void setCurrentUser(User user) {
        userManager.setCurrentUser(user);
    }

    public String generateDailyReport() {
        User user = getCurrentUser();
        return reportService.generateReport(user);
    }

    public String analyzeSleep(int hours) {
        User user = getCurrentUser();
        user.setSleepHours(hours);

        int sleepScore = sleepAnalyzer.analyze(user);
        int nutritionScore = nutritionAnalyzer.analyze(user);

        return "Сон: " + sleepScore + " часов\n\n"
                + "Рекомендация по сну:\n"
                + sleepStrategy.recommend(user) + "\n\n"
                + "Питание: " + nutritionScore + "/10\n"
                + nutritionStrategy.recommend(user);
    }
}
