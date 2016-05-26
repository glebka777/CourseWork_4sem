package com.tools;

import com.UserApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangePassBox {

    public static void show() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Смена пароля");

        HBox firstBox = new HBox();
        firstBox.setAlignment(Pos.CENTER_RIGHT);
        Label firstLabel = new Label("Введите новый пароль: ");
        firstLabel.setTextAlignment(TextAlignment.RIGHT);
        PasswordField firstField = new PasswordField();
        firstBox.getChildren().addAll(firstLabel, firstField);
        HBox secondBox = new HBox();
        secondBox.setAlignment(Pos.CENTER_RIGHT);
        Label secondLabel = new Label("Введите пароль ещё раз: ");
        PasswordField secondField = new PasswordField();
        secondBox.getChildren().addAll(secondLabel, secondField);

        HBox buttonBox = new HBox();
        Button closeButton = new Button("Назад");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(35);
        closeButton.setOnAction(event->window.close());

        Button submitButton = new Button("Изменить пароль");
        submitButton.setPrefWidth(200);
        submitButton.setPrefHeight(35);
        submitButton.setOnAction(event->submit(firstField.getText().trim(), secondField.getText().trim(), window));

        buttonBox.getChildren().addAll(submitButton, closeButton);
        buttonBox.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstBox = new HBox(firstBox), secondBox = new HBox(secondBox), buttonBox);
        firstBox.setAlignment(Pos.CENTER);
        secondBox.setAlignment(Pos.CENTER);
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

    private static void submit(String pass, String passRepeat, Stage window) {
        if (!pass.equals(passRepeat) || pass.equals("")) {
            AlertBox.display("Неверный ввод", "Введите другой пароль.");
            return;
        }
        if (!UserApplication.getBookShop().changePass(UserApplication.getClient().getId(), pass)) {
            AlertBox.display("Неверный ввод", "Введите другой пароль.");
        } else {
            window.close();
        }
    }

}