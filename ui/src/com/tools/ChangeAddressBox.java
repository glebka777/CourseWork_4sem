package com.tools;

import com.UserApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeAddressBox {

    public static void show() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Смена адреса доставки");

        HBox firstBox = new HBox();
        firstBox.setAlignment(Pos.CENTER_RIGHT);
        Label firstLabel = new Label("Введите новый адрес: ");
        firstLabel.setTextAlignment(TextAlignment.RIGHT);
        TextField firstField = new TextField();
        firstBox.getChildren().addAll(firstLabel, firstField);

        HBox buttonBox = new HBox();
        Button closeButton = new Button("Назад");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(35);
        closeButton.setOnAction(event->window.close());

        Button submitButton = new Button("Изменить адрес");
        submitButton.setPrefWidth(200);
        submitButton.setPrefHeight(35);
        submitButton.setOnAction(event->submit(firstField.getText().trim(), window));

        buttonBox.getChildren().addAll(submitButton, closeButton);
        buttonBox.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstBox = new HBox(firstBox), buttonBox);
        firstBox.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        layout.setOnKeyPressed(event->{
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
                submitButton.fire();
            }
        });

        Scene scene = new Scene(layout, 500, 350);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void submit(String address, Stage window) {
        if (!UserApplication.getBookShop().changeAddress(UserApplication.getClient().getId(), address)) {
            AlertBox.display("Неверный ввод", "Введите другой адрес.");
        }
        else {
            window.close();
        }
    }

}