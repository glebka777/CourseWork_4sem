package com.entities;

import java.util.LinkedList;

public class Client extends Person {

    private static Integer clientId = 0;
    LinkedList<Integer> orders;
    Cart cart;

    public Client(String firstName, String lastName, String number, String address, String email, String login, String
            password) {
        super(firstName, lastName, number, address, email, login, password);
        this.id = generateNextClientId();
        orders = new LinkedList<>();
        cart = new Cart();
    }

    private static int generateNextClientId() {
        return ++clientId;
    }

    public Cart getCart() {
        return cart;
    }

    public LinkedList<Integer> getOrders() {
        return orders;
    }

    public class Cart {

        LinkedList<Book.ISBN> bookISBNs;

        public Cart() {
            this.bookISBNs = new LinkedList<>();
        }

        public void addBook(Book.ISBN isbn) {
            bookISBNs.add(isbn);
        }

        public void removeBook(Book.ISBN isbn) {
            bookISBNs.remove(isbn);
        }

        public void clearCart() {
            bookISBNs.clear();
        }

        public boolean contains(Book.ISBN isbn){
            for (Book.ISBN bookISBN : bookISBNs) {
                if (bookISBN.equals(isbn)){
                    return true;
                }
            }
            return false;
        }

        public LinkedList<Book.ISBN> getBookISBNs(){
            return bookISBNs;
        }

    }

}
