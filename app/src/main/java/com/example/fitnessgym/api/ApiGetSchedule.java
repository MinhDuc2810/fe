package com.example.fitnessgym.api;

import com.example.fitnessgym.Schedule;

import java.util.List;

public class ApiGetSchedule {
    String status;
    List<Schedule> schedule;

    public ApiGetSchedule(String status, List<Schedule> schedule) {
        this.status = status;
        this.schedule = schedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
