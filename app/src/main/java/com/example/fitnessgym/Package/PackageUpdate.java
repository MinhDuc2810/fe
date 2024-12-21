package com.example.fitnessgym.Package;

import java.io.Serializable;

public class PackageUpdate implements Serializable {
    private String name;
    private String type;
    private String price;
    private int duration;

    public PackageUpdate(String name, String type, String price, int duration) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
