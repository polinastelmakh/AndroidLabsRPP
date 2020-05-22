package com.example.lab5;

import java.util.LinkedList;

public class LikeInfo {
    private static LikeInfo instance;
    private LinkedList<String> urls;

    private LikeInfo() {
        urls = new LinkedList<>();
    }

    public static LikeInfo createInstance() {
        if(instance == null) {
            instance = new LikeInfo();
        }
        return instance;
    }

    public void addUrl(String url) {
        if(urls.size() == 10) {
            urls.pop();
        }
        urls.add(url);
    }

    public void deleteUrl(String url) {
        urls.remove(url);
    }

    public LinkedList<String> getUrls() {
        return urls;
    }
}
