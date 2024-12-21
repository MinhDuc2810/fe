package com.example.fitnessgym.api;

import com.example.fitnessgym.Hocvien;

import java.util.List;

public class ApiGetHocvien {
String status;
List<Hocvien> hocvien;

    public ApiGetHocvien(String status, List<Hocvien> hocvien) {
        this.status = status;
        this.hocvien = hocvien;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Hocvien> getHocvien() {
        return hocvien;
    }

    public void setHocvien(List<Hocvien> hocvien) {
        this.hocvien = hocvien;
    }
}
