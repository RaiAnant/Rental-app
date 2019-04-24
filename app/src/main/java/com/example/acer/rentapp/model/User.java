package com.example.acer.rentapp.model;

import com.google.gson.annotations.SerializedName;


public class User {
    @SerializedName("USER_ID")
    private String userName;
    @SerializedName("USER_NAME")
    private String name;
    @SerializedName("USER_LOCATION")
    private String location;
    @SerializedName("USER_CONTACT")
    private String phno;
    @SerializedName("USER_PASSWORD")
    private String password;

    public  User(String userName, String name, String location, String phno, String password){
        this.userName  = userName;
        this.name = name;
        this.location = location;
        this.phno = phno;
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhno() {
        return phno;
    }

    public String getUserName() {
        return userName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
