package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

public class MainProduct {

    @SerializedName("imageURL")
    private String img;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("categoryID")
    private int categoryId;

    public MainProduct(){}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
