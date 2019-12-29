package com.louis.foodadvisor.Model;

public class ShopInfo {

    private Integer shopID;
    private String shopName;
    private String shopPhoto;
    private String shopAddress;
    private Double shopAddressLat;
    private Double shopAddressLong;
    private String shopCategory;
    private String shopStartTime;
    private String shopEndTime;
    private String shopPrice;

    private Integer shopDistance;

    public ShopInfo(Integer shopID, String shopName, String shopPhoto, String shopAddress, Double shopAddressLat, Double shopAddressLong, String shopCategory, String shopStartTime, String shopEndTime, String shopPrice) {
        this.shopID = shopID;
        this.shopName = shopName;
        this.shopPhoto = shopPhoto;
        this.shopAddress = shopAddress;
        this.shopAddressLat = shopAddressLat;
        this.shopAddressLong = shopAddressLong;
        this.shopCategory = shopCategory;
        this.shopStartTime = shopStartTime;
        this.shopEndTime = shopEndTime;
        this.shopPrice = shopPrice;
        this.shopDistance = 0;
    }

    public Integer getShopID() {
        return shopID;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Double getShopAddressLat() {
        return shopAddressLat;
    }

    public void setShopAddressLat(Double shopAddressLat) {
        this.shopAddressLat = shopAddressLat;
    }

    public Double getShopAddressLong() {
        return shopAddressLong;
    }

    public void setShopAddressLong(Double shopAddressLong) {
        this.shopAddressLong = shopAddressLong;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopStartTime() {
        return shopStartTime;
    }

    public void setShopStartTime(String shopStartTime) {
        this.shopStartTime = shopStartTime;
    }

    public String getShopEndTime() {
        return shopEndTime;
    }

    public void setShopEndTime(String shopEndTime) {
        this.shopEndTime = shopEndTime;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public Integer getShopDistance() {
        return shopDistance;
    }

    public void setShopDistance(Integer shopDistance) {
        this.shopDistance = shopDistance;
    }
}
