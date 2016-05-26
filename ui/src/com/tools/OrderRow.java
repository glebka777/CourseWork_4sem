package com.tools;

import com.entities.Book;
import com.entities.Order;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class OrderRow {

    Order order;
    SimpleStringProperty books;
    SimpleStringProperty destination;
    SimpleStringProperty orderDate;
    SimpleStringProperty deliveryDate;
    SimpleDoubleProperty price;
    SimpleStringProperty status;

    public OrderRow(Order order) {
        this.order = order;
        String books = "";
        for (Book book : order.getBooks()) {
            books += book.getAuthor() + " :: " + book.getName() + "\n";
        }
        this.books = new SimpleStringProperty(books);
        this.destination = new SimpleStringProperty(order.getDestination());
        this.orderDate = new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(order.getOrderDate()));
        this.deliveryDate = new SimpleStringProperty(new SimpleDateFormat("dd-MM-yyyy").format(order.getDeliveryDate()));
        this.price = new SimpleDoubleProperty(order.getPrice());
        this.status = new SimpleStringProperty(order.getStatus().toString());
    }

    public Order getOrder() {
        return order;
    }

    public String getBooks() {
        return books.get();
    }

    public void setBooks(String books) {
        this.books.set(books);
    }

    public String getDestination() {
        return destination.get();
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public String getDeliveryDate() {
        return deliveryDate.get();
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate.set(deliveryDate);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
