package com.entities;

import java.io.File;
import java.util.Date;

public class Book {

    private String name;
    private String author;
    private String description;
    private Double price;
    private Integer quantity;
    private Integer pages;
    private Integer pressRun;
    private Date date;
    private String publishingHouse;
    private ISBN isbn;
    private File cover;

    public Book(String name, String author, String description, Double price, Integer pages, Integer pressRun, ISBN isbn, String publishingHouse, Date date, File cover) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.price = price;
        this.pages = pages;
        this.pressRun = pressRun;
        this.isbn = isbn;
        this.publishingHouse = publishingHouse;
        this.date = date;
        this.cover = cover;
        quantity = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPressRun() {
        return pressRun;
    }

    public void setPressRun(Integer pressRun) {
        this.pressRun = pressRun;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

    public void addCopyOfBook() {
        quantity++;
    }

    public void removeCopyOfBook(){
        quantity--;
    }

    public File getCover() {
        return cover;
    }

    @Override
    public String toString() {
        return "Author: " + author + "; Name: " + name + "; ISBN: " + isbn + "; Quanity: " + quantity;
    }

    public static class ISBN {

        private int i1;
        private int i2;
        private int i3;
        private int i4;
        private int i5;

        public ISBN(int i1, int i2, int i3, int i4, int i5) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
        }

        public ISBN(String str) {
            String[] ints = str.split("-");
            if (ints.length == 5) {
                i1 = Integer.parseInt(ints[0]);
                i2 = Integer.parseInt(ints[1]);
                i3 = Integer.parseInt(ints[2]);
                i4 = Integer.parseInt(ints[3]);
                i5 = Integer.parseInt(ints[4]);
            }
        }

        @Override
        public String toString() {
            return i1 + "-" + i2 + "-" + i3 + "-" + i4 + "-" + i5;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj ||
                    obj != null
                            && obj instanceof ISBN
                            && i1 == ((ISBN) obj).i1
                            && i2 == ((ISBN) obj).i2
                            && i3 == ((ISBN) obj).i3
                            && i4 == ((ISBN) obj).i4
                            && i5 == ((ISBN) obj).i5;
        }

        @Override
        public int hashCode() {
            return i1 + i2 + i3 + i4 + i5;
        }
    }

}
