package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.User;
import model.UserManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScheduleController {

    public static class ScheduleRow {
        private final SimpleStringProperty day = new SimpleStringProperty();
        private final SimpleStringProperty activity = new SimpleStringProperty();

        public ScheduleRow(String day, String activity) {
            this.day.set(day);
            this.activity.set(activity);
        }

        public String getDay() { return day.get(); }
        public void setDay(String v) { day.set(v); }

        public String getActivity() { return activity.get(); }
        public void setActivity(String v) { activity.set(v); }

        public SimpleStringProperty dayProperty() { return day; }
        public SimpleStringProperty activityProperty() { return activity; }
    }

    @FXML private TableView<ScheduleRow> table;
    @FXML private TableColumn<ScheduleRow, String> dayCol;
    @FXML private TableColumn<ScheduleRow, String> activityCol;

    private final ObservableList<ScheduleRow> rows = FXCollections.observableArrayList();
    private User user;

    @FXML
    private void initialize() {
        user = UserManager.getInstance().getCurrentUser();
        if (user == null) {
            user = new User("student");
            UserManager.getInstance().setCurrentUser(user);
        }

        dayCol.setCellValueFactory(data -> data.getValue().dayProperty());
        activityCol.setCellValueFactory(data -> data.getValue().activityProperty());

        table.setEditable(true);
        activityCol.setEditable(true);
        activityCol.setCellFactory(TextFieldTableCell.forTableColumn());

        activityCol.setOnEditCommit(event -> {
            ScheduleRow row = event.getRowValue();
            String newVal = event.getNewValue();
            row.setActivity(newVal == null ? "" : newVal);
        });

        rows.clear();
        for (Map.Entry<String, String> e : user.getSchedule().entrySet()) {
            rows.add(new ScheduleRow(e.getKey(), e.getValue()));
        }
        table.setItems(rows);
    }

    @FXML
    private void onSave() {
        if (user == null) return;

        Map<String, String> newSchedule = new LinkedHashMap<>();
        for (ScheduleRow r : rows) {
            String day = r.getDay();
            String activity = r.getActivity();
            newSchedule.put(day, activity == null ? "" : activity.trim());
        }

        user.setSchedule(newSchedule);
        UserManager.getInstance().save();
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }
}
