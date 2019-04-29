package com.example.acer.rentapp.model;

import com.google.gson.annotations.SerializedName;

public class Admin {

    @SerializedName("ADMIN_ID")
    private String adminID;
    @SerializedName("ADMIN_NAME")
    private String name;
    @SerializedName("ADMIN_PASSWORD")
    private String password;

    public Admin(String adminID, String name, String password) {
        this.adminID = adminID;
        this.name = name;
        this.password = password;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
