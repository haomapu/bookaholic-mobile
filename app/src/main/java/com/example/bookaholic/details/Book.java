package com.example.bookaholic.details;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.bookaholic.Comment;
import com.example.bookaholic.R;
import com.google.firebase.database.Exclude;

public class Book implements Serializable {
    @Exclude
    public static ArrayList<Book> allBooks = new ArrayList<>();
    private String title, author, category, description, downloadURL;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int price;
    int imageURL;

    public Book(){

    }
    public Book(String title, String author, String category, String description, String downloadURL, ArrayList<Comment> comments, int price, int imageURL) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.downloadURL = downloadURL;
        this.comments = comments;
        this.price = price;
        this.imageURL = imageURL;
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Exclude
    public Book deepCopy() {
        return new Book(this.title, this.author, this.category, this.description, this.downloadURL, this.comments, this.price, this.imageURL);
    }

    public int getImageResId() {
        return imageURL;
    }

    @SuppressLint("DefaultLocale")
    public String getDisplayablePrice() {
        try {
            return String.format("%, d đ", price);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return "N/A";
        }
    }

    @Exclude
    public boolean hasNameSimilarTo(String text) {
        return this.title.toLowerCase().contains(text.toLowerCase());
    }

    @Exclude
    public boolean hasPriceInRange(Integer min, Integer max) {
        return (min == null || price >= min) && (max == null || price <= max);
    }
}
