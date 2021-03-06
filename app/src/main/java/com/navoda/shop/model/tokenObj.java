package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

public class tokenObj {

    @SerializedName("accessToken")
    private String token;

    @SerializedName("tokenType")
    private String type;

    public tokenObj(){}

    public tokenObj(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }
}
