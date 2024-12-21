package com.example.fitnessgym.PTrequest;

public class Response {
    int request_id;
    String action;
    int pt_id;

    public Response(int request_id, String action, int pt_id) {
        this.request_id = request_id;
        this.action = action;
        this.pt_id = pt_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }
}
