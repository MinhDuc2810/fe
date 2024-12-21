package com.example.fitnessgym.api;

public class ApiGetstatistical {
    String status;
    String total;

    public ApiGetstatistical(String status, String total) {
        this.status = status;
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
