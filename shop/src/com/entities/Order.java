package com.entities;

import java.util.Date;
import java.util.LinkedList;

public class Order {

    private static Integer orderId = 0;

    private Integer id;
    private Client client;
    private LinkedList<Book> books;
    private String destination;
    private Double price;
    private Date orderDate;
    private Date deliveryDate;
    private EStatus status;

    public Order(Client client, LinkedList<Book> books, String destination, Double price, Date orderDate, Date deliveryDate) {
        this.client = client;
        this.books = books;
        this.destination = destination;
        this.price = price;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        id = generateNextOrderId();
        status = EStatus.PENDING;
    }

    public static Integer getOrderId() {
        return orderId;
    }

    public static void setOrderId(Integer orderId) {
        Order.orderId = orderId;
    }

    private static int generateNextOrderId() {
        return ++orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LinkedList<Book> getBooks() {
        return books;
    }

    public void setBooks(LinkedList<Book> books) {
        this.books = books;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;

    }

    public Double getPrice() {
        return price;
    }

    public enum EStatus {
        PENDING, CANCELED, PERFORMING, COMPLETED
    }

}
