package com.example.lab5;

public class Item {
    private String url;
    private int like;

    public Item(String url) {
        this.url = url;
        this.like = 0;
    }

    public String getUrl() {
        return url;
    }

    public int getLiked() {
        return like;
    }

    public void setLiked(int liked) {
        this.like = liked;
    }
}
