package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

public class itemreq {

    @SerializedName("subProductID")
    private int productID;

    @SerializedName("quantity")
    private int quantity;

    public itemreq(int productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
