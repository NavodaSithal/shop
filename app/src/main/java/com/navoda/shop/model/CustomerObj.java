package com.navoda.shop.model;

import com.google.gson.annotations.SerializedName;

public class CustomerObj {

    @SerializedName("id")
    private int id;

    @SerializedName("firstName")
    private String firstname;

    @SerializedName("email")
    private String email;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("mobileNo")
    private String mobileNo;

    @SerializedName("status")
    private String status;

    public CustomerObj(){}

    public CustomerObj(int id, String firstname, String email, String lastName, String mobileNo, String status) {
        this.id = id;
        this.firstname = firstname;
        this.email = email;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.status = status;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
