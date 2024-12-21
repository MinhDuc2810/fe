package com.example.fitnessgym;

public class PtSpinner {
    String ptName;
    int ptId;

    public PtSpinner(String ptName, int ptId) {
        this.ptName = ptName;
        this.ptId = ptId;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public int getPtId() {
        return ptId;
    }

    public void setPtId(int ptId) {
        this.ptId = ptId;
    }

    @Override
    public String toString() {
        return ptName + " - " + ptId;
    }
}
