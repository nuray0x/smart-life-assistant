package gui;

import app.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.UserManager;

public class MainController {

    @FXML
    private Button scheduleButton;

    @FXML
    private void onScheduleEditor() {
        openChildWindow("/gui/ScheduleWindow.fxml", "Расписание");
    }

    @FXML
    private void onSleepAnalyze() {
        openChildWindow("/gui/SleepWindow.fxml", "Анализ сна");
    }

    @FXML
    private void onHabits() {
        openChildWindow("/gui/HabitsWindow.fxml", "Привычки");
    }

    @FXML
    private void onReport() {
        openChildWindow("/gui/ReportWindow.fxml", "Ежедневный отчёт");
    }

    @FXML
    private void onProfile() {
        openChildWindow("/gui/ProfileWindow.fxml", "Мой профиль");
    }

    @FXML
    private void onLogout() {
        try {
            UserManager.getInstance().setCurrentUser(null);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/gui/LoginWindow.fxml"));
            Scene scene = new Scene(loader.load(), 420, 320);
            scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());

            Stage loginStage = new Stage();
            loginStage.setTitle("Smart Life Assistant — вход");
            loginStage.setScene(scene);
            loginStage.show();

            Stage mainStage = (Stage) scheduleButton.getScene().getWindow();
            mainStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Не удалось выйти: " + e.getMessage());
        }
    }

    private void openChildWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initOwner(scheduleButton.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Не удалось открыть окно: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
