package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AuthDatabase;
import app.App;
import model.User;
import model.UserManager;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void onLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Введите логин и пароль");
            return;
        }

        boolean ok = AuthDatabase.checkLogin(username, password);
        if (!ok) {
            errorLabel.setText("Неверный логин или пароль");
            return;
        }

        User user = UserManager.getInstance().loadOrCreateUser(username);

        UserManager.getInstance().setCurrentUser(user);

        openMainWindow();
    }


    @FXML
    private void onRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Введите логин и пароль для регистрации");
            return;
        }

        boolean ok = AuthDatabase.register(username, password);
        if (!ok) {
            errorLabel.setText("Такой логин уже занят");
            return;
        }

        errorLabel.setText("Успешная регистрация! Теперь можно войти.");
    }

    private void openMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/gui/MainWindow.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());

            Stage mainStage = new Stage();
            mainStage.setTitle("Smart Life Assistant");
            mainStage.setScene(scene);
            mainStage.show();

            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Ошибка при открытии главного окна: " + e.getMessage());
        }
    }
}
