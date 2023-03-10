package com.example.bookaholic.details;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.Exclude;

public class Book implements Serializable {
    @Exclude
    public static ArrayList<Book> allBooks = new ArrayList<>();
    private String name, author, type, description, imageURL, downloadURL;
    private int price;

    public Book(String name, String author, String type, String description, String imageURL, String downloadURL, int price) {
        this.name = name;
        this.author = author;
        this.type = type;
        this.description = description;
        this.imageURL = imageURL;
        this.downloadURL = downloadURL;
        this.price = price;
    }

    public static ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public static void setAllBooks(ArrayList<Book> allBooks) {
        Book.allBooks = allBooks;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @NonNull
    @Exclude
    public Book deepCopy() {
        return new Book(this.name, this.author, this.type, this.description, this.imageURL, this.downloadURL, this.price);
    }
}
