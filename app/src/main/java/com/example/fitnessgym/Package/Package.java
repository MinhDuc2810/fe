package com.example.fitnessgym.Package;

import java.io.Serializable;

public class Package implements Serializable {
    private int id;
    private String name;
    private int duration;
    private String type;
    private String price;

    public Package(int id,String name, int duration, String type, String price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.type = type;
        this.price = price;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}