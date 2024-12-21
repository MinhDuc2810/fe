package com.example.fitnessgym;

public class Schedule {
    int request_id;
    int pt_id;
    String date;
    String time;
    String pt_name;
    String pt_phone;

    public Schedule(int request_id,int pt_id, String date, String time, String pt_name, String pt_phone) {
        this.date = date;
        this.request_id = request_id;
        this.pt_id = pt_id;
        this.time = time;
        this.pt_name = pt_name;
        this.pt_phone = pt_phone;
    }

    public int getPt_id() {
        return pt_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPt_name() {
        return pt_name;
    }

    public String getPt_phone() {
        return pt_phone;
    }
}
