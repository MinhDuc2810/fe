package com.example.fitnessgym.Package;

public class aPackage {
    private String name;
    private int duration;
    private String type;
    private String price;

    public aPackage(String name ,int duration , String type ,String price ) {
        this.price = price;
        this.type = type;
        this.duration = duration;
        this.name = name;
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
