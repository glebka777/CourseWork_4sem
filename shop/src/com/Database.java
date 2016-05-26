package com;

import com.entities.Book;
import com.entities.Client;
import com.entities.Order;
import com.exceptions.OrderCreationException;
import com.exceptions.WrongInformationException;

import java.util.HashMap;
import java.util.TreeMap;

public class Database {

    private TreeMap<Integer, Order> orders;
    private TreeMap<Integer, Client> clients;
    private HashMap<Book.ISBN, Book> books;

    public Database() {
        orders = new TreeMap<Integer, Order>();
        clients = new TreeMap<Integer, Client>();
        books = new HashMap<Book.ISBN, Book>();
    }

    public TreeMap<Integer, Order> getOrders() {
        return orders;
    }

    public TreeMap<Integer, Client> getClients() {
        return clients;
    }

    public HashMap<Book.ISBN, Book> getBooks() {
        return books;
    }

    public void addBook(Book newBook) {
        Book b;
        if ((b = books.get(newBook.getIsbn())) != null) b.addCopyOfBook();
        else books.put(newBook.getIsbn(), newBook);
    }

    public void addClient(Client client) throws WrongInformationException {
        for (Client c : clients.values()) {
            if (c.getLogin().equals(client.getLogin())) {
                throw new WrongInformationException("Current login is already used.");
            }
            if (c.getEmail().equals(client.getEmail())) {
                throw new WrongInformationException("Current e-mail is already used.");
            }
        }
        clients.put(client.getId(), client);
    }

    public Client removeClient(Integer id) {
        return clients.remove(id);
    }

    public void addOrder(Order order) throws OrderCreationException {
        Order o;
        if ((o = orders.get(order.getId())) != null
                && (o.getStatus() == Order.EStatus.PENDING || o.getStatus() == Order.EStatus.PERFORMING)) {
            throw new OrderCreationException("Order already " + o.getStatus().toString());
        }
        orders.put(order.getId(), order);
    }

    public Order removeOrder(Integer id) {
        return orders.remove(id);
    }

    public Client getClient(Integer id) {
        return clients.get(id);
    }

    public Book getBook(Book.ISBN isbn) {
        return books.get(isbn);
    }

    public Book removeBook(Book.ISBN isbn) {
        Book b;
        if ((b = books.get(isbn)) == null || b.getQuantity() == 0) {
            return null;
        }
        else {
            b.removeCopyOfBook();
        }
        return b;
    }

    public Order getOrder(Integer id) {
        return orders.get(id);
    }

}
