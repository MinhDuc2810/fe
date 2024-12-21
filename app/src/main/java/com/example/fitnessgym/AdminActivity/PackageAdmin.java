package com.example.fitnessgym.AdminActivity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PackageAdmin implements Serializable {
    @SerializedName("id")
    private int adid;
    @SerializedName("name")
    private String adname;
    @SerializedName("duration")
    private int adduration;
    @SerializedName("type")
    private String adtype;
    @SerializedName("price")
    private String adprice;



    public PackageAdmin(int adid, String adname, int adduration, String adtype, String adprice) {
        this.adid = adid;
        this.adname = adname;
        this.adduration = adduration;
        this.adtype = adtype;
        this.adprice = adprice;
    }

    public int getAdid() {
        return adid;
    }

    public void setAdid(int adid) {
        this.adid = adid;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public int getAdduration() {
        return adduration;
    }

    public void setAdduration(int adduration) {
        this.adduration = adduration;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public String getAdprice() {
        return adprice;
    }

    public void setAdprice(String adprice) {
        this.adprice = adprice;
    }
}
