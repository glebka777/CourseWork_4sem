package com.controllers;

import com.entities.Book;
import com.entities.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

import static com.UserApplication.getClient;
import static com.UserApplication.personalPageController;

public class BookViewPageController {

    @FXML
    public Button removeFromCartButton;
    @FXML
    public Button addToCartButton;

    private Client client;
    private Client.Cart cart;
    private Book book;

    @FXML
    private Label cartLabel;
    @FXML
    private Label cartStatus;
    @FXML
    private Label entryLabel;

    @FXML
    private Button closeButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publishingHouseLabel;
    @FXML
    private Label discriptionLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label ISBNLabel;
    @FXML
    private Label pagesLabel;
    @FXML
    private Label pressRunLabel;
    @FXML
    private Label quanityLabel;
    @FXML
    private Label dateLabel;

    public void setFields(Book book, double imageWidth, double imageHeight) {
        this.book = book;
        imageView.setImage(new Image(book.getCover().toString(), imageWidth, imageHeight, true, true));
        nameLabel.setText(book.getName());
        authorLabel.setText(book.getAuthor());
        publishingHouseLabel.setText(book.getPublishingHouse());
        discriptionLabel.setText(book.getDescription());
        priceLabel.setText(book.getPrice().toString());
        ISBNLabel.setText(book.getIsbn().toString());
        pagesLabel.setText(book.getPages().toString());
        pressRunLabel.setText(book.getPressRun().toString());
        quanityLabel.setText(book.getQuantity().toString());
        dateLabel.setText(new SimpleDateFormat("dd-MM-yyyy").format(book.getDate()));

        if ((client = getClient()) != null){
            entryLabel.setVisible(false);
            cartStatus.setText(String.valueOf(countBookCopiesInCart(book.getIsbn(), client)));
            cart = client.getCart();
        } else {
            cartLabel.setVisible(false);
            cartStatus.setVisible(false);
        }
    }

    private int countBookCopiesInCart(Book.ISBN currentISBN, Client client){
        int count = 0;
        for (Book.ISBN isbn : client.getCart().getBookISBNs()) {
            if (isbn.equals(currentISBN)){
                count++;
            }
        }
        return count;
    }

    @FXML
    private void removeFromCartButtonClick(ActionEvent event) {
        Book.ISBN isbn;
        cart.removeBook(isbn = book.getIsbn());
        if (!cart.contains(isbn)) {
            removeFromCartButton.setDisable(true);
        }
        cartStatus.setText(String.valueOf(countBookCopiesInCart(book.getIsbn(), client)));
        personalPageController.clientRefresh();
    }

    @FXML
    private void addToCartButtonClick(ActionEvent event) {
        cart.addBook(book.getIsbn());
        removeFromCartButton.setDisable(false);
        cartStatus.setText(String.valueOf(countBookCopiesInCart(book.getIsbn(), client)));
        personalPageController.clientRefresh();
    }

    @FXML
    private void closeButtonClick(ActionEvent event) {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
}

