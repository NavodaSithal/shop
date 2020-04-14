package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainPrizeListItem {

    @SerializedName("orderID")
    private int orderID;

    @SerializedName("refNo")
    private String refNo;

    @SerializedName("shopListWithPrices")
    private List<ShopPrizeItem> shopListWithPrices;

    public MainPrizeListItem(int orderID, String refNo, List<ShopPrizeItem> shopListWithPrices) {
        this.orderID = orderID;
        this.refNo = refNo;
        this.shopListWithPrices = shopListWithPrices;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public List<ShopPrizeItem> getShopListWithPrices() {
        return shopListWithPrices;
    }

    public void setShopListWithPrices(List<ShopPrizeItem> shopListWithPrices) {
        this.shopListWithPrices = shopListWithPrices;
    }
}
