package com.example.bookaholic.details;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.bookaholic.R;
import com.google.firebase.database.Exclude;

public class Book implements Serializable {
    @Exclude
    public static ArrayList<Book> allBooks = new ArrayList<>();
    private String title, author, category, description, downloadURL;
    private int price;
    int imageURL;

    public Book(String title, String author, String type, String description, int imageURL, String downloadURL, int price) {
        this.title = title;
        this.author = author;
        this.category = type;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageURL() {
        return imageURL;
    }

    public void setImageURL(int imageURL) {
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
        return new Book(this.title, this.author, this.category, this.description, this.imageURL, this.downloadURL, this.price);
    }

    public int getImageResId() {
        return imageURL;
    }
}
