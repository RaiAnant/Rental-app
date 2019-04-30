package com.example.acer.rentapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Request implements Serializable {

    @SerializedName("ASSET_ID")
    private String assetId;
    @SerializedName("CUSTOMER_ID")
    private String customerId;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("PICKUP_TIME")
    private String pickupTime;
    @SerializedName("DROP_TIME")
    private String dropTime;
    @SerializedName("RENT")
    private String rent;

    public Request(String assetId, String customerId, String status, String pickupTime, String dropTime, String dropLocation, String rent) {
        this.assetId = assetId;
        this.customerId = customerId;
        this.status = status;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.rent = rent;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }


    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}
