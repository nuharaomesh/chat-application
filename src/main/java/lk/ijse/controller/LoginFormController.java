package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private TextField txtUserName;

    public void btnLoginOnAction(ActionEvent event) throws IOException {

        if (txtUserName.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a username first..").show();
            return;
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/client_form.fxml"))));
        stage.setTitle(txtUserName.getText());
        stage.centerOnScreen();
        stage.show();
    }
}
