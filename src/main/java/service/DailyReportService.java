package service;

import model.User;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DailyReportService {

    private final SleepAnalyzer sleepAnalyzer = new SleepAnalyzer();
    private final NutritionAnalyzer nutritionAnalyzer = new NutritionAnalyzer();
    private final HabitAnalyzer habitAnalyzer = new HabitAnalyzer();
    private final SleepStrategy sleepStrategy = new SleepStrategy();
    private final NutritionStrategy nutritionStrategy = new NutritionStrategy();

    public String generateReport(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append("Ежедневный отчёт для: ")
                .append(user.getName())
                .append("\nВремя: ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .append("\n\n");

        int sleep = sleepAnalyzer.analyze(user);
        int nutScore = nutritionAnalyzer.analyze(user);
        String nutLevel = nutritionAnalyzer.levelText(nutScore);
        String habitToday = habitAnalyzer.suggestToday(user);

        sb.append("Сон: ").append(sleep).append(" часов\n");
        sb.append("Рекомендация по сну: ").append(sleepStrategy.recommend(user)).append("\n\n");

        sb.append("Питание (оценка): ").append(nutScore)
                .append(" / 10 (").append(nutLevel).append(")\n");
        sb.append("Рекомендация по питанию: ")
                .append(nutritionStrategy.recommend(user)).append("\n\n");

        sb.append("Сегодняшняя привычка: ").append(habitToday).append("\n\n");

        sb.append("Расписание на ближайшие дни:\n");
        user.getSchedule().forEach((day, activity) ->
                sb.append("  ").append(day).append(" - ").append(activity).append("\n")
        );

            return sb.toString();
    }

    public Path exportToFile(User user, String reportText) throws IOException {
        String safeName = user.getName().replaceAll("\\s+", "_");
        String fileName = "daily_report_" + safeName + "_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) +
                ".txt";

        Path path = Paths.get(fileName);
        Files.writeString(path, reportText);
        return path;
    }
}