package com.tools;

import com.entities.Book;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookRow {

    public Book book;
    public SimpleStringProperty author;
    public SimpleStringProperty name;
    public SimpleDoubleProperty price;
    public SimpleStringProperty publishingHouse;

    public BookRow(Book book) {
        this.book = book;
        this.author = new SimpleStringProperty(book.getAuthor());
        this.name = new SimpleStringProperty(book.getName());
        this.price = new SimpleDoubleProperty(book.getPrice());
        this.publishingHouse = new SimpleStringProperty(book.getPublishingHouse());
    }

    public Book getBook() {
        return book;
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Double getPrice() {
        return price.get();
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public String getPublishingHouse() {
        return publishingHouse.get();
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse.set(publishingHouse);
    }
}
