package gui;

import model.UserManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class HabitsController {

    @FXML
    private ListView<String> habitsList;

    @FXML
    private TextField newHabitField;

    private User user;

    @FXML
    private void initialize() {
        user = UserManager.getInstance().getCurrentUser();
        if (user == null) {
            user = new User("Student");
            UserManager.getInstance().setCurrentUser(user);
        }

        habitsList.setItems(FXCollections.observableArrayList(user.getHabits()));
    }

    @FXML
    private void onAddHabit() {
        String text = newHabitField.getText();
        if (text == null || text.isBlank()) {
            return;
        }
        user.addHabit(text);
        habitsList.getItems().add(text);
        newHabitField.clear();
        UserManager.getInstance().save();

    }

    @FXML
    private void onDeleteHabit() {
        int idx = habitsList.getSelectionModel().getSelectedIndex();
        if (idx < 0) {
            return;
        }
        habitsList.getItems().remove(idx);
        user.removeHabit(idx);
        UserManager.getInstance().save();

    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) habitsList.getScene().getWindow();
        stage.close();
    }
}
