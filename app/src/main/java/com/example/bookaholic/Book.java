package com.example.bookaholic;

import java.io.Serializable;

public class Book implements Serializable {

    private String imageUrl, name, author,
            category, desciption, price,publicationDate,
            publisher, size, numberOfPages, typeOfCover;


    public Book(String name){
        this.name = name;
    }

    public Book(String imageUrl, String name, String author,String category, String desciption, String price
            , String publicationDate, String publisher, String size, String numberOfPages, String typeOfCover) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.author = author;
        this.category = category;
        this.desciption = desciption;
        this.price = price;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.size = size;
        this.numberOfPages = numberOfPages;
        this.typeOfCover = typeOfCover;
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
    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getTypeOfCover() {
        return typeOfCover;
    }

    public void setTypeOfCover(String typeOfCover) {
        this.typeOfCover = typeOfCover;
    }
}
