package com.example.bookaholic;

public class Offer {
    private int image;
    private String offer;

    public Offer(int image , String offer){
        this.image = image;
        this.offer = offer;
    }

    public int getImage() {
        return image;
    }

    public String getOffer() {
        return offer;
    }
}
