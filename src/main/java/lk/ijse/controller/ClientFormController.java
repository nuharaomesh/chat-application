package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lk.ijse.service.ClientSide;

import java.io.*;
import java.net.Socket;

public class ClientFormController {

    @FXML
    private VBox mainContainer;
    @FXML
    private TextField txtMsg;
    ClientSide ob;

    public void initialize() throws IOException {

        ob = new ClientSide();
        new Thread(ob).start();

        ob.valueProperty().addListener(((observableValue, oldValue, newValue) -> {

            Label label = new Label(newValue);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().add(label);
            mainContainer.getChildren().add(hBox);
        }));
    }
    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        ob.sendClient(txtMsg.getText());
    }
}