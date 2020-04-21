package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

public class MyOrderListItem {

    @SerializedName("productID")
    private int id;

    @SerializedName("productName")
    private String Name;

    @SerializedName("quantity")
    private Double quentity;

    public MyOrderListItem(int id, String name, Double quentity) {
        this.id = id;
        Name = name;
        this.quentity = quentity;
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

    public Double getQuentity() {
        return quentity;
    }

    public void setQuentity(Double quentity) {
        this.quentity = quentity;
    }
}
