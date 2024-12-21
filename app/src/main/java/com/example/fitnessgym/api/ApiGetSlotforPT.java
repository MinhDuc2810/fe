package com.example.fitnessgym.api;

import com.example.fitnessgym.Sessions;

import java.util.List;

public class ApiGetSlotforPT {
    String status;
    List<Sessions> sessions;

    public ApiGetSlotforPT(String status, List<Sessions> sessions) {
        this.status = status;
        this.sessions = sessions;


    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sessions> getSessions() {
        return sessions;
    }

    public void setSessions(List<Sessions> sessions) {
        this.sessions = sessions;
    }
}
