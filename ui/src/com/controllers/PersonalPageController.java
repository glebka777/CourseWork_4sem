package com.controllers;

import com.entities.Book;
import com.entities.Client;
import com.exceptions.OrderCreationException;
import com.tools.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static com.UserApplication.*;

public class PersonalPageController implements Initializable {

    @FXML
    public TableView<BookRow> cartTable;
    @FXML
    public TextField summaryField;
    @FXML
    private Button applyButton;

    private Client client;

    @FXML
    private ToggleButton orderSwitch;
    @FXML
    private ToggleButton cartSwitch;
    @FXML
    private TableView<OrderRow> orderTable;
    @FXML
    private HBox tableContainer;
    @FXML
    private DatePicker calendar;
    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label emailLabel;

    public void clientRefresh() {
        client = getClient();
        firstNameLabel.setText(client.getFirstName());
        lastNameLabel.setText(client.getLastName());
        phoneNumberLabel.setText(client.getNumber());
        addressLabel.setText(client.getAddress());
        emailLabel.setText(client.getEmail());
        refreshCartTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cartTable.setRowFactory(tv->{
            TableRow<BookRow> row = new TableRow<>();
            row.setOnMouseClicked(event->{
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    BookRow rowData = row.getItem();
                    showBookStage(rowData.getBook());
                }
            });
            return row;
        });

        tableContainer.getChildren().clear();
        tableContainer.getChildren().add(cartTable);

        orderTable.setRowFactory(tv->{
            TableRow<OrderRow> row = new TableRow<>();
            row.setOnMouseClicked(event->{
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    OrderRow rowData = row.getItem();
                    OrderCancelBox.show(rowData.getOrder());
                }
            });
            return row;
        });
    }

    public void switchToCartTable(ActionEvent event) {
        refreshCartTable();
        cartSwitch.setSelected(true);
        orderSwitch.setSelected(false);
        tableContainer.getChildren().clear();
        tableContainer.getChildren().add(cartTable);
        calendar.setDisable(false);
        summaryField.setDisable(false);
        applyButton.setDisable(false);
    }

    public void switchToOrderTable(ActionEvent event) {
        refreshOrderTable();
        cartSwitch.setSelected(false);
        orderSwitch.setSelected(true);
        tableContainer.getChildren().clear();
        tableContainer.getChildren().add(orderTable);
        calendar.setDisable(true);
        summaryField.setDisable(true);
        applyButton.setDisable(true);
    }

    @FXML
    private void changeAddressButtonClick(ActionEvent event) {
        ChangeAddressBox.show();
        clientRefresh();
    }

    private void refreshOrderTable() {
        ArrayList<OrderRow> tempList = new ArrayList<>();
        for (Integer orderID : client.getOrders()) {
            tempList.add(new OrderRow(getBookShop().getDatabase().getOrder(orderID)));
        }
        ObservableList<OrderRow> oList = FXCollections.observableList(tempList);
        orderTable.setItems(oList);
    }

    private void refreshCartTable() {
        ArrayList<BookRow> tempList = new ArrayList<>();
        Double summaryPrice = 0.0;
        for (Book.ISBN isbn : client.getCart().getBookISBNs()) {
            Book b;
            tempList.add(new BookRow(b = getBookShop().getDatabase().getBooks().get(isbn)));
            summaryPrice += b.getPrice();
        }
        ObservableList<BookRow> oList = FXCollections.observableList(tempList);
        cartTable.setItems(oList);
        summaryField.setText(summaryPrice.toString());
    }

    @FXML
    private void changePasswordButtonClick(ActionEvent event) {
        ChangePassBox.show();
    }

    @FXML
    private void logOutButtonClick(ActionEvent event) {
        logOutClient();
        clearLabels();
        setMainPage();
    }

    @FXML
    private void applyButtonClick(ActionEvent event) throws OrderCreationException {
        LocalDate localDate = calendar.getValue();
        if (client.getCart() == null || client.getCart().getBookISBNs().size() == 0) {
            AlertBox.display("Ошибка", "Корзина пуста.");
            return;
        }
        if (localDate == null) {
            AlertBox.display("Неверная дата доставки", "Выберите другой день.");
            return;
        }
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date deliveryDate = Date.from(instant);
        if (!deliveryDate.after(new Date())) {
            AlertBox.display("Неверная дата доставки", "Выберите другой день.");
            return;
        }
        getBookShop().createOrder(client.getId(), client.getCart().getBookISBNs(), client.getAddress(), deliveryDate);
        AlertBox.display("Заказ успешно оформлен.", "Спасибо за заказ.\nНаш оператор скоро свяжется с Вами.");
        client.getCart().clearCart();
        refreshCartTable();
    }

    @FXML
    private void backButtonClick(ActionEvent event) {
        setMainPage();
        clearLabels();
    }

    private void clearLabels() {
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        phoneNumberLabel.setText("");
        addressLabel.setText("");
        emailLabel.setText("");
        summaryField.setText("");
    }

}

//TODO showOrderStage
