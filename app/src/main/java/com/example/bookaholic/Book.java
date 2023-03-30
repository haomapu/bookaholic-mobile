package com.example.bookaholic;

import java.io.Serializable;

public class Book implements Serializable {

    private String imageUrl;
    private String name;
    private String author;
    private String category;
    private String desciption;
    private String price;

    public Book(String name){
        this.name = name;
    }

    public Book(String imageUrl, String name, String category, String author, String desciption, String price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.author = author;
        this.category = category;
        this.desciption = desciption;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
