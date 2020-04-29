package com.example.lab2;

public class Singleton {

    public String[] name;
    public String[] id;
    private static Singleton instance;
    private Singleton(){}

    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
            instance.name = new String[200];
            instance.id = new String[200];
        }
        return instance;
    }
}
