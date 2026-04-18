package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AuthDatabase;
import model.User;
import model.UserManager;

public class ProfileController {

    @FXML private Label usernameLabel;
    @FXML private Label habitsLabel;

    @FXML private TextField nameField;
    @FXML private TextField sleepField;

    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField repeatPasswordField;

    @FXML private Label statusLabel;

    private User user;

    @FXML
    private void initialize() {
        user = UserManager.getInstance().getCurrentUser();
        if (user == null) {
            setStatus("Пользователь не найден.");
            return;
        }

        usernameLabel.setText(user.getId() != null ? user.getId() : "-");
        nameField.setText(user.getName() != null ? user.getName() : "");
        sleepField.setText(String.valueOf(user.getSleepHours()));
        habitsLabel.setText(String.valueOf(user.getHabits().size()));
    }

    @FXML
    private void onSaveProfile() {
        if (user == null) return;

        String newName = nameField.getText() == null ? "" : nameField.getText().trim();
        String sleepText = sleepField.getText() == null ? "" : sleepField.getText().trim();

        int sleep;
        try {
            sleep = Integer.parseInt(sleepText);
        } catch (Exception e) {
            setStatus("Часы сна должны быть числом (0–24).");
            return;
        }

        if (sleep < 0 || sleep > 24) {
            setStatus("Часы сна должны быть от 0 до 24.");
            return;
        }

        if (!newName.isEmpty()) {
            user.setName(newName);
        }

        user.setSleepHours(sleep);
        UserManager.getInstance().save();
        setStatus("Профиль сохранён");
    }

    @FXML
    private void onChangePassword() {
        if (user == null) return;

        String p1 = newPasswordField.getText();
        String p2 = repeatPasswordField.getText();

        if (p1 == null || p1.isBlank() || p2 == null || p2.isBlank()) {
            setStatus("Введите новый пароль и повторите его.");
            return;
        }
        if (!p1.equals(p2)) {
            setStatus("Пароли не совпадают.");
            return;
        }
        if (p1.length() < 4) {
            setStatus("Пароль слишком короткий (минимум 4 символа).");
            return;
        }

        boolean ok = AuthDatabase.updatePassword(user.getId(), p1);
        if (ok) {
            newPasswordField.clear();
            repeatPasswordField.clear();
            setStatus("Пароль обновлён");
        } else {
            setStatus("Не удалось обновить пароль.");
        }
    }

    @FXML
    private void onDeleteAccount() {
        if (user == null) return;

        boolean ok = AuthDatabase.deleteUser(user.getId());
        if (!ok) {
            setStatus("Не удалось удалить аккаунт.");
            return;
        }

        UserManager.getInstance().setCurrentUser(null);
        UserManager.getInstance().save();

        setStatus("Аккаунт удалён. Закройте приложение и войдите снова.");
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
    }

    private void setStatus(String msg) {
        if (statusLabel != null) statusLabel.setText(msg);
    }
}
