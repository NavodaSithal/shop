package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

public class CustomerObj {

    @SerializedName("id")
    private int id;

    @SerializedName("firstName")
    private String firstname;

    @SerializedName("email")
    private String quentity;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("mobileNo")
    private String mobileNo;

    @SerializedName("status")
    private String status;

    public CustomerObj(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getQuentity() {
        return quentity;
    }

    public void setQuentity(String quentity) {
        this.quentity = quentity;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
