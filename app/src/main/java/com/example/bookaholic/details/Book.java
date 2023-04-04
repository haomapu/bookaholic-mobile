package com.example.bookaholic.details;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.example.bookaholic.Comment;
import com.example.bookaholic.R;
import com.google.firebase.database.Exclude;

public class Book implements Serializable {
    @Exclude
    public static ArrayList<Book> allBooks = new ArrayList<>();
    private String title, author, category, description, downloadURL;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int price;
    private ArrayList<String> images = new ArrayList<>();

    public Book(){

    }
    public Book(String title, String author, String category, String description, String downloadURL, ArrayList<Comment> comments, int price, ArrayList<String> images) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.downloadURL = downloadURL;
        this.comments = comments;
        this.price = price;
        this.images = images;
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

    public float getRateAvg(){
        float sum = 0;
        for (int i = 0; i < comments.size(); i++){
            sum += comments.get(i).getRate();
        }
        return sum/comments.size();
    }
    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public Integer getPrice() {
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @NonNull
    @Exclude
    public Book deepCopy() {
        return new Book(this.title, this.author, this.category, this.description, this.downloadURL, this.comments, this.price, this.images);
    }

    public int getImageResId() {
        return Integer.parseInt(images.get(0));
    }

    @SuppressLint("DefaultLocale")
    public String getDisplayablePrice() {
        String str = NumberFormat.getNumberInstance(Locale.US).format(price);
        str += " đ";
        return str;
    }

    @Exclude
    public boolean hasNameSimilarTo(String text) {
        return this.title.toLowerCase().contains(text.toLowerCase());
    }

    @Exclude
    public boolean hasPriceInRange(Integer min, Integer max) {
        return (min == null || price >= min) && (max == null || price <= max);
    }

    public static Book findBookByTitle(String title) {
        for (int i = 0; i < allBooks.size(); i++){
            if (allBooks.get(i).getTitle().contains(title)){
                return allBooks.get(i);
            }
        }
        return null;
    }
}
