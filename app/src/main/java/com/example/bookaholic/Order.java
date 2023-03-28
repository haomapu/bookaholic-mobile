package com.example.bookaholic;

import com.example.bookaholic.details.Book;

import java.util.Date;
import java.util.List;

public class Order {
    private List<Book> orderBooks;
    private User user;
    private String address;
    private double totalPrice;
    private String orderStatus;
    private Date createdAt;

    public Order(List<Book> orderBooks, User user, String address, double totalPrice, String orderStatus, Date createdAt) {
        this.orderBooks = orderBooks;
        this.user = user;
        this.address = address;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public List<Book> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<Book> orderBooks) {
        this.orderBooks = orderBooks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
