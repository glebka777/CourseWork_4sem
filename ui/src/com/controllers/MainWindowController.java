package com.controllers;

import com.BookSearcher;
import com.entities.Book;
import com.entities.Client;
import com.tools.BookRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.UserApplication.*;

public class MainWindowController implements Initializable {

    @FXML
    public TableView<BookRow> table;
    @FXML
    private CheckBox strictBox;
    @FXML
    private Label fullNameLabel;
    @FXML
    private HBox header;
    @FXML
    private VBox personalBox;
    @FXML
    private VBox optionsBox;
    @FXML
    private TextField authorField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField publishingHouseField;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;
    @FXML
    private Button searchButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        header.getChildren().remove(personalBox);
        table.setRowFactory(tv->{
            TableRow<BookRow> row = new TableRow<>();
            row.setOnMouseClicked(event->{
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    BookRow rowData = row.getItem();
                    showBookStage(rowData.getBook());
                }
            });
            return row;
        });
    }

    public void refreshClient() {
        Client client = getClient();
        header.getChildren().clear();
        if (client == null) {
            header.getChildren().add(optionsBox);
            fullNameLabel.setText("");
        }
        else {
            header.getChildren().add(personalBox);
            Client c;
            fullNameLabel.setText((c = getClient()).getFirstName() + " " + c.getLastName());
        }
        refreshTable();
    }

    @FXML
    private void clearButtonClick(ActionEvent event) {
        authorField.clear();
        nameField.clear();
        publishingHouseField.clear();
        minPriceField.clear();
        maxPriceField.clear();
        refreshTable();
    }

    private void refreshTable() {
        ArrayList<BookRow> tempList = new ArrayList<>();
        for (Book book : getBookShop().getDatabase().getBooks().values()) {
            tempList.add(new BookRow(book));
        }
        ObservableList<BookRow> oList = FXCollections.observableList(tempList);
        table.setItems(oList);
    }

    private void refreshTable(List<Book> books) {
        ArrayList<BookRow> tempList = new ArrayList<>();
        for (Book book : books) {
            tempList.add(new BookRow(book));
        }
        ObservableList<BookRow> oList = FXCollections.observableList(tempList);
        table.setItems(oList);
    }

    @FXML
    private void showPersonalPage(ActionEvent event) {
        setPersonalPage();
    }

    @FXML
    private void exitButtonClick(ActionEvent event) {
        logOutClient();
        setMainPage();
    }

    @FXML
    private void searchButtonClick(ActionEvent event) {
        Double minPrice = null;
        Double maxPrice = null;
        String s;
        try {
            minPrice = (s = minPriceField.getText()).equals("") ? null : Double.parseDouble(s);
            maxPrice = (s = maxPriceField.getText()).equals("") ? null : Double.parseDouble(s);
        } catch(NumberFormatException e) {
            minPriceField.clear();
            maxPriceField.clear();
        }
        ArrayList<Book> allBooks = new ArrayList<>(getBookShop().getDatabase().getBooks().values());
        String author = authorField.getText().trim();
        String name = nameField.getText().trim();
        String publishingHouse = publishingHouseField.getText().trim();
        List<Book> searchResultList = null;
        if (strictBox.isSelected()){
            searchResultList = BookSearcher.strictSearch(allBooks, author, name, publishingHouse, minPrice, maxPrice);
        } else {
            searchResultList = BookSearcher.nonStrictSearch(allBooks, author, name, publishingHouse, minPrice, maxPrice);
        }
        refreshTable(searchResultList);
    }

    @FXML
    private void registerButtonClick(ActionEvent event) {
        showRegisterPage();
    }

    @FXML
    private void loginButtonClick(ActionEvent event) {
        showLoginPage();
    }

}
