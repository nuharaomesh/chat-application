package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.controller.ServerFormController;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        new Thread(() ->{

            try {
                ServerFormController.main(new String[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}