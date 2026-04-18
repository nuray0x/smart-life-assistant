package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.nio.file.Path;

import model.User;
import model.UserManager;
import service.DailyReportService;

public class ReportController {

    @FXML
    private TextArea reportArea;

    private final DailyReportService reportService = new DailyReportService();
    private User user;

    @FXML
    private void initialize() {
        user = UserManager.getInstance().getCurrentUser();
        if (user == null) {
            user = new User("Student");
            UserManager.getInstance().setCurrentUser(user);
        }

        String text = reportService.generateReport(user);
        reportArea.setText(text);
    }

    @FXML
    private void onSave() {
        try {
            String reportText = reportArea.getText();
            Path path = reportService.exportToFile(user, reportText);
            reportArea.appendText("\n\nОтчёт сохранён в файл:\n" + path.toAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            reportArea.appendText("\n\nОшибка при сохранении: " + e.getMessage());
        }
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) reportArea.getScene().getWindow();
        stage.close();
    }
}
