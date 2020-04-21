package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrdersMain {

    @SerializedName("orderID")
    private int orderID;

    @SerializedName("refNo")
    private String refNo;

    @SerializedName("shopID")
    private int shopID;

    @SerializedName("shopName")
    private String shopName;

    @SerializedName("status")
    private String status;

    @SerializedName("itemList")
    private List<MyOrderListItem> itemList ;

    public MyOrdersMain(){

    }

    public MyOrdersMain(int orderID, String refNo, int shopID, String shopName, String status, List<MyOrderListItem> itemList) {
        this.orderID = orderID;
        this.refNo = refNo;
        this.shopID = shopID;
        this.shopName = shopName;
        this.status = status;
        this.itemList = itemList;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MyOrderListItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<MyOrderListItem> itemList) {
        this.itemList = itemList;
    }
}
