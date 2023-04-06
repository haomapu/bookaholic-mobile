package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Book implements Serializable {

    private String imageUrl, title, author,
            category, description, size,publicationDate,
            publisher, typeOfCover, recentlyDate;
    private int quantity, numberOfPages, price, buyer;

    private ArrayList<Comment> comments;
    private ArrayList<String> images = new ArrayList<>();


    public Book(String title){
        this.title = title;
    }

    public Book(String imageUrl, String title, String author, String category, String description, int quantity, int price
            , String publicationDate, String publisher, String size, int numberOfPages, String typeOfCover, ArrayList<String> images) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.size = size;
        this.numberOfPages = numberOfPages;
        this.typeOfCover = typeOfCover;
        this.comments = new ArrayList<Comment>();
        this.images = images;
        this.buyer = 0;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.recentlyDate = currentDate.format(formatter);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity= quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getTypeOfCover() {
        return typeOfCover;
    }

    public void setTypeOfCover(String typeOfCover) {
        this.typeOfCover = typeOfCover;
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

    public Integer getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public String getRecentlyDate() {
        return recentlyDate;
    }

    public void setRecentlyDate(String recentlyDate) {
        this.recentlyDate = recentlyDate;
    }
}
