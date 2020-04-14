package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopPrizeItem {

    @SerializedName("shopID")
    private int shopID;

    @SerializedName("shopName")
    private String shopName;

    @SerializedName("distance")
    private String distance;

    @SerializedName("totalValue")
    private String totalValue;

    @SerializedName("latitude")
    private String lat;

    @SerializedName("longitude")
    private String lon;

    @SerializedName("notAvailableProductsList")
    private List<UnAvailableItem> notAvailableProductsList;

//    public ShopPrizeItem(int shopID, String shopName, String distance, String totalValue, List<UnAvailableItem> notAvailableProductsList) {
//        this.shopID = shopID;
//        this.shopName = shopName;
//        this.distance = distance;
//        this.totalValue = totalValue;
//        this.notAvailableProductsList = notAvailableProductsList;
//    }

    public ShopPrizeItem(int shopID, String shopName, String distance, String totalValue, String lat, String lon, List<UnAvailableItem> notAvailableProductsList) {
        this.shopID = shopID;
        this.shopName = shopName;
        this.distance = distance;
        this.totalValue = totalValue;
        this.lat = lat;
        this.lon = lon;
        this.notAvailableProductsList = notAvailableProductsList;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public List<UnAvailableItem> getNotAvailableProductsList() {
        return notAvailableProductsList;
    }

    public void setNotAvailableProductsList(List<UnAvailableItem> notAvailableProductsList) {
        this.notAvailableProductsList = notAvailableProductsList;
    }
}
