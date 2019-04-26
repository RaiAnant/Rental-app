package com.example.acer.rentapp.model;

import com.google.gson.annotations.SerializedName;

public class Asset {
    @SerializedName("ASSET_ID")
    private String assetId;
    @SerializedName("ASSET_TYPE")
    private String assetType;
    @SerializedName("ASSET_NAME")
    private String assetName;
    @SerializedName("LENDER_ID")
    private String userName;
    @SerializedName("CUSTOMER_ID")
    private String customerId;
    @SerializedName("PICKUP_TIME")
    private String pickupTime;
    @SerializedName("PICKUP_LOCATION")
    private String pickupLocation;
    @SerializedName("DROP_TIME")
    private String dropTime;
    @SerializedName("DROP_LOCATION")
    private String dropLocation;
    @SerializedName("CHARGES")
    private String charges;
    @SerializedName("IS_AVAIL")
    private String isAvail;

    public Asset(String assetId, String assetType, String assetName, String userName, String customerId, String pickupTime, String pickupLocation, String dropTime, String dropLocation, String charges, String isAvail) {
        this.assetId = assetId;
        this.assetType = assetType;
        this.assetName = assetName;
        this.userName = userName;
        this.customerId = customerId;
        this.pickupTime = pickupTime;
        this.pickupLocation = pickupLocation;
        this.dropTime = dropTime;
        this.dropLocation = dropLocation;
        this.charges = charges;
        this.isAvail = isAvail;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getIsAvail() {
        return isAvail;
    }

    public void setIsAvail(String isAvail) {
        this.isAvail = isAvail;
    }
}
