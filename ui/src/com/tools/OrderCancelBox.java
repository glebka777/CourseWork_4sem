package com.tools;

import com.UserApplication;
import com.entities.Order;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderCancelBox {

    public static void show(Order order) {
        if (order.getStatus() == Order.EStatus.COMPLETED || order.getStatus() == Order.EStatus.CANCELED) return;
        Stage stage = new Stage();
        Button submitButton = new Button("Отменить заказ");
        submitButton.setPrefWidth(200);
        submitButton.setPrefHeight(50);
        submitButton.setOnAction(e->submit(order, stage));
        Button closeButton = new Button("Назад");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(50);
        closeButton.setOnAction(e->stage.close());
        HBox layout = new HBox(submitButton, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 420, 75);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Отмена заказа");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private static void submit(Order order, Stage stage) {
        UserApplication.getBookShop().cancelOrder(order.getId());
        stage.close();
    }

}
