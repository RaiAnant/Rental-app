package com.example.acer.rentapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Asset implements Serializable {
    @SerializedName("ASSET_ID")
    private String assetId;
    @SerializedName("ASSET_TYPE")
    private String assetType;
    @SerializedName("ASSET_NAME")
    private String assetName;
    @SerializedName("LENDER_ID")
    private String userName;
    @SerializedName("PICKUP_LOCATION")
    private String pickupLocation;
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
        this.pickupLocation = pickupLocation;
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

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
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
