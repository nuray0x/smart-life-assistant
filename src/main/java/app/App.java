package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/gui/LoginWindow.fxml"));
        Scene scene = new Scene(loader.load(), 420, 320);
        scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());

        stage.setTitle("Smart Life Assistant — вход");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
