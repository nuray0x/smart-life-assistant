package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.User;
import model.UserManager;
import service.NutritionAnalyzer;
import service.NutritionStrategy;
import service.SleepAnalyzer;
import service.SleepStrategy;

public class SleepController {

    @FXML private Slider sleepSlider;
    @FXML private Label sleepValueLabel;
    @FXML private TextArea resultArea;

    private User user;

    private final SleepAnalyzer sleepAnalyzer = new SleepAnalyzer();
    private final NutritionAnalyzer nutritionAnalyzer = new NutritionAnalyzer();
    private final SleepStrategy sleepStrategy = new SleepStrategy();
    private final NutritionStrategy nutritionStrategy = new NutritionStrategy();

    @FXML
    private void initialize() {
        user = UserManager.getInstance().getCurrentUser();
        if (user == null) {
            user = new User("Student");
            UserManager.getInstance().setCurrentUser(user);
        }

        sleepSlider.setValue(user.getSleepHours());
        sleepValueLabel.setText(String.valueOf((int) sleepSlider.getValue()));

        sleepSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            sleepValueLabel.setText(String.valueOf(newVal.intValue()));
        });
    }

    @FXML
    private void onAnalyze() {
        int h = (int) Math.round(sleepSlider.getValue());

        user.setSleepHours(h);
        UserManager.getInstance().save();

        int sleep = sleepAnalyzer.analyze(user);
        int nutScore = nutritionAnalyzer.analyze(user);
        String nutLevel = nutritionAnalyzer.levelText(nutScore);

        StringBuilder sb = new StringBuilder();
        sb.append("Ваш сон: ").append(sleep).append(" часов\n\n");
        sb.append("Рекомендация по сну:\n")
                .append(sleepStrategy.recommend(user)).append("\n\n");
        sb.append("Оценка питания: ").append(nutScore)
                .append(" / 10 (").append(nutLevel).append(")\n");
        sb.append("Совет по питанию:\n")
                .append(nutritionStrategy.recommend(user));

        resultArea.setText(sb.toString());
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) resultArea.getScene().getWindow();
        stage.close();
    }
}
