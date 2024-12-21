package com.example.fitnessgym.api;



import com.example.fitnessgym.PTrequest.Slot;

import java.util.List;

public class ApiGetTImeAvailable {
    private String status;
    private List<Slot> slots;

    // Getters và Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}



