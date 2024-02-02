package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

public class ClientFormController implements Initializable {

    @FXML
    private Pane emojiPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox mainContainer;
    @FXML
    private TextField txtMsg;
    FileChooser fileChooser = new FileChooser();
    ClientSide ob;
    String imgUrl;

    String[]emoji = {
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE07", // 😇
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE0B", // 😋
    };

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
    void btnEmojiOnAction(ActionEvent event) {
        emojiPane.setVisible(!emojiPane.isVisible());
    }

    @FXML
    void btnImageOnAction(ActionEvent event) throws FileNotFoundException {

        Initializable initializable = (URL url, ResourceBundle resourceBundle) -> {
            fileChooser.setInitialDirectory(new File("/home"));
        };

        File file = fileChooser.showOpenDialog(new Stage());
        imgUrl = file.getPath();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            emojiPane.setVisible(false);
            int buttonIndex = 0;
            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 3; column++) {
                    if (buttonIndex < emoji.length) {
                        String emojies = emoji[buttonIndex];
                        JFXButton emojiButton = createEmojiButton(emojies);
                        gridPane.add(emojiButton, column, row);
                        buttonIndex++;
                    } else {
                        break;
                    }
                }
            }

    }

    private JFXButton createEmojiButton(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }

    private void emojiButtonAction(ActionEvent actionEvent) {
        JFXButton button = (JFXButton) actionEvent.getSource();
        txtMsg.appendText(button.getText());

    }

}