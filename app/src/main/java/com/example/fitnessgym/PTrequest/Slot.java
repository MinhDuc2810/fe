package com.example.fitnessgym.PTrequest;

public class Slot {
    private int id;
    private String time;

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return time; // Hiển thị thời gian trực tiếp trên Spinner
    }
}



