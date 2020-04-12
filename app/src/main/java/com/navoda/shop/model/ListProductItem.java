package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

public class ListProductItem {

    @SerializedName("id")
    private int id;

    @SerializedName("Name")
    private String Name;

    @SerializedName("quentity")
    private int quentity;

    public ListProductItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuentity() {
        return quentity;
    }

    public void setQuentity(int quentity) {
        this.quentity = quentity;
    }
}
