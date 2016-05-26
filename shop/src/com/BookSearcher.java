package com;

import com.entities.Book;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookSearcher {

    public static CopyOnWriteArrayList<Book> strictSearch(ArrayList<Book> allBooks, String author, String name, String
            publishingHouse, Double minPrice, Double maxPrice) {
        CopyOnWriteArrayList<Book> result = new CopyOnWriteArrayList<>(allBooks);
        for (Book book : result) {
            if (author != null && !author.equals("")) {
                if (!book.getAuthor().contains(author)) {
                    result.remove(book);
                    continue;
                }
            }
            if (name != null && !name.equals("")) {
                if (!book.getName().contains(name)) {
                    result.remove(book);
                    continue;
                }
            }
            if (publishingHouse != null && !publishingHouse.equals("")) {
                if (!book.getPublishingHouse().contains(publishingHouse)) {
                    result.remove(book);
                    continue;
                }
            }
            if (minPrice != null && maxPrice != null) {
                if (book.getPrice() < minPrice || book.getPrice() > maxPrice) {
                    result.remove(book);
                }
            }
            else if (minPrice != null) {
                if (book.getPrice() < minPrice) {
                    result.remove(book);
                }
            }
            else if (maxPrice != null) {
                if (book.getPrice() > maxPrice) {
                    result.remove(book);
                }
            }
        }
        return result;
    }

    public static CopyOnWriteArrayList<Book> nonStrictSearch(ArrayList<Book> allBooks, String author, String name,
                                                             String publishingHouse, Double minPrice, Double maxPrice) {
        CopyOnWriteArrayList<Book> result = new CopyOnWriteArrayList<>(allBooks);
        for (Book book : result) {
            if (author != null && !author.equals("")) {
                author = author.toLowerCase();
                String[] words;
                if ((words = author.split(" ")).length > 1) {
                    boolean contains = false;
                    for (String word : words) {
                        if (book.getAuthor().toLowerCase().contains(word)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        result.remove(book);
                        continue;
                    }
                }
                else if (!book.getAuthor().toLowerCase().contains(author)) {
                    result.remove(book);
                    continue;
                }
            }
            if (name != null && !name.equals("")) {
                name = name.toLowerCase();
                String[] words;
                if ((words = name.split(" ")).length > 1) {
                    boolean contains = false;
                    for (String word : words) {
                        if (book.getName().toLowerCase().contains(word)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        result.remove(book);
                        continue;
                    }
                }
                else if (!book.getName().toLowerCase().contains(name)) {
                    result.remove(book);
                    continue;
                }
            }
            if (publishingHouse != null && !publishingHouse.equals("")) {
                publishingHouse = publishingHouse.toLowerCase();
                String[] words;
                if ((words = publishingHouse.split(" ")).length > 1) {
                    boolean contains = false;
                    for (String word : words) {
                        if (book.getPublishingHouse().toLowerCase().contains(word)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        result.remove(book);
                        continue;
                    }
                }
                else if (!book.getPublishingHouse().toLowerCase().contains(publishingHouse)) {
                    result.remove(book);
                    continue;
                }
            }
            if (minPrice != null && maxPrice != null) {
                if (book.getPrice() < minPrice || book.getPrice() > maxPrice) {
                    result.remove(book);
                }
            }
            else if (minPrice != null) {
                if (book.getPrice() < minPrice) {
                    result.remove(book);
                }
            }
            else if (maxPrice != null) {
                if (book.getPrice() > maxPrice) {
                    result.remove(book);
                }
            }
        }
        return result;
    }

}
