package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.dto.TransferData;
import lk.ijse.service.ClientSide;

import java.io.*;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClientFormController  {

    @FXML
    private VBox mainContainer;
    @FXML
    private TextField txtMsg;
    FileChooser fileChooser = new FileChooser();
    ClientSide ob;
    String imgUrl;

    public void initialize() throws IOException {

        ob = new ClientSide();
        new Thread(ob).start();

        ob.valueProperty().addListener(((observableValue, oldValue, newValue) -> {


            if (newValue.getMsg().startsWith("/home")) {

                try {

                    HBox imgBox = new HBox();
                    imgBox.setAlignment(Pos.CENTER_LEFT);
                    imgBox.setPadding(new Insets(5,5,5,10));
                    System.out.println(newValue.getMsg());
                    ImageView imageView = new ImageView(new Image(new FileInputStream((newValue.getMsg()))));
                    imageView.setFitHeight(300);
                    imageView.setFitWidth(300);
                    imgBox.getChildren().add(imageView);

                    mainContainer.getChildren().add(imgBox);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 0, 10));

            Text text = new Text(newValue.getMsg());
            text.setStyle("-fx-font-size: 14");
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 12px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(1, 1, 1));

            hBox.getChildren().add(textFlow);

            HBox hBoxTime = new HBox();
            hBoxTime.setAlignment(Pos.CENTER_LEFT);
            hBoxTime.setPadding(new Insets(0, 5, 5, 10));
            String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            Text time = new Text(stringTime);
            time.setStyle("-fx-font-size: 8");

            hBoxTime.getChildren().add(time);

            mainContainer.getChildren().add(hBox);
            mainContainer.getChildren().add(hBoxTime);
        }));
    }
    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {

        if (imgUrl != null && txtMsg.getText().equals("")) {

            ob.sendClient(new TransferData("IMAGE", imgUrl, "", "ALL"));

            ImageView imageView = new ImageView(new Image(new FileInputStream(imgUrl)));
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            HBox hBox = new HBox();
            hBox.getChildren().add(imageView);
            hBox.setPadding(new Insets(5,5,5,10));
            hBox.setAlignment(Pos.CENTER_RIGHT);
            mainContainer.getChildren().add(hBox);
            imgUrl = "";
            return;
        }
        if (imgUrl != null) {

            ob.sendClient(new TransferData("IMAGE", imgUrl, "", "ALL"));

            ImageView imageView = new ImageView(new Image(new FileInputStream(imgUrl)));
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            HBox hBox = new HBox();
            hBox.getChildren().add(imageView);
            hBox.setPadding(new Insets(5,5,5,10));
            hBox.setAlignment(Pos.CENTER_RIGHT);
            mainContainer.getChildren().add(hBox);
        }

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 0, 10));

        Text text = new Text(txtMsg.getText());
        text.setStyle("-fx-font-size: 14");
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 12px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(1, 1, 1));

        hBox.getChildren().add(textFlow);

        HBox hBoxTime = new HBox();
        hBoxTime.setAlignment(Pos.CENTER_RIGHT);
        hBoxTime.setPadding(new Insets(0, 5, 5, 10));
        String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Text time = new Text(stringTime);
        time.setStyle("-fx-font-size: 8");

        hBoxTime.getChildren().add(time);

        mainContainer.getChildren().add(hBox);
        mainContainer.getChildren().add(hBoxTime);

        ob.sendClient(new TransferData("TEXT", txtMsg.getText(), "", "ALL"));

        txtMsg.clear();
    }

    @FXML
    void btnImageOnAction(ActionEvent event) throws FileNotFoundException {

        Initializable initializable = (URL url, ResourceBundle resourceBundle) -> {
            fileChooser.setInitialDirectory(new File("/home"));
        };

        File file = fileChooser.showOpenDialog(new Stage());
        imgUrl = file.getPath();
    }
}