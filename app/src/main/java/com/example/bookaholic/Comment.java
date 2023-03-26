package com.example.bookaholic;

public class Comment {
    private String content;
    private int rate;

    public Comment(){
    }
    public Comment(String content, int rate) {
        this.content = content;
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
