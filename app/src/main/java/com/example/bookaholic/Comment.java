package com.example.bookaholic;

public class Comment {
    private String content,name;
    private int rate, avatar;

    public Comment(){
    }
    public Comment(String content, String name, int avatar,int rate) {
        this.content = content;
        this.rate = rate;
        this.avatar = avatar;
        this.name = name;
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

    public int getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rate;
    }
}
