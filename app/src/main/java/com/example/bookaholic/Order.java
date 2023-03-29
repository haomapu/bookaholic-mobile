package com.example.bookaholic;

import com.example.bookaholic.details.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private ArrayList<OrderedBook> orderBooks;
    private User user;
    private String address;
    private double totalPrice;
    private String orderStatus;
    private Date createdAt;

    public Order(ArrayList<OrderedBook> orderBooks, User user, String address, double totalPrice, String orderStatus, Date createdAt) {
        this.orderBooks = orderBooks;
        this.user = user;
        this.address = address;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public ArrayList<OrderedBook> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(ArrayList<OrderedBook> orderBooks) {
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
