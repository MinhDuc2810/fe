package com.example.fitnessgym.api;

import com.example.fitnessgym.Available_pts;

import java.util.List;

public class ApiListRequestResponse {
    private String status;
    private List<Available_pts> available_pts;

    public ApiListRequestResponse(String status, List<Available_pts> available_pts) {
        this.status = status;
        this.available_pts = available_pts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Available_pts> getAvailable_pts() {
        return available_pts;
    }

    public void setAvailable_pts(List<Available_pts> available_pts) {
        this.available_pts = available_pts;
    }
}
