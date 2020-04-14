package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Prizereq {

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("productList")
    private List<itemreq> productList;

    public Prizereq(String latitude, String longitude, List<itemreq> productList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.productList = productList;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<itemreq> getProductList() {
        return productList;
    }

    public void setProductList(List<itemreq> productList) {
        this.productList = productList;
    }
}
